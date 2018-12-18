package com.bagevent.core.presenter;

import android.app.Activity;
import android.content.Intent;

import com.bagevent.core.view.IView;

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
public class ActivityPresenterImpl<T extends IView> implements IActivityPresenter<T>  {
    protected T view;
    protected Activity activity;
    @Override
    public void bind(T iView,Activity activity) {
        this.view = iView;
        this.activity = activity;
    }

    @Override
    public void unbind() {
        this.view = null;
        this.activity = null;
    }
}
