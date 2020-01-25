package com.example.meetingscheduler.Utils;

import androidx.annotation.NonNull;

import com.example.meetingscheduler.Meeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository
{
    private static Repository repository;

    public static Repository getInstance()
    {
        if (repository == null)
        {
            synchronized (Repository.class)
            {
                repository =new Repository();
            }
        }
        return repository;
    }

    private Repository() { }

    public void getMeetings(String date,
                               final RequestCallBack<List<Meeting>> callBack)
    {
        Call<List<Meeting>> call = HelperClient.getRequestInterface().getMeetings(date);
        call.enqueue(new Callback<List<Meeting>>() {
            @Override
            public void onResponse(@NonNull Call<List<Meeting>> call,
                                   @NonNull Response<List<Meeting>> response) {
                if (response.code() == RequestInterface.RESPONSE_SUCCESS) {
                    callBack.onSuccess(response.body());
                }
                else {
                    callBack.onFailure(response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Meeting>> call, @NonNull Throwable t) {
                callBack.onFailure(RequestInterface.INTERNET_FAILURE);
            }
        });
    }

}
