package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Adapter.All_PatientAppAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.PatientAppoint;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppoitemntsActivty extends AppCompatActivity {

    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    All_PatientAppAdapter RecyclerViewHorizontalAdapter;
    APIService mApiService;
    List<PatientAppoint.AppointmentsEntity> mListData;
    @BindView(R.id.ApptmetsList)
    RecyclerView ApptmetsList;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar pbLoadingIndicator;
    @BindView(R.id.tv_error_message_display)
    TextView tvErrorMessageDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoitemnts_activty);
        ButterKnife.bind(this);
        HorizontalLayout = new LinearLayoutManager(AppoitemntsActivty.this, LinearLayoutManager.VERTICAL, false);
        ApptmetsList.setLayoutManager(HorizontalLayout);
        mApiService = ApiUtils.getAPIService();
        int id = SharePrefarence.getmInstance(AppoitemntsActivty.this).getUserId();
        GetPatientAppointment(id);
    }
    private void GetPatientAppointment(int data) {
        final ProgressDialog loading = ProgressDialog.show(AppoitemntsActivty.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.GetPatientAppointment(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PatientAppoint>() {
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
                    public void onNext(PatientAppoint data) {
                        loading.dismiss();
                        if (data.getStatus() == 101) {
                            Toasty.normal(AppoitemntsActivty.this, "Email Id Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(AppoitemntsActivty.this, "Mobile No Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 200) {

                            mListData = data.getAppointments();
                            RecyclerViewHorizontalAdapter = new All_PatientAppAdapter(AppoitemntsActivty.this,mListData);
                            ApptmetsList.setAdapter(RecyclerViewHorizontalAdapter);

                        }
                    }
                });


    }


    public void back(View view) {
        super.onBackPressed();
    }
}
