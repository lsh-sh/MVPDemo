package com.bagevent.core.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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
public interface IFragmentPresenter<V extends IView> {
    void bind(V iView, Fragment fragment);


    void unbind();
}
