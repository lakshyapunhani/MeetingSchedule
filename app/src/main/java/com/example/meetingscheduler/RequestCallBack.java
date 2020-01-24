package com.example.meetingscheduler;

public interface RequestCallBack<RcGENERIC> {
    void onSuccess(RcGENERIC response);
    void onFailure(int failureCode);
}
