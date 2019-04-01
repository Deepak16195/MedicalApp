package com.rdavepatient.soft.meetdoctor.remote.api;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Dev on 27-02-2018.
 */

public class API {

    public static final String WEB_API_BASE_URL = "http://mediapi.mdsoftech.in/api/";

    public static WebService getWebService() {
        return new Retrofit.Builder().baseUrl(WEB_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService.class);
    }

}