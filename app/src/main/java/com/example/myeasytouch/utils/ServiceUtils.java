package com.example.myeasytouch.utils;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

/**
 * Created by 司维 on 2017/10/16.
 */

public class ServiceUtils {
    public static boolean isOpening(Context context, String serviceName) {
        if ("".equals(serviceName) || serviceName == null) {
            return false;
        }
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningServiceInfo> runningList = manager.getRunningServices(200);
//        for (int i = 0; i < runningList.size(); i++) {
//            if (runningList.get(i).service.getClassName().equals(serviceName)) {
//                return true;
//            }
//        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
            @Override
            public void onAccessibilityStateChanged(boolean b) {
                Log.d("ServiceUtils", "changed" + b);
            }
        });

        return false;
    }
}
