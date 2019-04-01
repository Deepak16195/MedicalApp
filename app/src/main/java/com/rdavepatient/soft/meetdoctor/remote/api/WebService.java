package com.rdavepatient.soft.meetdoctor.remote.api;


import com.rdavepatient.soft.meetdoctor.Models.NotificationModel;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("Appointment/getNotification1")
        Flowable<NotificationModel> getUserNotificationList(@Query("user_id") int user_id, @Query("start") int start, @Query("length") int length);


}
