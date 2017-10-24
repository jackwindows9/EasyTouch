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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    public final static String GLOBAL_ACTION_RECENTS = "RECENTS";
    private Context mContext;
    private WindowManager.LayoutParams mLayoutParams;
    private ImageView imageView_home;
    private ImageView imageView_favor;
    private ImageView imageView_back;
    private ImageView imageView_recents;

    public MutiTaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.mutitask_view_layout, this);
        initImageView();
        initLayoutParams();
    }

    public void initImageView(){
        imageView_home = findViewById(R.id.image_home);
        imageView_favor = findViewById(R.id.image_star);
        imageView_back = findViewById(R.id.image_back);
        imageView_recents = findViewById(R.id.image_recents);
        imageView_home.setOnClickListener(onClickListener);
        imageView_favor.setOnClickListener(onClickListener);
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
        MyViewHolder.hideMutiTaskView();
        MyViewHolder.showEasyTouchView();
        Log.d(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
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
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    mContext.startActivity(intent);
                    break;
                case R.id.image_back:
                    handleAccessibilityClick(GLOBAL_ACTION_BACK);
                    break;
                case R.id.image_recents:
                    handleAccessibilityClick(GLOBAL_ACTION_RECENTS);
                    break;
                case R.id.image_star:
                    break;
                default:
                    break;
            }
            MyViewHolder.hideMutiTaskView();
            MyViewHolder.showEasyTouchView();
        }
    };

    private void handleAccessibilityClick(String message){
        if (MyViewHolder.isServiceRunning) {
            if (message == GLOBAL_ACTION_BACK) {
                EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_BACK));
            }
            if (message == GLOBAL_ACTION_RECENTS){
                EventBus.getDefault().post(new MessageEvent(GLOBAL_ACTION_RECENTS));
            }
        }
        else{
            Toast.makeText(mContext, R.string.toast, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }
}
