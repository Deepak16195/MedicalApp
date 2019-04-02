package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rdavepatient.soft.meetdoctor.Adapter.familyListAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.MainActivity;
import com.rdavepatient.soft.meetdoctor.Models.Famelimember;
import com.rdavepatient.soft.meetdoctor.Models.Registers;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.RegisterActivity;
import com.rdavepatient.soft.meetdoctor.Utility.CheckInternet;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddFamilymemberActivty extends AppCompatActivity implements View.OnClickListener {

    public Spinner spBloodGroup;
    public EditText BirthdayPicker;
    public Button NextButton;
    public Button UpdateButton;
    public EditText Fullname;
    public EditText Relation;
    public EditText Mobile;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    APIService mApiService;
    Famelimember.FamelyEntity Famelimemberdata;
    public static String bdaydate;
    String postid="";
    int pos;
    int position=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_familymember);
        postid=getIntent().getStringExtra("Postid");
        if(postid == null)
        {
            postid="";
        }
        initView();
        mApiService = ApiUtils.getAPIService();
        if(!postid.equalsIgnoreCase(""))
        {
            pos=Integer.valueOf(postid);
            Fullname.setText(familyListAdapter.list.get(pos).getFamelyMemberName()+"");
            BirthdayPicker.setText(familyListAdapter.list.get(pos).getDOB()+"");
            bdaydate =familyListAdapter.list.get(pos).getDOB();
            Mobile.setText(familyListAdapter.list.get(pos).getMobileNo()+"");
            Relation.setText(familyListAdapter.list.get(pos).getRelation()+"");
            String bloodgrop = familyListAdapter.list.get(pos).getBloodGrop();
            if (bloodgrop.equals("O+")) {
                position = 0;
            }
            if (bloodgrop.equals("O-")) {
                position = 1;
            }
            if (bloodgrop.equals("A+")) {
                position = 2;
            }
            if (bloodgrop.equals("A-")) {
                position = 3;
            }
            if (bloodgrop.equals("B+")) {
                position = 4;
            }
            if (bloodgrop.equals("B-")) {
                position = 5;
            }
            if (bloodgrop.equals("AB+")) {
                position = 6;
            }
            if (bloodgrop.equals("AB-")) {
                position = 7;
            }
            spBloodGroup.setSelection(position);
            NextButton.setVisibility(View.GONE);
            UpdateButton.setVisibility(View.VISIBLE);
        }

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bGs = spBloodGroup.getSelectedItem() + "";
                if (CheckInternet.isConnectingToInternet(AddFamilymemberActivty.this)) {
                    if (!Utils.isEmpty(Fullname) && !Utils.isEmpty(Relation) && !Utils.isEmpty(Mobile)) {
                        if (checkMobileValidation(Mobile)) {
                            String bG = spBloodGroup.getSelectedItem() + "";
                            if (bGs.equals("Blood Group")) {
                                Utils.customMessage(AddFamilymemberActivty.this, "Please Select Any Blood Group !");
                            } else {
                                final int id = SharePrefarence.getmInstance(AddFamilymemberActivty.this).getUserId();
                                Famelimemberdata = new Famelimember.FamelyEntity();
                                Famelimemberdata.setDOB(bdaydate);
                                Famelimemberdata.setFamelyMemberName(Fullname.getText().toString() + "");
                                Famelimemberdata.setBloodGrop(spBloodGroup.getSelectedItem() + "");
                                Famelimemberdata.setMobileNo(Mobile.getText().toString().trim() + "");
                                Famelimemberdata.setRelation(Relation.getText().toString().trim() + "");
                                Famelimemberdata.setUserid(id);
                                AddFamilymems(Famelimemberdata);


                            }

                        }
                    }
                    ChackNull(Fullname);
                    ChackNull(Relation);
                    ChackNull(Mobile);
                } else {
                    Utils.customMessage(AddFamilymemberActivty.this, "Please check your Internet connection!");
                }
            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bGs = spBloodGroup.getSelectedItem() + "";
                if (CheckInternet.isConnectingToInternet(AddFamilymemberActivty.this)) {
                    if (!Utils.isEmpty(Fullname) && !Utils.isEmpty(Relation) && !Utils.isEmpty(Mobile)) {
                        if (checkMobileValidation(Mobile)) {
                            String bG = spBloodGroup.getSelectedItem() + "";
                            if (bGs.equals("Blood Group")) {
                                Utils.customMessage(AddFamilymemberActivty.this, "Please Select Any Blood Group !");
                            } else {
                                final int id = SharePrefarence.getmInstance(AddFamilymemberActivty.this).getUserId();
                                Famelimemberdata = new Famelimember.FamelyEntity();
                                Famelimemberdata.setDOB(bdaydate);
                                Famelimemberdata.setFamelyMemberName(Fullname.getText().toString() + "");
                                Famelimemberdata.setBloodGrop(spBloodGroup.getSelectedItem() + "");
                                Famelimemberdata.setMobileNo(Mobile.getText().toString().trim() + "");
                                Famelimemberdata.setRelation(Relation.getText().toString().trim() + "");
                                Famelimemberdata.setUserid(id);
                                Famelimemberdata.setFamelymemberId(familyListAdapter.list.get(pos).getFamelymemberId());
                                AddFamilymems(Famelimemberdata);


                            }

                        }
                    }
                    ChackNull(Fullname);
                    ChackNull(Relation);
                    ChackNull(Mobile);
                } else {
                    Utils.customMessage(AddFamilymemberActivty.this, "Please check your Internet connection!");
                }
            }
        });
    }

    public void ChackNull(EditText text) {
        String mNmae = text.getText().toString().trim();
        if (mNmae == null || mNmae.equalsIgnoreCase("null") || mNmae.length() == 0) {
            text.setError("Cannot be empty !");
        }
    }

    private boolean checkMobileValidation(EditText mobile_no) {
        if (mobile_no.getText().toString().trim().length() < 10 || mobile_no.getText().toString().trim().length() > 13) {
            mobile_no.requestFocus();
            mobile_no.setError("Invalid Mobile no!");
            return false;
        }
        return true;
    }

    private void updateLabel() {
        String myFormat = "dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        bdaydate = sdf.format(myCalendar.getTime());
        BirthdayPicker.setText("Birthday  " + sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.BirthdayPicker) {
            new DatePickerDialog(AddFamilymemberActivty.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    }

    private void initView() {
        spBloodGroup = (Spinner) findViewById(R.id.sp_blood_group);
        BirthdayPicker = (EditText) findViewById(R.id.BirthdayPicker);
        BirthdayPicker.setOnClickListener(AddFamilymemberActivty.this);
        NextButton = (Button) findViewById(R.id.NextButton);
        UpdateButton = (Button) findViewById(R.id.UpdateButton);
        Fullname = (EditText) findViewById(R.id.Fullname);
        Mobile = (EditText) findViewById(R.id.Mobile);
        Relation = (EditText) findViewById(R.id.Relation);
    }


    public void back(View view) {
        super.onBackPressed();
    }


    private void AddFamilymems(Famelimember.FamelyEntity data) {
        final ProgressDialog loading = ProgressDialog.show(AddFamilymemberActivty.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.AddFamelimember(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Famelimember>() {
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
                    public void onNext(Famelimember data) {
                        if (data.getStatus() == 101) {
                            Toasty.normal(getApplication(), "Email Id Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(getApplication(), "Mobile No Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 200) {
                            Intent i = new Intent(getApplication(), FamilymemberList.class);
                            startActivity(i);
                            AddFamilymemberActivty.this.finish();

                        }
                    }
                });


    }

}
