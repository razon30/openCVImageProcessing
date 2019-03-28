package com.prince.thesis.utils;

/**
 * Created by razon30 on 01-08-17.
 */

import android.app.Application;


public class MyApplication extends Application {

    public static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static MyApplication getInstance(){

        if (instance == null){
            instance = new MyApplication();
        }

        return instance;

    }

}
