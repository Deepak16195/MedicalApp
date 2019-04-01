package com.rdavepatient.soft.meetdoctor.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Activity.AppoitemntsActivty;
import com.rdavepatient.soft.meetdoctor.Adapter.CustomPaginationRecyclerView;
import com.rdavepatient.soft.meetdoctor.Adapter.NotificationAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.MainActivity;
import com.rdavepatient.soft.meetdoctor.Models.NotificationModel;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.Utility.CheckInternet;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;
import com.rdavepatient.soft.meetdoctor.remote.api.API;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification extends Fragment {


    public static final String MY_PREFS_NAME = "Community";
    public static Notification newInstance() {
        Notification fragment = new Notification();
        return fragment;
    }
    public static final String TAG = "NOTIFICATION_FRAGMENT";
    /*-----------------  VIEW COMPONENT DECLARE ------------------*/
    RecyclerView mRecyclerView;
    NotificationAdapter mNotificationAdapter;
    int user_id = 2;
    TextView allread;
    ArrayList<NotificationModel.NotificationBean> data;
    ProgressBar mProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    APIService mApiService;
    public boolean requestOnWay = false;
    private PublishProcessor<Integer> pagination;
    private CompositeDisposable compositeDisposable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_notification, container, false);
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(rootView);
        mApiService = ApiUtils.getAPIService();
        pagination = PublishProcessor.create();
        compositeDisposable = new CompositeDisposable();
        /*------------------ RecyclerView -----------------*/
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        data = new ArrayList<>();
        mNotificationAdapter = new NotificationAdapter(getContext(), data);
        mRecyclerView.setAdapter(mNotificationAdapter);

        mRecyclerView.addOnScrollListener(new CustomPaginationRecyclerView(layoutManager) {
            @Override
            public void onLoadMore(int currentPage, int totalItemCount, View view) {
                if (!requestOnWay) {
                    pagination.onNext(mNotificationAdapter.getLastVisibleItemId());
                }
            }
        });
        /*------------------------------------------------------*/

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetworkData();

            }
        });

        allread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              MarkAllRead(user_id);

            }
        });

        Disposable disposable = pagination.onBackpressureDrop().doOnNext(integer -> {
            requestOnWay = true;
            mProgressBar.setVisibility(View.VISIBLE);
        }).doOnError(error -> {
            Log.d("retrofit_error", error.toString());
        }).concatMap(this::getUserNotificationList).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.getStatus()== 200) {
                        if(response.getNotification() == null)
                        {

                        }else {
                            data = response.getNotification();
                            mNotificationAdapter.setData(data);
                        }

                    } else {
                        Log.e("error", response.getStatus()+"");
                    }
                    requestOnWay = false;
                    mProgressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                })
                .doOnError(throwable -> {
                    if (throwable instanceof HttpException) {
                        Response<?> response = ((HttpException) throwable).response();
                        Log.d("HttpException", response.message());
                    } else {
                        Log.d("server_error", throwable.toString());
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }).subscribe();


        compositeDisposable.add(disposable);
        loadNetworkData();

        return rootView;

    }


    private void initView(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeContainer);
        mProgressBar = rootView.findViewById(R.id.progress_bar);
        mRecyclerView = rootView.findViewById(R.id.notification_rv);
        allread = rootView.findViewById(R.id.allread);

       // user_id =  SharePrefarence.getmInstance(getContext()).getUserId();
    }

    private void loadNetworkData() {
        if (CheckInternet.isConnectingToInternet(getContext())) {
            mProgressBar.setVisibility(View.VISIBLE);
            mNotificationAdapter.clearData();
            pagination.onNext(0);
        } else {
            mProgressBar.setVisibility(View.GONE);
            Utils.customMessage(getContext(), "Please, Check your internet connection");
        }
    }


    private Flowable<NotificationModel> getUserNotificationList(int from_row_no) {
        int lenth = from_row_no + 20;
        return API.getWebService().getUserNotificationList(user_id, from_row_no, lenth)
                .delay(200, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }



    private void MarkAllRead(int userid) {
        mApiService.MarkAllRead(userid).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Otprecvier>() {
                    @Override
                    public void onCompleted() {
                        loadNetworkData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                        Log.e("Token_Status", "FCM Token Can't be Saved");
                    }

                    @Override
                    public void onNext(Otprecvier response) {
                        if (response.getStatus() == 101) {
                            Log.e("Token_Status", "FCM Token Can't be Saved");
                            return;
                        }
                        if (response.getStatus() == 200) {
//                            MainActivity main=new MainActivity();
//                            main.countnotification(user_id);;

                        }
                    }
                });
    }

}
