package com.bagevent.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.bagevent.BuildConfig;
import com.bagevent.R;
import com.bagevent.http.exception.ApiException;
import com.bagevent.http.exception.HttpError;
import com.bagevent.http.exception.HttpResultFunc;
import com.bagevent.utils.ErrCodeUtil;
import com.bagevent.utils.LogUtil;
import com.bagevent.utils.NetUtil;
import com.bagevent.utils.TosUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
public class RetrofitTaskNone {

    private static DealWithNone dealWithNone;
    private static HttpMsg httpMsg;


    public void getServiceData(Observable<String> observable, final DealWithNone dealWithNone, final HttpMsg httpMsg) {
        RetrofitTaskNone.dealWithNone = dealWithNone;
        RetrofitTaskNone.httpMsg = httpMsg;


        if (!NetUtil.isConnected()) {
            showFailed(new ApiException(HttpError.NETWORK_ERROR, ErrCodeUtil.ErrCode(HttpError.NETWORK_ERROR)));
        } else {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(new HttpResultFunc())
                    .subscribe(new SimpleObserver() {
                        @Override
                        protected void onSuccess(String result) {
                            if (!TextUtils.isEmpty(result)) {
                                if (BuildConfig.DEBUG) {
                                    LogUtil.e(httpMsg.getUrl());
                                    LogUtil.e(result);
                                }
                                JSONObject jsonObject = JSON.parseObject(result);
                                if (jsonObject.containsKey("code")) {
                                    int code = jsonObject.getIntValue("code");
                                    if (code == 200) {
                                        dealWithJson(jsonObject);
                                    } else {
                                        showFailed(new ApiException(code, ErrCodeUtil.ErrCode(code)));
                                    }
                                } else if (jsonObject.containsKey("retStatus")) {
                                    int retStatus = jsonObject.getIntValue("retStatus");
                                    if (retStatus == 200) {
                                        dealWithJson(jsonObject);
                                    } else {
                                        showFailed(new ApiException(HttpError.UNKNOWN, ErrCodeUtil.ErrCode(HttpError.UNKNOWN)));
                                    }
                                }
                            }
                        }

                        @Override
                        protected void onError(ApiException ex) {
                            super.onError(ex);
                        }

                        private void dealWithJson(JSONObject jsonObject) {
                            try {
                                dealWithNone.onSuccess(httpMsg, jsonObject);
                            } catch (JSONException e) {
                                showFailed(new ApiException(HttpError.PARSE_ERROR, ErrCodeUtil.ErrCode(HttpError.PARSE_ERROR)));
                            }

                        }
                    });
        }

//        if (NetUtil.isConnected()) {
//            observable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .onErrorResumeNext(new HttpResultFunc())
//                    .subscribe(simpleObserver);
//        } else {
//            simpleObserver.onError(new ApiException(HttpError.NETWORK_ERROR, ErrCodeUtil.ErrCode(HttpError.NETWORK_ERROR)));
//        }
    }

    private void showFailed(ApiException ex) {
        //网络连接失败提示信息
        if (ex.getCode() == HttpError.NETWORK_ERROR) {
            TosUtil.showCenter(R.string.http_connect_failed);
        }

        dealWithNone.onFailed(httpMsg, ex);
    }


    private SimpleObserver simpleObserver = new SimpleObserver() {

        @Override
        protected void onSuccess(String result) {
            if (!TextUtils.isEmpty(result)) {
                if (BuildConfig.DEBUG) {
                    LogUtil.e(httpMsg.getUrl());
                    LogUtil.e(result);
                }
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.containsKey("code")) {
                    int code = jsonObject.getIntValue("code");
                    if (code == 200) {
                        dealWithJson(jsonObject);
                    } else {
                        showFailed(new ApiException(code, ErrCodeUtil.ErrCode(code)));
                    }
                } else if (jsonObject.containsKey("retStatus")) {
                    int retStatus = jsonObject.getIntValue("retStatus");
                    if (retStatus == 200) {
                        dealWithJson(jsonObject);
                    } else {
                        showFailed(new ApiException(HttpError.UNKNOWN, ErrCodeUtil.ErrCode(HttpError.UNKNOWN)));
                    }
                }
            }
        }

        @Override
        protected void onError(ApiException ex) {
            showFailed(ex);
        }


        private void dealWithJson(JSONObject jsonObject) {
            try {
                dealWithNone.onSuccess(httpMsg, jsonObject);
            } catch (JSONException e) {
                showFailed(new ApiException(HttpError.PARSE_ERROR, ErrCodeUtil.ErrCode(HttpError.PARSE_ERROR)));
            }

        }


    };

    /**
     * 取消请求
     */
    public void cancel() {
        //取消请求
        dealWithNone = null;
        httpMsg = null;
        simpleObserver.cancelRequest();

    }
}
