package com.example.myeasytouch.event;

/**
 * Created by 司维 on 2017/10/13.
 */

public class MessageEvent {
    private String msg;

    public MessageEvent(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
