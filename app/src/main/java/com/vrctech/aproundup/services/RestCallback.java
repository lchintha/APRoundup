package com.vrctech.aproundup.services;

import android.app.Activity;

import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;

import org.json.JSONObject;

public abstract class RestCallback {

    private Activity activity;

    protected RestCallback(Activity activity){
        this.activity = activity;
    }

    protected RestCallback(){
    }

    public abstract void result(JSONObject response);

    void onFailure() {
        NotifyHelper.toast(activity, R.string.unexpected_error);
    }
}
