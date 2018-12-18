package com.bagevent.http.exception;

import com.alibaba.fastjson.JSONException;
import com.bagevent.utils.ErrCodeUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

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
public class HttpResultFunc implements Function<Throwable, Observable<String>> {
    @Override
    public Observable<String> apply(Throwable throwable) {
        ApiException ex;
        if (throwable instanceof JSONException) {
            ex = new ApiException(throwable, HttpError.PARSE_ERROR);
            ex.setError(ErrCodeUtil.ErrCode(HttpError.PARSE_ERROR));
        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            ex = new ApiException(throwable, HttpError.NETWORK_ERROR);
            ex.setError(ErrCodeUtil.ErrCode(HttpError.NETWORK_ERROR));
        } else if (throwable instanceof SSLHandshakeException) {
            ex = new ApiException(throwable, HttpError.SSL_ERROR);
            ex.setError(ErrCodeUtil.ErrCode(HttpError.SSL_ERROR));
        } else {
            ex = new ApiException(throwable, HttpError.UNKNOWN);
            ex.setError(ErrCodeUtil.ErrCode(HttpError.UNKNOWN));
        }
        return Observable.error(ex);
    }
}
