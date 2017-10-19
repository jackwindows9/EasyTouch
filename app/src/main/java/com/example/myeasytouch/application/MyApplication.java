package com.example.myeasytouch.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by 司维 on 2017/10/19.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    public MyApplication() {
        super();
        Log.d(TAG, "MyApplication");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory");
    }
}
