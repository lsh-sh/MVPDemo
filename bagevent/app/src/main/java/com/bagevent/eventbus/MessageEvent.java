package com.bagevent.eventbus;

import android.os.Bundle;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class MessageEvent {
    private short action;
    private Bundle bundle;

    public MessageEvent(short action, Bundle bundle){
        this.action = action;
        this.bundle = bundle;
    }

    public MessageEvent(short action){
        this(action,null);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public short getAction() {
        return action;
    }
}
