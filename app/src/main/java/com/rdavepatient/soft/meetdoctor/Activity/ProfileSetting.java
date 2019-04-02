package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.Registers;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.RegisterActivity;
import com.rdavepatient.soft.meetdoctor.Utility.CheckInternet;
import com.rdavepatient.soft.meetdoctor.Utility.PermissionUtility;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileSetting extends AppCompatActivity {
    APIService mApiService;
    Registers.UserEntity mregisters;
    int Userid;
    @BindView(R.id.image_back_button)
    ImageView imageBackButton;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.Fullname)
    TextInputEditText Fullname;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.MobileNo)
    TextInputEditText MobileNo;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;
    @BindView(R.id.Emailid)
    TextInputEditText Emailid;
    @BindView(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @BindView(R.id.Password)
    TextInputEditText Password;
    @BindView(R.id.textInputLayout4)
    TextInputLayout textInputLayout4;
    @BindView(R.id.NextButton)
    Button NextButton;
    @BindView(R.id.sp_blood_group)
    Spinner spBloodGroup;
    @BindView(R.id.RbMale)
    RadioButton RbMale;
    @BindView(R.id.RbFemale)
    RadioButton RbFemale;
    @BindView(R.id.nutCalGenderRadioGroup)
    RadioGroup nutCalGenderRadioGroup;
    Registers.UserEntity Registerdata;
    int position;
    protected EditText BirthdayPicker;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    String bdaydate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        ButterKnife.bind(this);
        mApiService = ApiUtils.getAPIService();
        BirthdayPicker = (EditText) findViewById(R.id.BirthdayPicker);
        Userid = SharePrefarence.getmInstance(ProfileSetting.this).getUserId();
        GetProfile(Userid);
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
        BirthdayPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileSetting.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        bdaydate = sdf.format(myCalendar.getTime());
        BirthdayPicker.setText("" + sdf.format(myCalendar.getTime()));
    }

    public void back(View view) {
        super.onBackPressed();
    }


    private void GetProfile(int data) {
        final ProgressDialog loading = ProgressDialog.show(ProfileSetting.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.GetProfile(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Registers>() {
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
                    public void onNext(Registers data) {
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
                            mregisters = data.getUser();
                            Fullname.setText(data.getUser().getUserName());
                            MobileNo.setText(data.getUser().getMobileNo());
                            Emailid.setText(data.getUser().getEmail());
                            bdaydate = (data.getUser().getDOB());
                            if(bdaydate == null)
                            {
                                bdaydate="";
                            }
                            BirthdayPicker.setText(bdaydate);
                            String bloodgrop = data.getUser().getBloodGroup();
                            Password.setVisibility(View.GONE);
                            String Gendrers = data.getUser().getGender();
                            if(Gendrers.equalsIgnoreCase("F"))
                            {
                                RbMale.setChecked(false);
                                RbFemale.setChecked(true);
                            }

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
                        }
                    }
                });


    }

    private boolean checkValidation() {
        if (Utils.isEmpty(Fullname)) {
            Fullname.requestFocus();
            Fullname.setError("Fullname Can't be Empty!");
            return false;

        } else if (Utils.isEmpty(MobileNo)) {
            MobileNo.requestFocus();
            MobileNo.setError("Please provide your mobile no!");
            return false;
        } else if (Utils.isEmpty(Emailid)) {
            Emailid.requestFocus();
            Emailid.setError("Email-Id can't be empty!");
            return false;

        }

        return true;
    }


    @OnClick(R.id.NextButton)
    public void onViewClicked() {

            if (CheckInternet.isConnectingToInternet(getApplicationContext())) {
                if (checkValidation()) {
                    String Gender;
                    Registerdata = new Registers.UserEntity();
                    Registerdata.setUserId(Userid);
                    Registerdata.setUserName(Fullname.getText().toString());
                    Registerdata.setDOB(bdaydate);
                    Registerdata.setUserType(2);
                    Registerdata.setBloodGroup(spBloodGroup.getSelectedItem() + "");
                    if (RbMale.isChecked()) {
                        Gender = "M";
                    } else {
                        Gender = "F";
                    }
                    Registerdata.setGender(Gender);
                    User_Update(Registerdata);

                }
            } else {
                Utils.customMessage(ProfileSetting.this, "No Network Available");
            }

    }

    private void User_Update(Registers.UserEntity data) {
        final ProgressDialog loading = ProgressDialog.show(ProfileSetting.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.User_register(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Registers>() {
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
                    public void onNext(Registers data) {
                        if (data.getStatus() == 200) {
                            loading.dismiss();
                            if (data.getUser().getUserId() == Userid) {
                                boolean b1 = SharePrefarence.getmInstance(getApplicationContext()).userid(data.getUser().getUserId(), data.getUser().getUserName(), data.getUser().getEmail(), data.getUser().getMobileNo());
                                Utils.customMessage(ProfileSetting.this, "Profile Updated");
                                finish();
                            }
                        }
                    }
                });


    }


}
