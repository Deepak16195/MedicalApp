package com.rdavepatient.soft.meetdoctor.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdavepatient.soft.meetdoctor.Adapter.All_PatientAppAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.PatientAppoint;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Oppiments extends Fragment {


    RecyclerView ApptmetsList;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    All_PatientAppAdapter RecyclerViewHorizontalAdapter;
    APIService mApiService;
    List<PatientAppoint.AppointmentsEntity> mListData;
    LinearLayout hide;



    public static Oppiments newInstance() {
        Oppiments fragment = new Oppiments();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_oppiments, container, false);
        ApptmetsList = view.findViewById(R.id.ApptmetsList);
        hide= view.findViewById(R.id.hide);
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ApptmetsList.setLayoutManager(HorizontalLayout);
        mApiService = ApiUtils.getAPIService();
        int id = SharePrefarence.getmInstance(getContext()).getUserId();
        GetPatientAppointment(id);


        return view;
    }


    private void GetPatientAppointment(int data) {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
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
                            Toasty.normal(getContext(), "Email Id Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(getContext(), "Mobile No Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 200) {

                            mListData = data.getAppointments();
                            if(mListData.size() > 0)
                            {
                                hide.setVisibility(View.GONE);
                            }
                            RecyclerViewHorizontalAdapter = new All_PatientAppAdapter(getContext(),mListData);
                            ApptmetsList.setAdapter(RecyclerViewHorizontalAdapter);

                        }
                    }
                });


    }

}
