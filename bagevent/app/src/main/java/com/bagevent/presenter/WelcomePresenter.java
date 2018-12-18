package com.bagevent.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bagevent.app.Keys;
import com.bagevent.bean.UserInfo;
import com.bagevent.core.presenter.ActivityPresenterImpl;
import com.bagevent.http.DealWithNone;
import com.bagevent.http.HttpMsg;
import com.bagevent.http.RetrofitTaskNone;
import com.bagevent.http.ServiceApi;
import com.bagevent.http.exception.ApiException;
import com.bagevent.http.exception.HttpError;
import com.bagevent.utils.APITool;
import com.bagevent.utils.AppUtil;
import com.bagevent.utils.NetUtil;
import com.bagevent.utils.ShpUtil;
import com.bagevent.view.WelcomeView;

import io.reactivex.Observable;
import io.reactivex.Observer;

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
public class WelcomePresenter extends ActivityPresenterImpl<WelcomeView> {

    private String eventId;
    private String collectionId;
    private String autoToken;
    private String userId;

    /**
     * 进行页面调转
     */
    public void dispatchJumpPage() {
        Bundle jpushBundle = activity.getIntent().getExtras();
        if (jpushBundle != null) {
            eventId = jpushBundle.getString(Keys.KEY_EVENT_ID);
        }

        AppUtil.runOnUIDelay(new Runnable() {
            @Override
            public void run() {
                String autoCollect = ShpUtil.getUserString(Keys.KEY_AUTO_COLLECT, "");
                if (TextUtils.isEmpty(autoCollect)) {
                    handleNoAutoCollect();
                } else {
                    handleHasAutoCollect(autoCollect);
                }
            }
        }, 1000);
    }

    private void handleNoAutoCollect() {
        if (ShpUtil.getUserString(Keys.KEY_AUTOLOGIN_TOKEN, "").isEmpty()) {
            view.toLoginPage();
        } else {
            if (NetUtil.isConnected()) {
                autoLogin();
            } else {
                view.toLoginPage();
            }
        }
    }

    private void handleHasAutoCollect(String autoCollect) {

        // TODO: 2018/11/26 处理有采集信息的情况
        String[] collects = autoCollect.split(":");
        eventId = collects[1];
        collectionId = collects[2];
        userId = collects[3];
        autoToken = collects[4];
        autoToken = collects[4];
    }

    /**
     * 自动登录
     */
    public void autoLogin() {

        HttpMsg httpMsg = new HttpMsg(ServiceApi.AUTO_LOGIN);
        String loginToken = ShpUtil.getUserString(Keys.KEY_AUTOLOGIN_TOKEN, "");
        new RetrofitTaskNone().getServiceData(APITool.autoLogin(loginToken), new DealWithNone() {
            @Override
            public void onSuccess(HttpMsg httpMsg, JSONObject result) {
                view.toMainPage(JSON.parseObject(result.toJSONString(), UserInfo.class));
            }

            @Override
            public void onFailed(HttpMsg httpMsg, ApiException ex) {
                if (ex.getCode() == HttpError.NETWORK_ERROR) {
                }

            }
        }, httpMsg);

    }
}
