package com.bagevent.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bagevent.R;
import com.bagevent.bean.UserInfo;
import com.bagevent.core.presenter.ActivityPresenterImpl;
import com.bagevent.http.DealWithNone;
import com.bagevent.http.HttpMsg;
import com.bagevent.http.RetrofitTaskNone;
import com.bagevent.http.ServiceApi;
import com.bagevent.http.exception.ApiException;
import com.bagevent.http.exception.HttpError;
import com.bagevent.utils.APITool;
import com.bagevent.utils.TosUtil;
import com.bagevent.view.LoginView;

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
public class LoginPresenter extends ActivityPresenterImpl<LoginView> {

    /**
     * 检查账户
     *
     * @return true 输入合法可以进行登录
     */
    public boolean checkAccount() {
        if (!TextUtils.isEmpty(view.getUserName()) && !TextUtils.isEmpty(view.getPassword())) {
            return true;
        } else {
            TosUtil.showCenter(R.string.msg_name_pwd_null);
            return false;
        }
    }

    /**
     * 进行登录
     */
    public void login() {
        HttpMsg httpMsg = new HttpMsg(ServiceApi.LOGIN);
        new RetrofitTaskNone().getServiceData(APITool.login(view.getUserName(), view.getPassword()), new DealWithNone() {
            @Override
            public void onSuccess(HttpMsg httpMsg, JSONObject result) {
                view.toMainPage(JSON.parseObject(result.toJSONString(), UserInfo.class));
            }

            @Override
            public void onFailed(HttpMsg httpMsg, ApiException ex) {
                if (ex.getCode() != HttpError.NETWORK_ERROR) {
                    TosUtil.show(ex.getError());
                }
                view.setBtnLoginTxt(R.string.btn_login);
            }
        }, httpMsg);
    }

}
