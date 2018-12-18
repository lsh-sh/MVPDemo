package com.bagevent.http;

import com.bagevent.app.Constant;

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
public interface ServiceApi {

    /**
     * 网络请求地址
     */
    String BASE_URL = "https://www.bagevent.com/api/v1/"; //网络请求的URL

    /**
     * 登录
     */
    String LOGIN = "login.do?loginSource=4&loginType=0&" + Constant.ACCESS_TOKEN + Constant.ACCESS_SERCRET;

    /**
     * 自动登录
     */
    String AUTO_LOGIN = "login.do?loginSource=4&loginType=1&" + Constant.ACCESS_TOKEN + Constant.ACCESS_SERCRET;

    /**
     * 是否存在未读消息
     */
    String EXIST_UNREAD_MSG = "message/findExistNewMessage?" + Constant.ACCESS_TOKEN + Constant.ACCESS_SERCRET;

    /**
     * 活动列表
     */
    String EVENT_LIST = "events/";

}
