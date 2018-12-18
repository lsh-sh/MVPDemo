package com.bagevent.utils;

import com.bagevent.app.Constant;
import com.bagevent.http.HttpClient;
import com.bagevent.http.ServiceApi;

import io.reactivex.Observable;

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
public class APITool {

    /**
     * 登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    public static Observable<String> login(String userName, String userPwd) {
        return HttpClient.getInstance().service.login(userName, userPwd);
    }

    /**
     * 自动登录
     *
     * @param autoLoginToken
     * @return
     */
    public static Observable<String> autoLogin(String autoLoginToken) {
        return HttpClient.getInstance().service.autoLogin(autoLoginToken);
    }


    public static Observable<String> findExistNewMessage(String userId) {
        return HttpClient.getInstance().service.findExistNewMessage(ShpUtil.getAutoLoginToken(), userId);
    }

    public static Observable<String> getEventList(String userId) {
        String url = ServiceApi.EVENT_LIST + userId + "?apiType=1&" + Constant.ACCESS_TOKEN + Constant.ACCESS_SERCRET;
        return HttpClient.getInstance().service.getEeventList(ShpUtil.getAutoLoginToken(), url);
    }
}

