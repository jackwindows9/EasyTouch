package com.example.myeasytouch.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myeasytouch.R;
import com.example.myeasytouch.holder.MyViewHolder;
import com.example.myeasytouch.view.EasyTouchView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private final static String TAG = "MainActivity";
    private final static int REQUESTCODE = 101;
    private static MyViewHolder myViewHolder;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        mSwitch = (Switch) findViewById(R.id.open_easytouch);
        mSwitch.setOnCheckedChangeListener(this);
        if (myViewHolder == null){
            myViewHolder = new MyViewHolder(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkFloatWindowPermission()) {//已授权
            MyViewHolder.showEasyTouchView();
        }
        if (!EasyTouchView.isAlive){
            Log.d(TAG, "setCheck(false) onResume");
            mSwitch.setChecked(false);
        }
        else{
            Log.d(TAG, "setCheck(true) onResume");
            mSwitch.setChecked(true);
        }
    }

    private boolean checkFloatWindowPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {//已拥有权限
                return true;
            }
        }
        else{//23以下版本只要安装就已被授权，并且无法撤销
            return true;
        }
        return false;
    }

    private void showPromptingDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.hint));
        alertDialog.setMessage(getResources().getString(R.string.hint_content_window));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, REQUESTCODE);
                Toast.makeText(getApplicationContext(), R.string.toast, Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "showPromptingDialog");
                mSwitch.setChecked(false);
            }
        });
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d(TAG, "onCheckedChanged");
        if(b) {
            if (!checkFloatWindowPermission()) {//未授权
                showPromptingDialog();
            }
            else{
                MyViewHolder.showEasyTouchView();
            }
        }
        else{
            MyViewHolder.hideEasyTouchView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE) {
            if (!EasyTouchView.isAlive) {
                mSwitch.setChecked(false);
            }
        }
    }
}
