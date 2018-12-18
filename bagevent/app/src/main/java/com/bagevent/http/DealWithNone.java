package com.bagevent.http;

import com.alibaba.fastjson.JSONObject;
import com.bagevent.http.exception.ApiException;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/2
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public interface DealWithNone {

    void onSuccess(HttpMsg httpMsg,JSONObject result);

    void onFailed(HttpMsg httpMsg,ApiException ex);
}
