package com.example.myeasytouch.holder;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.example.myeasytouch.instance.WindowManagerInstance;
import com.example.myeasytouch.view.EasyTouchView;
import com.example.myeasytouch.view.MutiTaskMainView;

/**
 * Created by 司维 on 2017/10/11.
 */

public class MyViewHolder {
    private final static String TAG = "MyViewHolder";
    private static Context applicationContext;
    private static EasyTouchView mEasyTouchView;
    private static MutiTaskMainView mutiTaskMainView;
    private static WindowManager mWindowManager;
    public static boolean isServiceRunning = false;

    public MyViewHolder(Context context){//只允许初始化一次，因为只能有一个applicationContext
        applicationContext = context.getApplicationContext();
        this.mEasyTouchView = new EasyTouchView(applicationContext, null);
        this.mutiTaskMainView = new MutiTaskMainView(applicationContext, null);
        WindowManagerInstance.setApplicationContext(applicationContext);
        mWindowManager = WindowManagerInstance.newInstance();
    }

    public static void showEasyTouchView(){
        if (!EasyTouchView.isAlive) {//未存在
            WindowManager.LayoutParams layoutParams = mEasyTouchView.getmLayoutParams();
            EasyTouchView.isAlive = true;
            mWindowManager.addView(mEasyTouchView, layoutParams);
            Log.d(TAG, "show");
        }
    }

    public static void hideEasyTouchView(){
        if (EasyTouchView.isAlive) {//已存在
            EasyTouchView.isAlive = false;
            mWindowManager.removeView(mEasyTouchView);
            Log.d(TAG, "hide");
        }
    }

    public static void showMutiTaskMainView(){
        WindowManager.LayoutParams layoutParams = mutiTaskMainView.getmLayoutParams();
        mWindowManager.addView(mutiTaskMainView, layoutParams);
    }

    public static void hideMutiTaskMainView(){
        mWindowManager.removeView(mutiTaskMainView);
    }

    public static void openMutiTaskWindow(){
        hideEasyTouchView();
        showMutiTaskMainView();
    }

    public static void setIsServiceRunning(boolean isRunning){
        isServiceRunning = isRunning;
    }

    //    private static void showPromptingDialog(){
    //        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
    //        alertDialog.setTitle(mContext.getResources().getString(R.string.hint));
    //        alertDialog.setMessage(mContext.getResources().getString(R.string.hint_content_accessibility));
    //        alertDialog.setCancelable(false);
    //        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    //                Toast.makeText(mContext, R.string.toast, Toast.LENGTH_LONG).show();
    //                isDialogShow = false;
    //            }
    //        });
    //        alertDialog.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                isDialogShow = false;
    //            }
    //        });
    //        alertDialog.show();
    //    }
}
