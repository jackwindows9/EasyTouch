package com.example.myeasytouch.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myeasytouch.R;
import com.example.myeasytouch.activity.MainActivity;
import com.example.myeasytouch.instance.WindowManagerInstance;
import com.example.myeasytouch.view.EasyTouchView;
import com.example.myeasytouch.view.MutiTaskView;

/**
 * Created by 司维 on 2017/10/11.
 */

public class MyViewHolder {
    private static Context mContext;
    private static Context appContext;
    private final static String TAG = "MyViewHolder";
    private static EasyTouchView mEasyTouchView;
    private static MutiTaskView mutiTaskView;
    private static WindowManager mWindowManager;
    private WindowManagerInstance mWindowManagerInstance;
    public static boolean isServiceRunning = false;

    public MyViewHolder(Context context){
        mContext = context;
        appContext = context.getApplicationContext();
        this.mEasyTouchView = new EasyTouchView(appContext, null);
        this.mutiTaskView = new MutiTaskView(appContext, null);
        mWindowManagerInstance = new WindowManagerInstance(appContext);
        mWindowManager = WindowManagerInstance.newInstance();
    }

    public static void showEasyTouchView(){
        WindowManager.LayoutParams layoutParams = mEasyTouchView.getmLayoutParams();
        if (!EasyTouchView.isAlive) {//未存在
            EasyTouchView.isAlive = true;
            mWindowManager.addView(mEasyTouchView, layoutParams);
            Log.d(TAG, "SHOW");
        }
    }

    public static void hideEasyTouchView(){
        if (EasyTouchView.isAlive) {//已存在
            EasyTouchView.isAlive = false;
            mWindowManager.removeView(mEasyTouchView);
            Log.d(TAG, "hide");
        }
    }

    public static void showMutiTaskView(){
        WindowManager.LayoutParams layoutParams = mutiTaskView.getmLayoutParams();
        mWindowManager.addView(mutiTaskView, layoutParams);
    }

    public static void hideMutiTaskView(){
        mWindowManager.removeView(mutiTaskView);
    }

    public static void openMutiTaskWindow(){
        if (isServiceRunning){//服务已开启
            hideEasyTouchView();
            showMutiTaskView();
        }
        else{
            try {
                mContext.startActivity(new Intent(mContext, MainActivity.class));
                showPromptingDialog();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void showPromptingDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle(mContext.getResources().getString(R.string.hint));
        alertDialog.setMessage(mContext.getResources().getString(R.string.hint_content_accessibility));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                Toast.makeText(mContext, R.string.toast, Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ;
            }
        });
        alertDialog.show();
    }

    public static void setIsServiceRunning(boolean isRunning){
        isServiceRunning = isRunning;
    }
}
