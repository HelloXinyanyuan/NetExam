package com.whunf.netexam;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MyApplication extends Application {

    RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Volley队列
        requestQueue = Volley.newRequestQueue(this);
    }
}
