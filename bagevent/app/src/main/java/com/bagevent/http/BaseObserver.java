package com.bagevent.http;

import com.bagevent.http.exception.ApiException;
import com.bagevent.http.exception.HttpError;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


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
public abstract class BaseObserver implements Observer<String> {

    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            onError((ApiException) e);
        }else {
            onError(new ApiException(e,HttpError.UNKNOWN));
        }
    }

    @Override
    public void onNext(String t) {
        onSuccess(t);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 错误回调
     * @param ex
     */
    protected abstract void onError(ApiException ex);

    protected abstract void onSuccess(String result);
}
