package com.example.myeasytouch.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.myeasytouch.event.MessageEvent;
import com.example.myeasytouch.holder.MyViewHolder;
import com.example.myeasytouch.view.MutiTaskView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyService extends AccessibilityService {
    private final static String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        MyViewHolder.setIsServiceRunning(true);
        Log.d(TAG, "isServiceRunning");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        MyViewHolder.setIsServiceRunning(false);
        Log.d(TAG, "isServiceRunning");
        return super.onUnbind(intent);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event){
        String msg = event.getMsg();
        if (MutiTaskView.GLOBAL_ACTION_BACK.equals(msg)) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        if (MutiTaskView.GLOBAL_ACTION_HOME.equals(msg)){
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        }
        if (MutiTaskView.GLOBAL_ACTION_RECENTS.equals(msg)){
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
    }

}
