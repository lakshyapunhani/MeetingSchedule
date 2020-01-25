package com.example.meetingscheduler.Utils;

import com.example.meetingscheduler.Meeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    int INTERNAL_SERVER_ERROR = 500;
    int UNAUTHORIZED = 401;
    int INTERNET_FAILURE = 600;
    int RESPONSE_SUCCESS = 200;
    int NO_DATA_ON_SERVER = 204;
    int POST_SUCCESS = 201;
    int BAD_REQUEST = 400;
    int SERVER_DOWN = 503;

    @GET("schedule")
    Call<List<Meeting>> getMeetings(
            @Query("date") String date);
}
