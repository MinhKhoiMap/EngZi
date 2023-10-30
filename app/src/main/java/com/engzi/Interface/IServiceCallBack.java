package com.engzi.Interface;

public interface IServiceCallBack {
    void retrieveData(Object response);

    void onFailed(Exception e);

    void onComplete();
}
