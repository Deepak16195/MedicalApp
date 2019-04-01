package com.rdavepatient.soft.meetdoctor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdavepatient.soft.meetdoctor.Activity.BookingActivity;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.Models.Registers;
import com.rdavepatient.soft.meetdoctor.Models.VerifyResonce;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Forgot extends AppCompatActivity implements View.OnClickListener  {

    protected EditText editMobilePassword;
    protected Button Confirm,Changepassword;
    EditText first, Second, Third, Fourth;
    TextView resendotp;
    String OTPChack = "1234", InputOTP;
    AlertDialog dialog;
    int UserID;
    APIService mApiService;
    EditText edit_new_Password,edit_Re_Password;
    String sNewPassword,sConfirmPassword;
    String mnobileno;
    public static String OTp = "";
    public static Button SendaallinfoData;


    public static Forgot newInstance() {
        Forgot fragment = new Forgot();
        return fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        initView();
        //UserID = SharePrefarence.getmInstance(Forgot.this).getUserId();
        mApiService = ApiUtils.getAPIService();
    }
    private void initView() {
        editMobilePassword = (EditText) findViewById(R.id.edit_Mobile_Password);
        Confirm = (Button) findViewById(R.id.Confirm);
        Confirm.setOnClickListener(Forgot.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Confirm) {


            if (editMobilePassword.getText().length() == 0 || editMobilePassword.getText().length() <= 9) {
                editMobilePassword.setError(Html.fromHtml("<font color='White' >Enter 10 digit Mobile No</font>"));
                editMobilePassword.requestFocus();
                return;
            }

            mnobileno = editMobilePassword.getText().toString();
            ForgetPassword(mnobileno,2);


        }
    }


    public void OtpDialog() {
        final Dialog dialog = new Dialog(Forgot.this);
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
        SendaallinfoData =  dialog.findViewById(R.id.Sendaallinfo);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendotptouser(mo);
                Utils.custoAlert(Forgot.this, "OTP resended");

            }
        });
        SendaallinfoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobilena = editMobilePassword.getText().toString();
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
                if (OTPChack.equals(InputOTP.toString())) {

                    dialog.dismiss();

                    String mobilena = mnobileno;
                    String otps = InputOTP.toString();
                    varifyOTPdata(mobilena, otps);



                } else {
                    //dialog.dismiss();
                    //Utiles.custoAlert(RegisterActivityTwo.this, "OTP Not Verified Re-enter Again");
                   // Toast.makeText(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_LONG).show();
                    Toasty.error(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_SHORT, true).show();
                    Toast.makeText(getApplication(),OTPChack+"",Toast.LENGTH_LONG).show();
                    first.setText("");
                    Second.setText("");
                    Third.setText("");
                    Fourth.setText("");
                    first.requestFocus();
                    dialog.show();

                }
            }
        });
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
            Forgot.SendaallinfoData.performClick();

        } catch (Exception e) {
            Log.e("OTP Page Error", e.getMessage());
        }
    }

    private void varifyOTPdata(String mobile,String OTP) {
        mApiService.varifyPassword(mobile,OTP).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VerifyResonce>() {
                    @Override
                    public void onCompleted() {
                        //categoryAdapter = new CategoryAdapter(Products.this, mCategory, "Products");
                        //recyclerView.setAdapter(categoryAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                    }
                    @Override
                    public void onNext(VerifyResonce response) {
                        if (response.getStatus() == 101) {
                            Toasty.error(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_SHORT, true).show();
                            first.setText("");
                            Second.setText("");
                            Third.setText("");
                            Fourth.setText("");
                            first.requestFocus();
                            return;
                        }
                        if (response.getStatus() == 200) {

                            UserID = response.getUser().getBasicDetails().getUserId();
                            AlterdilogCreation();


                        }
                    }
                });
    }
    public void back(View view) {
        super.onBackPressed();
    }

    public void AlterdilogCreation() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.updatepassword, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(Forgot.this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        dialog = alert.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setLayout(600, 500);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;
        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);
        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 1.01f);
        // Set alert dialog height equal to screen height 70%
        //int dialogWindowHeight = (int) (displayHeight * 0.5f);
        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        //  layoutParams.height = dialogWindowHeight;
        // Apply the newly created layout parameters to the alert dialog window

        dialog.getWindow().setAttributes(layoutParams);
        edit_new_Password = alertLayout.findViewById(R.id.edit_new_Password);
        edit_Re_Password = alertLayout.findViewById(R.id.edit_Re_Password);
        Changepassword= alertLayout.findViewById(R.id.Changepassword);

        Changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmPassword()){
                    String New =edit_Re_Password.getText().toString().trim();
                    if(UserID == 0 )
                    {
                        Utils.customMessage(Forgot.this, "Can't Update");
                    }else
                    {
                        ChangePasswords(UserID,New);
                    }

                }
                else {
                    Toasty.error(getApplication(), "Password doesn't match!", Toast.LENGTH_SHORT, true).show();
                }

            }
        });

    }

    private void ChangePasswords(int U_id,String NewPassword) {
        mApiService.ChangePassword(U_id,NewPassword).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Otprecvier>() {
                    @Override
                    public void onCompleted() {
                        //categoryAdapter = new CategoryAdapter(Products.this, mCategory, "Products");
                        //recyclerView.setAdapter(categoryAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                    }
                    @Override
                    public void onNext(Otprecvier response) {
                        if (response.getStatus() == 101) {
                            Toasty.error(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (response.getStatus() == 200) {
                            Forgot.this.finish();
                            Toast.makeText(getApplication(), "Password Updated", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private boolean confirmPassword() {
        if (!isEmpty(edit_new_Password)) {
            sNewPassword = edit_new_Password.getText().toString().trim();
            sConfirmPassword = edit_Re_Password.getText().toString().trim();
            if (sNewPassword.equals(sConfirmPassword)) {
                return true;
            }

        }
        return false;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;

    }



    private void ForgetPassword(String Mobile,int type) {
        mApiService.ForgetPassword(Mobile,type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Otprecvier>() {
                    @Override
                    public void onCompleted() {
                        //categoryAdapter = new CategoryAdapter(Products.this, mCategory, "Products");
                        //recyclerView.setAdapter(categoryAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Otprecvier response) {
                        if (response.getStatus() == 101) {
                            editMobilePassword.setError(Html.fromHtml("<font color='White' >Number Not Exists</font>"));
                            return;
                        }
                        if (response.getStatus() == 200) {
                            editMobilePassword.setText("");
                            OTPChack = response.getOtp() + "";
                            OtpDialog();
                        }
                    }
                });
    }



}
