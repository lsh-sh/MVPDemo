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
public interface IActivityPresenter<V extends IView> {
    void bind(V iView, Activity activity);

    void unbind();
}