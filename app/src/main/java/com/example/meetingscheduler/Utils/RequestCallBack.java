package com.example.meetingscheduler.Utils;

public interface RequestCallBack<RcGENERIC> {
    void onSuccess(RcGENERIC response);
    void onFailure(int failureCode);
}
