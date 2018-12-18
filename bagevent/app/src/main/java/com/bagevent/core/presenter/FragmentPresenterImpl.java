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
 * time 2018/11/7
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public abstract class FragmentPresenterImpl<T extends IView> implements IFragmentPresenter<T> {
    protected T view;
    protected Fragment fragment;

    /**
     * 懒加载数据(联网操作、载入大量数据)
     */
    public abstract void lazyLoad();

    @Override
    public void bind(T iView, Fragment fragment) {
        this.view = iView;
        this.fragment = fragment;
    }

    @Override
    public void unbind() {
        this.view = null;
        this.fragment = null;
    }
}
