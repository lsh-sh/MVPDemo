package com.bagevent.page;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bagevent.R;
import com.bagevent.app.Keys;
import com.bagevent.bean.UserInfo;
import com.bagevent.core.page.CommonActivity;
import com.bagevent.presenter.LoginPresenter;
import com.bagevent.utils.PageTool;
import com.bagevent.utils.ShpUtil;
import com.bagevent.view.LoginView;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

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
public class LoginActivity extends CommonActivity<LoginPresenter, LoginView> implements LoginView {
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initContent() {
        StatusBarUtil.setTransparent(this);

        String userAccount = ShpUtil.getAccountString(Keys.KEY_ACCOUNT_INFO, "");
        if (TextUtils.isEmpty(userAccount)) {
            etLoginUsername.setText("");
        } else {
            etLoginUsername.setText(userAccount);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginView getVIew() {
        return this;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    public String getUserName() {
        return etLoginUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etLoginPassword.getText().toString().trim();
    }

    @Override
    public void setBtnLoginTxt(int id) {
        btnLogin.setText(id);
    }

    @Override
    public void toMainPage(UserInfo userInfo) {
        PageTool.saveUserInfo(userInfo);
        ShpUtil.putAccountString(Keys.KEY_ACCOUNT_INFO, getUserName());
        PageTool.go(this, MainActivity.class);
        finishPage();
    }


    @OnClick({R.id.btn_login, R.id.iv_phone_login,
            R.id.iv_wechat_login, R.id.iv_saoma_login,
            R.id.tv_register, R.id.tv_forget_pwd})
    public void onClic(View view) {
        switch (view.getId()) {
            case R.id.btn_login: //登录
                if (presenter.checkAccount()) {
                    setBtnLoginTxt(R.string.btn_logining);
                    presenter.login();
                }
                break;
            case R.id.tv_register: //立即注册
                // TODO: 2018/11/26 立即注册
                break;
            case R.id.tv_forget_pwd: //忘记密码
                break;
            case R.id.iv_phone_login: //手机登录
                break;
            case R.id.iv_wechat_login: //微信登录
                break;
            case R.id.iv_saoma_login: //扫描登录
                break;
        }
    }


    @Override
    public void noNetworkView() {

    }
}
