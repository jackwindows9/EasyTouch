package com.example.myeasytouch.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.myeasytouch.R;
import com.example.myeasytouch.holder.MyViewHolder;
import com.example.myeasytouch.instance.WindowManagerInstance;

/**
 * Created by 司维 on 2017/10/11.
 */

public class EasyTouchView extends LinearLayout {
    private final static String TAG = "EasyTouchView";
    private static int statusBarHeight;
    private WindowManager.LayoutParams mLayoutParams;
    private static int screenWidth;
    private static int screenHeight;
    public static boolean isAlive = false;
    /** 
     *  记录当前手指位置在屏幕上的横坐标值 
     */
    private static float xInScreen;
    /**
     *  记录当前手指位置在屏幕上的纵坐标值 
     */
    private static float yInScreen;
    /**
     *  记录手指按下时在屏幕上的横坐标的值  
     */
    private static float xDownInScreen;
    /**
     *  记录手指按下时在屏幕上的纵坐标的值  
     */
    private static float yDownInScreen;
    /**
     *  记录手指按下时在小悬浮窗的View上的横坐标的值 
     */
    private static float xInView;
    /**
     *  记录手指按下时在小悬浮窗的View上的纵坐标的值 
     */
    private static float yInView;

    public EasyTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        initLayoutParams();
        LayoutInflater.from(context).inflate(R.layout.easytouch_view_layout, this);
    }

    public void initLayoutParams(){
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0,
                0,
                PixelFormat.TRANSPARENT
        );
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mLayoutParams.x = screenWidth;
        mLayoutParams.y = screenHeight / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (xInScreen == xDownInScreen && yInScreen == yDownInScreen){
                    MyViewHolder.openMutiTaskWindow();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void updateViewPosition() {
        mLayoutParams.x = (int) (xInScreen - xInView);
        mLayoutParams.y = (int) (yInScreen - yInView);
        WindowManager windowManager = WindowManagerInstance.newInstance();
        windowManager.updateViewLayout(this, mLayoutParams);
    }

    public float getStatusBarHeight() {//获取状态栏高度
        Rect rectangle = new Rect();
        this.getWindowVisibleDisplayFrame(rectangle);
        statusBarHeight = rectangle.top;
        return statusBarHeight;
    }

    public WindowManager.LayoutParams getmLayoutParams() {
        return mLayoutParams;
    }

}
