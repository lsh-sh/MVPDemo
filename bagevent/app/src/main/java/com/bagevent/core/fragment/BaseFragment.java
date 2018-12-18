package com.bagevent.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagevent.core.presenter.FragmentPresenterImpl;
import com.bagevent.core.view.IView;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.http.HttpMsg;
import com.bagevent.utils.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
public abstract class BaseFragment<P extends FragmentPresenterImpl, V extends IView> extends Fragment {
    protected P presenter;
    protected V view;
    protected boolean isCreated = false;
    private Unbinder bind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    private View contentView;

    /**
     * 设置加载的布局Id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化布局控件和设置监听事件，获取getArguments相关数据在这里操作
     */
    protected void initLayout() {

    }


    /**
     * 广播列表
     *
     * @return
     */
    protected short[] getActions() {
        return null;
    }

    /**
     * 接收广播
     *
     * @param msgEvent
     */
    protected void receiveMessage(MessageEvent msgEvent) {

    }

    /**
     * 为 fragment 绑定UI视图时，该方法被回调
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(setLayoutId(), container, false);
        presenter = getPresenter();
        view = getVIew();
        if (presenter != null && view != null) {
            presenter.bind(view, this);
        }
        bind = ButterKnife.bind(this, contentView);
        return contentView;
    }


    /**
     * onCreateView执行完后立即执行。
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
        //**注册监听事件**
        register_event();
        //**初始化页面布局，相关变量**
        initLayout();
        // 初始化
        isCanLoadData();
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
        if (!isCreated) {
            return;
        }
    }

    @Override
    public void onDestroyView() {
        //******状态复位******
        isInit = false;
        isLoad = false;
        if (presenter != null) {
            presenter.unbind();
        }
        if (bind != null) {
            bind.unbind();
        }
        unregister_event();
        super.onDestroyView();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventPostThread(MessageEvent msgEvent) {
        receiveMessage(msgEvent);
    }

    /**
     * 获取设置的布局
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    /**
     * 订阅相关事件
     */
    private void register_event() {
        //******订阅事件******
        short[] actions = getActions();
        if (actions != null && actions.length != 0) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    /**
     * 取消订阅相关事件
     */
    private void unregister_event() {
        //******取消订阅******
        short[] actions = getActions();
        if (actions != null && actions.length != 0) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);//反注册EventBus
            }
        }
    }


    @Override
    public Context getContext() {

        if (super.getContext() == null) {
            return AppUtil.getContext();
        }
        return super.getContext();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     * 3.是否已经懒加载过
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint() && !isLoad) {
            if (presenter != null) {
                presenter.lazyLoad();
            }
            isLoad = true;
        }
    }

    protected abstract V getVIew();

    protected abstract P getPresenter();
}
