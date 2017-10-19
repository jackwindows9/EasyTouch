package com.example.myeasytouch.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myeasytouch.R;
import com.example.myeasytouch.event.MessageEvent;
import com.example.myeasytouch.holder.MyViewHolder;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by 司维 on 2017/10/11.
 */

public class MutiTaskView extends RelativeLayout {
    private final static String TAG = "MutiTaskView";
    public final static String GLOBAL_ACTION_BACK = "BACK";
    public final static String GLOBAL_ACTION_HOME = "HOME";
    public final static String GLOBAL_ACTION_RECENTS = "RECENTS";
    private WindowManager.LayoutParams mLayoutParams;
    private LinearLayout linearLayout_mutitaskview;
    private ImageView imageView_home;
    private ImageView imageView_flashlight;
    private ImageView imageView_back;
    private ImageView imageView_recents;

    public MutiTaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.mutitask_view_layout, this);
        linearLayout_mutitaskview = findViewById(R.id.linearlayout_view);
        initImageView();
        initLayoutParams();
    }

    public void initImageView(){
        imageView_home = findViewById(R.id.image_home);
        imageView_flashlight = findViewById(R.id.image_flashlight);
        imageView_back = findViewById(R.id.image_back);
        imageView_recents = findViewById(R.id.image_recents);
        imageView_home.setOnClickListener(onClickListener);
        imageView_flashlight.setOnClickListener(onClickListener);
        imageView_back.setOnClickListener(onClickListener);
        imageView_recents.setOnClickListener(onClickListener);
    }

    public void initLayoutParams(){
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0,
                0,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
        );
        mLayoutParams.gravity = Gravity.CENTER;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (!isInMyView(event)){//未点击view中区域
                MyViewHolder.hideMutiTaskView();
                MyViewHolder.showEasyTouchView();
                Log.d(TAG, "onTouchEvent");
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean isInMyView(MotionEvent event){
        int[] location = new int[2];
        linearLayout_mutitaskview.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (event.getX() > x &&
                event.getX() < (x + linearLayout_mutitaskview.getWidth()) &&
                event.getY() > y &&
                event.getY() < (y + linearLayout_mutitaskview.getHeight())
                ) {
            return true;
        }
        else{
            return false;
        }
    }

    public WindowManager.LayoutParams getmLayoutParams() {
        return mLayoutParams;
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickListener");
            switch(view.getId()){
                case R.id.image_home:
                    EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_HOME));
                    MyViewHolder.hideMutiTaskView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.image_back:
                    EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_BACK));
                    MyViewHolder.hideMutiTaskView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.image_recents:
                    EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_RECENTS));
                    MyViewHolder.hideMutiTaskView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.image_flashlight:
                    MyViewHolder.hideMutiTaskView();
                    MyViewHolder.showEasyTouchView();
                    break;
                default:
                    break;
            }
        }
    };
}
