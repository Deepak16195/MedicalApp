package com.rdavepatient.soft.meetdoctor.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rdavepatient.soft.meetdoctor.Activity.All_Doctors;
import com.rdavepatient.soft.meetdoctor.Activity.ProfileSetting;
import com.rdavepatient.soft.meetdoctor.Adapter.All_docterAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.DocterListData;
import com.rdavepatient.soft.meetdoctor.Models.Registers;
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
public class DocterList extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    All_docterAdapter RecyclerViewHorizontalAdapter;
    APIService mApiService;
    String Areacode;
    LinearLayout hide;
    List<DocterListData.DocterBean> mListData;

    public DocterList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_docter_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        hide= view.findViewById(R.id.hide);
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        mApiService = ApiUtils.getAPIService();
        Areacode = SharePrefarence.getmInstance(getContext()).getAreacode();
        if (Areacode != null && Areacode == "94043") {
            GetdocterList(Areacode, All_Doctors.DocterType);
        } else {
            GetdocterList("", All_Doctors.DocterType);
        }

        return view;
    }


    private void GetdocterList(String data, int type) {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.GetDocterList(data, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DocterListData>() {
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
                    public void onNext(DocterListData data) {
                        loading.dismiss();
                        if (data.getStatus() == 200) {
                            mListData = data.getDocter();
                            if(mListData.size() > 0)
                            {
                                hide.setVisibility(View.GONE);
                            }
                            RecyclerViewHorizontalAdapter = new All_docterAdapter(getContext(), mListData);
                            recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

                        }
                    }
                });


    }


}
