package com.bagevent.page;

import com.bagevent.R;
import com.bagevent.bean.UserInfo;
import com.bagevent.core.page.CommonActivity;
import com.bagevent.presenter.WelcomePresenter;
import com.bagevent.utils.PageTool;
import com.bagevent.view.WelcomeView;
import com.jaeger.library.StatusBarUtil;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/7
 * <p>
 * desp 欢迎页面
 * <p>
 * <p>
 * =============================================
 */
public class WelcomeActivity extends CommonActivity<WelcomePresenter, WelcomeView> implements WelcomeView {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected WelcomeView getVIew() {
        return this;
    }

    @Override
    protected WelcomePresenter getPresenter() {
        return new WelcomePresenter();
    }

    @Override
    public void toLoginPage() {
        PageTool.go(this, LoginActivity.class);
        finishPage();
    }

    @Override
    public void toMainPage(UserInfo userInfo) {
        PageTool.saveUserInfo(userInfo);
        PageTool.go(this, MainActivity.class);
        finishPage();
    }

    @Override
    protected void initContent() {
        StatusBarUtil.setTransparent(this);
        presenter.dispatchJumpPage();
    }

    @Override
    public void noNetworkView() {

    }
}
