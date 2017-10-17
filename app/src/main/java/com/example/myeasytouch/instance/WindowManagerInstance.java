package com.example.myeasytouch.instance;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by 司维 on 2017/10/12.
 */

public class WindowManagerInstance {
    private static WindowManager windowManager ;
    public static Context mContext;

    public WindowManagerInstance(Context context){
        mContext = context;
    }
    public static WindowManager newInstance(){
        if (windowManager == null){
            windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }
}
