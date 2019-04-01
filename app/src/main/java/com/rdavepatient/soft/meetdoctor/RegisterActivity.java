package com.rdavepatient.soft.meetdoctor;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rdavepatient.soft.meetdoctor.Activity.AddFamilymemberActivty;
import com.rdavepatient.soft.meetdoctor.Activity.Login_page;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.Models.Registers;
import com.rdavepatient.soft.meetdoctor.Utility.CheckInternet;
import com.rdavepatient.soft.meetdoctor.Utility.PermissionUtility;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.Tx_Fullname)
    TextInputEditText TxFullname;
    @BindView(R.id.Tx_Mobile)
    TextInputEditText TxMobile;
    @BindView(R.id.Tx_Emailid)
    TextInputEditText TxEmailid;
    @BindView(R.id.Tx_Password)
    TextInputEditText TxPassword;
    @BindView(R.id.Bu_SignUp)
    Button BuSignUp;
    @BindView(R.id.Tx_haveAccount)
    TextView TxHaveAccount;
    protected EditText BirthdayPicker;
    String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Registers.UserEntity Registerdata;
    APIService mApiService;
    Dialog dialog;
    EditText first, Second, Third, Fourth;
    TextView resendotp;
    String OTPChack = "1234", InputOTP, Mobileno;
    int Userid;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    String bdaydate;
    public static String OTp = "";

    public static Button Sendaallinfo;


    public static RegisterActivity newInstance() {
        RegisterActivity fragment = new RegisterActivity();
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        BirthdayPicker = (EditText) findViewById(R.id.BirthdayPicker);
        mApiService = ApiUtils.getAPIService();





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
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();



    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        bdaydate = sdf.format(myCalendar.getTime());
        BirthdayPicker.setText("Birthday  " + sdf.format(myCalendar.getTime()));
    }


    public void recivedSms(String message) {
        try {
            String otpNumber = message.substring(7, 11);
            Log.d("OTP", otpNumber + "");

//            if(et_OTP!=null)
//            {
//                et_OTP.setText(otpNumber);
//            }
            //  verifyOTPSms(otpNumber);

            OTp = otpNumber;
            RegisterActivity.Sendaallinfo.performClick();


        } catch (Exception e) {
            Log.e("OTP Page Error", e.getMessage());
        }
    }


    private boolean checkValidation() {
        if (Utils.isEmpty(TxFullname)) {
            TxFullname.requestFocus();
            TxFullname.setError("Fullname Can't be Empty!");
            return false;

        }
        if (Utils.isEmpty(TxFullname)) {
            TxFullname.requestFocus();
            TxFullname.setError("Fullname Can't be Empty!");
            return false;

        } else if (Utils.isEmpty(TxMobile)) {
            TxMobile.requestFocus();
            TxMobile.setError("Please provide your mobile no!");
            return false;
        } else if (Utils.isEmpty(TxEmailid)) {
            TxEmailid.requestFocus();
            TxEmailid.setError("Email-Id can't be empty!");
            return false;

        } else if (Utils.isEmpty(TxPassword)) {
            TxPassword.requestFocus();
            TxPassword.setError("Password Can't be Empty!");
            return false;
        }
        if (Utils.isEmpty(BirthdayPicker)) {
            TxFullname.requestFocus();
            TxFullname.setError("Fullname Can't be Empty!");
            return false;

        }

        return true;
    }

    private boolean checkMobileValidation(EditText mobile_no) {
        if (mobile_no.getText().toString().trim().length() != 10) {
            mobile_no.requestFocus();
            mobile_no.setError("Invalid Mobile no!");
            return false;
        }
        return true;
    }

    private boolean checkEmailValidation(EditText email_id) {
        if (!email_id.getText().toString().trim().matches(emailPattern)) {
            email_id.requestFocus();
            email_id.setError("Invalid Email-ID!");
            return false;
        }
        return true;
    }


    @OnClick({R.id.Bu_SignUp, R.id.Tx_haveAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Bu_SignUp:

                if (CheckInternet.isConnectingToInternet(RegisterActivity.this)) {

                    if (checkValidation())
                    {
                        if (checkMobileValidation(TxMobile) && checkEmailValidation(TxEmailid)) {
                            sendotptouser(TxMobile.getText().toString().trim(), 2);
                        }
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Tx_haveAccount:
                Intent intent = new Intent(RegisterActivity.this, Login_page.class);
                startActivity(intent);
                break;
        }
    }


    private void User_Registestion(Registers.UserEntity data) {
        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.User_register(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Registers>() {
                    @Override
                    public void onCompleted() {
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
                            Mobileno = TxMobile.getText().toString();
                            Userid = data.getUser().getUserId();
                            Toasty.success(RegisterActivity.this, "Login Success!", Toast.LENGTH_SHORT, true).show();
                            RegisterActivity.this.finish();
                            boolean b1 = SharePrefarence.getmInstance(getApplicationContext()).userid(data.getUser().getUserId(), data.getUser().getUserName(), data.getUser().getEmail(), data.getUser().getMobileNo());
                            loading.dismiss();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }
                    }
                });


    }

    public void OtpDialog() {
        dialog = new Dialog(RegisterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.otp_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
        first = (EditText) dialog.findViewById(R.id.first);
        Second = (EditText) dialog.findViewById(R.id.Second);
        Third = (EditText) dialog.findViewById(R.id.Third);
        Fourth = (EditText) dialog.findViewById(R.id.Fourth);
        resendotp = (TextView) dialog.findViewById(R.id.resendotp);
        Sendaallinfo =  dialog.findViewById(R.id.Sendaallinfo);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendotptouser(mo);
                Toasty.success(RegisterActivity.this, "OTP Resended!", Toast.LENGTH_SHORT, true).show();
            }
        });

        Sendaallinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobilena = TxMobile.getText().toString();
                varifyOTPdata(mobilena, OTp);

            }
        });


        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Second.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Third.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Fourth.requestFocus();
            }
        });


        Fourth.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (first.getText().toString().isEmpty()) {
                    first.setFocusable(true);
                    return;
                }
                if (Second.getText().toString().isEmpty()) {
                    Second.setFocusable(true);
                    return;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Third.getText().toString().isEmpty()) {
                    Third.setFocusable(true);
                    return;
                }

                InputOTP = first.getText().toString() + Second.getText().toString() + Third.getText().toString() + Fourth.getText().toString();
                String mobilena = TxMobile.getText().toString();
                String otps = InputOTP.toString();
                varifyOTPdata(mobilena, otps);

            }
        });

    }


    private void varifyOTPdata(String mobile, String OTP) {
        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.varifyOTP(mobile, OTP).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Registers>() {
                    @Override
                    public void onCompleted() {
                        loading.dismiss();
                        //categoryAdapter = new CategoryAdapter(Products.this, mCategory, "Products");
                        //recyclerView.setAdapter(categoryAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.dismiss();
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Registers response) {
                        if (response.getStatus() == 101) {
                            Toast.makeText(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_LONG).show();
                            first.setText("");
                            Second.setText("");
                            Third.setText("");
                            Fourth.setText("");
                            first.requestFocus();
                            return;
                        }
                        if (response.getStatus() == 200) {
                            dialog.dismiss();
                            Registerdata = new Registers.UserEntity();
                            Registerdata.setMobileNo(TxMobile.getText().toString());
                            Registerdata.setEmail(TxEmailid.getText().toString());
                            Registerdata.setPassword(TxPassword.getText().toString());
                            Registerdata.setUserName(TxFullname.getText().toString());
                            Registerdata.setUserType(2);
                            Registerdata.setDOB(bdaydate);
                            User_Registestion(Registerdata);

                        }
                    }
                });
    }


    private void sendotptouser(String Mobile, int type) {
        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.Sendotptouser(Mobile, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                        if (data.getStatus() == 101) {
                            Toasty.normal(getApplication(), data.getMessage()).show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(getApplication(), data.getMessage()).show();
                            return;
                        }
                        if (data.getStatus() == 200) {
                            loading.dismiss();
                            OtpDialog();

                        }
                        if (data.getStatus() == 404) {
                            Toasty.normal(getApplication(), data.getMessage()).show();

                        }
                    }
                });


    }


}
