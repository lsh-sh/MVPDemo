package com.bagevent.http;

import com.bagevent.http.exception.ApiException;

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
public abstract class SimpleObserver extends BaseObserver {

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    protected void onError(ApiException ex) {

    }


    /**
     * 取消网络请求
     */
    protected void cancelRequest() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
