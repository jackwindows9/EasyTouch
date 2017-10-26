package com.example.myeasytouch.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
    private static int viewWidth;
    private static int viewHeight;
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = this.getWidth();
        viewHeight = this.getHeight();
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
                mLayoutParams.x = (int) (xInScreen - xInView);
                mLayoutParams.y = (int) (yInScreen - yInView);
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (xInScreen == xDownInScreen && yInScreen == yDownInScreen){//表示并未发生move事件，可看作点击事件
                    MyViewHolder.openMutiTaskWindow();
                }
                else{//发生了move事件，需要进行回归两边的动画
                    startViewPositionAnimator();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void updateViewPosition() {
        WindowManager windowManager = WindowManagerInstance.newInstance();
        windowManager.updateViewLayout(this, mLayoutParams);
    }

    public void startViewPositionAnimator(){
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1f);
        //Duration 默认300ms
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                if (mLayoutParams.x + viewWidth / 2 <= screenWidth / 2){//向左
                    mLayoutParams.x = (int) ((float)mLayoutParams.x * (1 - value));
                }
                else{
                    mLayoutParams.x += (int) ((float)(screenWidth - mLayoutParams.x) * value);
                }
                updateViewPosition();
            }
        });
        valueAnimator.start();
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
