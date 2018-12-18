package com.bagevent.presenter;

import android.content.IntentFilter;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.badoo.mobile.util.WeakHandler;
import com.bagevent.app.Keys;
import com.bagevent.core.presenter.ActivityPresenterImpl;
import com.bagevent.http.DealWithNone;
import com.bagevent.http.HttpMsg;
import com.bagevent.http.RetrofitTaskNone;
import com.bagevent.http.ServiceApi;
import com.bagevent.http.exception.ApiException;
import com.bagevent.receiver.NetWorkReceiver;
import com.bagevent.utils.APITool;
import com.bagevent.utils.AppUtil;
import com.bagevent.utils.ShpUtil;
import com.bagevent.view.MainView;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/26
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class MainPresenter extends ActivityPresenterImpl<MainView> {

    private WeakHandler weakHandler;
    private String userId;
    private NetWorkReceiver netWorkReceiver;
    private RetrofitTaskNone retrofitTaskNone;

    public void init() {
        weakHandler = new WeakHandler();
        userId = ShpUtil.getUserString(Keys.KEY_USER_ID, "");
        setJPushAlias();
        readMessageFromServer();
        registerNetWorkBroadcast();
    }

    /**
     * 注册网络变化的接受者
     */
    private void registerNetWorkBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkReceiver = new NetWorkReceiver();
        activity.registerReceiver(netWorkReceiver, filter);
    }

    /**
     * 从后台读取信息列表是否有未读的消息
     */
    private void readMessageFromServer() {
        retrofitTaskNone = new RetrofitTaskNone();
        retrofitTaskNone.getServiceData(APITool.findExistNewMessage(userId), new DealWithNone() {
            @Override
            public void onSuccess(HttpMsg httpMsg, JSONObject result) {
                if (result.containsKey("respObject")) {
                    boolean respObject = result.getBooleanValue("respObject");
                    if (respObject) {
                        view.showMessagePoint();
                    }
                }
            }

            @Override
            public void onFailed(HttpMsg httpMsg, ApiException ex) {

            }
        }, new HttpMsg(ServiceApi.EXIST_UNREAD_MSG));
    }

    /**
     * 设置极光推送的别名
     */
    private void setJPushAlias() {
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        if (TextUtils.isEmpty(ShpUtil.getUserString("alias" + userId, ""))) {
            weakHandler.post(new Runnable() {
                @Override
                public void run() {
                    JPushInterface.setAlias(AppUtil.getContext(), userId, mAliasCallback);
                }
            });
        }
    }


    private TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, final String alias, Set<String> set) {
            switch (code) {
                case 0:
                    // Toast.makeText(HomePage.this, "Set Alias Success!", Toast.LENGTH_SHORT).show();
                    ShpUtil.putUserString("alias" + alias, alias);
                    break;
                case 6002:
                    weakHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JPushInterface.setAlias(AppUtil.getContext(), alias, mAliasCallback);
                        }
                    }, 1000 * 60);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void unbind() {
        retrofitTaskNone.cancel();
        activity.unregisterReceiver(netWorkReceiver);
        super.unbind();
    }
}
