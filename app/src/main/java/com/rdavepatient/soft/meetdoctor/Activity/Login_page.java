package com.rdavepatient.soft.meetdoctor.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Forgot;
import com.rdavepatient.soft.meetdoctor.MainActivity;
import com.rdavepatient.soft.meetdoctor.Models.Registers;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.RegisterActivity;
import com.rdavepatient.soft.meetdoctor.Utility.CheckInternet;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Login_page extends AppCompatActivity {

    TextView register, forgot;
    Button login;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.Tx_Mobile)
    TextInputEditText TxMobile;
    @BindView(R.id.Tx_Password)
    TextInputEditText TxPassword;
    APIService mApiService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);
        mApiService = ApiUtils.getAPIService();
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forgot = (TextView) findViewById(R.id.forgot);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TxMobile.getText().length() == 0) {
                    TxMobile.setError(Html.fromHtml("<font color='White' >Con not be empty!</font>"));
                    TxMobile.requestFocus();
                    return;
                }
                if (TxPassword.getText().toString().length() == 0) {
                    TxPassword.setError(Html.fromHtml("<font color='White' >Con not be empty!</font>"));
                    TxPassword.requestFocus();
                    return;
                }

                Registers.UserEntity Registerdata = new Registers.UserEntity();
                Registerdata.setMobileNo(TxMobile.getText().toString());
                Registerdata.setPassword(TxPassword.getText().toString());
                Registerdata.setUserType(2);
                if (CheckInternet.isConnectingToInternet(Login_page.this)) {
                    CheckLogin(Registerdata);
                } else {
                    Toast.makeText(Login_page.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_page.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_page.this, Forgot.class);
                startActivity(i);
            }
        });

        /*Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,*/

        Dexter.withActivity(this)
                .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }


    private void CheckLogin(Registers.UserEntity Registerdata) {   // -> LoginANDCheckUser
        final ProgressDialog loading = ProgressDialog.show(Login_page.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        ApiUtils.getAPIService().checkLogin(Registerdata).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                    public void onNext(Registers response) {
                        if (response.getStatus() == 101) {
//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.Forget_Password), "please provide registered email-id or mobile no.", Snackbar.LENGTH_LONG);
//                            snackbar.show();
                            Toasty.error(getApplication(), "Invalid User or Password!", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (response.getStatus() == 200) {

                            boolean b1 = SharePrefarence.getmInstance(getApplicationContext()).userid(response.getUser().getUserId(), response.getUser().getUserName(), response.getUser().getEmail(), response.getUser().getMobileNo());
                            loading.dismiss();
                            startActivity(new Intent(Login_page.this, MainActivity.class));
                            Login_page.this.finish();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
