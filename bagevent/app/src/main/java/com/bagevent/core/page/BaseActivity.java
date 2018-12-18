package com.bagevent.core.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.bagevent.app.AppManager;
import com.bagevent.app.Constant;
import com.bagevent.core.presenter.ActivityPresenterImpl;
import com.bagevent.core.view.IView;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.utils.PageTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public abstract class BaseActivity<P extends ActivityPresenterImpl, V extends IView> extends FragmentActivity {
    protected P presenter;
    protected V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);
        PageTool.setStartActivityAnimation(this);

        initBeginView();

        setContentView(setLayoutId());

        presenter = getPresenter();
        view = getVIew();
        if (presenter != null && view != null) {
            presenter.bind(view, this);
        }

        initLayout();
    }

    protected abstract int setLayoutId();

    protected abstract void initLayout();

    protected abstract V getVIew();

    protected abstract P getPresenter();

    protected void initBeginView() {


    }

    protected void receiveMessage(MessageEvent event) {

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        receiveMessage(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        register_event();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregister_event();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unbind();
        }
    }

    @Override
    public void finish() {
        super.finish();
        PageTool.setFinishActivityAnimation(this);

    }


    protected void finishPage() {
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 页面进出动画
     *
     * @return
     */
    public int getActivityAnimation() {
        return Constant.ACTIVITY_ANIMATION_LEFT_RIGHT;
    }
}
