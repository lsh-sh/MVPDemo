package com.bagevent.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

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
public class BagEventApp extends Application {
    private static BagEventApp bagEventApp;

    public static BagEventApp getInstance() {
        return bagEventApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bagEventApp = this;
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
