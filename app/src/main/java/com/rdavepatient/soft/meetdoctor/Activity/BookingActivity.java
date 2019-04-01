package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdavepatient.soft.meetdoctor.Adapter.DateAdapter;
import com.rdavepatient.soft.meetdoctor.Adapter.TagAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.MainActivity;
import com.rdavepatient.soft.meetdoctor.Models.BookDocter;
import com.rdavepatient.soft.meetdoctor.Models.DateModel;
import com.rdavepatient.soft.meetdoctor.Models.GetSlot;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.TagLayout.FlowTagLayout;
import com.rdavepatient.soft.meetdoctor.TagLayout.OnTagSelectListener;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookingActivity extends AppCompatActivity {


    @BindView(R.id.img_home_profile_pic)
    ImageView imgHomeProfilePic;
    @BindView(R.id.lay_rel_img)
    RelativeLayout layRelImg;
    @BindView(R.id.Doctername)
    TextView Doctername;
    @BindView(R.id.dashBackDateBt)
    ImageView dashBackDateBt;
    @BindView(R.id.dashDateTv)
    TextView dashDateTv;
    @BindView(R.id.dashForwardDateBt)
    ImageView dashForwardDateBt;
    @BindView(R.id.rv_date)
    RecyclerView rvDate;
    @BindView(R.id.Bt_morg)
    Button BtMorg;
    @BindView(R.id.Bt_Eving)
    Button BtEving;
    @BindView(R.id.mobile_flow_layout)
    FlowTagLayout mobileFlowLayout;
    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.BookNow)
    Button BookNow;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.D_post)
    TextView DPost;
    @BindView(R.id.Morning_Time)
    TextView MorningTime;
    @BindView(R.id.Evening_Time)
    TextView EveningTime;
    private FlowTagLayout mMobileFlowTagLayout;
    private TagAdapter<String> mMobileTagAdapter;
    List<String> dataSource = new ArrayList<>();
    public static List<Boolean> dataCategoryid = new ArrayList<>();
    List<GetSlot.SlotListEntity> mAlldata = new ArrayList<>();
    APIService mApiService;
    GetSlot Categorydata;
    protected RecyclerView mRVDate;
    public static FrameLayout frame;
    RecyclerView.LayoutManager mLayoutManager;
    DateAdapter mDateAdapter;
    ArrayList<DateModel> models;
    List<GetSlot> mCategorydata;
    int UserID, DocterId = 0;
    Calendar myCalendar;
    String strDate;
    DateFormat dateFormat;
    BookDocter DataSet;
    Date date;
    String AppotmentTime;
    String Pikerdate;
    public static String DocternameData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);
        DocterId = getIntent().getIntExtra("DocterId", 0);
        mApiService = ApiUtils.getAPIService();
        mRVDate = findViewById(R.id.rv_date);
        dashDateTv = findViewById(R.id.dashDateTv);
        dashBackDateBt = findViewById(R.id.dashBackDateBt);
        dashForwardDateBt = findViewById(R.id.dashForwardDateBt);
        Categorydata = new GetSlot();
        UserID = SharePrefarence.getmInstance(BookingActivity.this).getUserId();
        date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        strDate = dateFormat.format(date);
        dashDateTv.setText(strDate);
        Doctername.setText(DocternameData);

        dashBackDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date newdate = Calendar.getInstance().getTime();
                String newdatelive = dateFormat.format(newdate);
                if (strDate.equals(newdatelive)) {
                    Toast.makeText(getApplicationContext(), "can't book on back day", Toast.LENGTH_LONG).show();
                    return;
                }
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, -1);
                date = c.getTime();
                strDate = dateFormat.format(date);
                dashDateTv.setText(strDate);
                Pikerdate=strDate;
                BtMorg.performClick();
            }
        });
        dashForwardDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
                strDate = dateFormat.format(date);
                dashDateTv.setText(strDate);
                Pikerdate=strDate;
                BtMorg.performClick();
            }
        });
        Pikerdate = dashDateTv.getText().toString().trim();


        /*----------------------------- Date Rescyclerview------------------------------------*/
        mRVDate.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRVDate.setLayoutManager(mLayoutManager);
        mDateAdapter = new DateAdapter(this);
        mRVDate.setAdapter(mDateAdapter);
        /*-----------------------------------------------------------------------------------------*/
        myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.setTimeInMillis(System.currentTimeMillis() - 1000);
                updateLabel();
            }
        };

        dashDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

//                new DatePickerDialog(BookingActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        mMobileFlowTagLayout = (FlowTagLayout) findViewById(R.id.mobile_flow_layout);
        mMobileTagAdapter = new TagAdapter<>(this);
        mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
        mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                mCategorydata = new ArrayList<>();
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append("  ,  ");
                        // Categorydata.setAppointments(get);

                        AppotmentTime = mMobileTagAdapter.getItem(i) + "";
                        mCategorydata.add(Categorydata);

                    }
                    // Snackbar.make(parent, "Hello:" + sb.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    // Snackbar.make(parent, "Cant be Black plz select One " + selectedList.size(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        BtMorg.performClick();


    }

    public void back(View view) {
        super.onBackPressed();
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dashDateTv.setText(sdf.format(myCalendar.getTime()));
        Pikerdate=sdf.format(myCalendar.getTime());
    }

    private void GetAllPostList(int docterid, String time, String Category) {   // -> LoginANDCheckUser
        ApiUtils.getAPIService().GetSlotOFDocters(docterid, time, Category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetSlot>() {
                    @Override
                    public void onCompleted() {
                        mMobileTagAdapter.onlyAddAll(dataSource);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("retro_error", e.toString());
                    }

                    @Override
                    public void onNext(GetSlot response) {
                        if (response.getStatus() == 101) {

                            return;
                        }
                        if (response.getStatus() == 200) {
                            mMobileTagAdapter.clearAndAddAll(dataSource);
                            mAlldata.clear();
                            dataSource.clear();
                            dataCategoryid.clear();

                            if (response.getAppointments().getSlotList().size() > 0) {
                                mAlldata = response.getAppointments().getSlotList();
                                for (int x = 0; x < mAlldata.size(); x++) {
                                    dataSource.add(mAlldata.get(x).getSlot());
                                    dataCategoryid.add(mAlldata.get(x).getStatus());
                                }

                                MorningTime.setText(response.getAppointments().getTimeSlot().getMornigFrom()+" To "+response.getAppointments().getTimeSlot().getMornigTo());
                                EveningTime.setText(response.getAppointments().getTimeSlot().getEveningFrom()+" To "+response.getAppointments().getTimeSlot().getEveningTo());

//                                mMobileTagAdapter.onlyAdd("Test");
//                                mMobileTagAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }

    private void BookDocter(BookDocter data) {
        final ProgressDialog loading = ProgressDialog.show(BookingActivity.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.BookDocter(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Otprecvier>() {
                    @Override
                    public void onCompleted() {
                        loading.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.dismiss();
                        Log.e("retro_error", e.toString());
                    }

                    @Override
                    public void onNext(Otprecvier data) {
                        loading.dismiss();
                        if (data.getStatus() == 101) {
                            Toasty.normal(getApplication(), "Email Id Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(getApplication(), "Mobile No Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 200) {

                            Toasty.normal(getApplication(), data.getMessage()).show();
                            Intent i = new Intent(getApplication(), MainActivity.class);
                            startActivity(i);
                            BookingActivity.this.finish();

                        }
                    }
                });


    }


    @OnClick({R.id.Bt_morg, R.id.Bt_Eving, R.id.BookNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Bt_morg:
                GetAllPostList(DocterId, dashDateTv.getText().toString(), "M");
                BtMorg.setBackground(getResources().getDrawable(R.color.Buttoncolor));
                BtEving.setBackground(getResources().getDrawable(R.color.White));
                break;
            case R.id.Bt_Eving:
                GetAllPostList(DocterId, dashDateTv.getText().toString(), "E");
                BtMorg.setBackground(getResources().getDrawable(R.color.White));
                BtEving.setBackground(getResources().getDrawable(R.color.Buttoncolor));
                break;
            case R.id.BookNow:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // Detetepost(postidtodelite);
//                                    list.remove(getAdapterPosition());
//                                    notifyItemRemoved(getAdapterPosition());
//                                    notifyItemRangeChanged(getAdapterPosition(), list.size());
                                DataSet = new BookDocter();
                                DataSet.setPatientId(UserID);
                                DataSet.setDoctorId(DocterId);
                                DataSet.setAppointmentDate(dashDateTv.getText().toString().trim());
                                DataSet.setAppointmentTime(AppotmentTime);
                                BookDocter(DataSet);
                                dialog.dismiss();
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setMessage("Are you sure to Book Appointment ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                break;
        }
    }
}
