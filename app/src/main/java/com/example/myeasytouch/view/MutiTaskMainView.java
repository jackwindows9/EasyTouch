package com.example.myeasytouch.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myeasytouch.R;
import com.example.myeasytouch.event.MessageEvent;
import com.example.myeasytouch.holder.MyViewHolder;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by 司维 on 2017/10/11.
 */

public class MutiTaskMainView extends RelativeLayout {
    private final static String TAG = "MutiTaskView";
    public final static String GLOBAL_ACTION_BACK = "BACK";
    public final static String GLOBAL_ACTION_RECENTS = "RECENTS";
    private Context mContext;
    private ImageView imageView_home;
    private ImageView imageView_favor;
    private ImageView imageView_back;
    private ImageView imageView_recents;
    private ImageView favor_back;
    private WindowManager.LayoutParams mLayoutParams;
    private LinearLayout linearLayout_mutitaskview;
    private View relativeLayout_mutitaskview;
    private RelativeLayout.LayoutParams relativeLayout_LayoutParams;

    public MutiTaskMainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initLayoutParams();
        initImageView();
    }

    public void initImageView() {
        imageView_home = findViewById(R.id.image_home);
        imageView_favor = findViewById(R.id.image_star);
        imageView_back = findViewById(R.id.image_back);
        imageView_recents = findViewById(R.id.image_recents);
        imageView_home.setOnClickListener(onClickListener);
        imageView_favor.setOnClickListener(onClickListener);
        imageView_back.setOnClickListener(onClickListener);
        imageView_recents.setOnClickListener(onClickListener);
    }

    public void initFavor(){
        favor_back = findViewById(R.id.favor_back);
        favor_back.setOnClickListener(onClickListener);
    }

    public void initLayoutParams() {
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
        relativeLayout_mutitaskview = LayoutInflater.from(mContext).inflate(R.layout.mutitask_main_layout, null);
        relativeLayout_LayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout_LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(relativeLayout_mutitaskview, relativeLayout_LayoutParams);
        linearLayout_mutitaskview = findViewById(R.id.linearlayout_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isInMyView(event)) {//未点击view中区域
                replaceToMainView();
                MyViewHolder.hideMutiTaskMainView();
                MyViewHolder.showEasyTouchView();
                Log.d(TAG, "onTouchEvent");
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean isInMyView(MotionEvent event) {
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
        } else {
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
            switch (view.getId()) {
                case R.id.image_home:
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    mContext.startActivity(intent);
                    MyViewHolder.hideMutiTaskMainView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.image_back:
                    handleAccessibilityClick(GLOBAL_ACTION_BACK);
                    MyViewHolder.hideMutiTaskMainView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.image_recents:
                    handleAccessibilityClick(GLOBAL_ACTION_RECENTS);
                    MyViewHolder.hideMutiTaskMainView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.image_star:
                    replaceToFavorView();
                    break;
                case R.id.favor_back:
                    replaceToMainView();
                    break;
                default:
                    break;
            }
        }
    };

    private void handleAccessibilityClick(String message) {
        if (MyViewHolder.isServiceRunning) {
            if (message == GLOBAL_ACTION_BACK) {
                EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_BACK));
            }
            if (message == GLOBAL_ACTION_RECENTS) {
                EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_RECENTS));
            }
        } else {
            Toast.makeText(mContext, R.string.toast, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }

    private void replaceToFavorView() {
        this.removeView(relativeLayout_mutitaskview);
        relativeLayout_mutitaskview = LayoutInflater.from(mContext).inflate(R.layout.mutitask_favor_layout, null);
        this.addView(relativeLayout_mutitaskview, relativeLayout_LayoutParams);
        initFavor();
    }

    private void replaceToMainView(){
        this.removeView(relativeLayout_mutitaskview);
        relativeLayout_mutitaskview = LayoutInflater.from(mContext).inflate(R.layout.mutitask_main_layout, null);
        this.addView(relativeLayout_mutitaskview, relativeLayout_LayoutParams);
        initImageView();
    }
}
