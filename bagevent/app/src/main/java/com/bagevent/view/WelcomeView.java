package com.bagevent.view;

import com.bagevent.bean.UserInfo;
import com.bagevent.core.view.IView;

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
public interface WelcomeView extends IView {
    void toLoginPage();
    void toMainPage(UserInfo userInfo);
}
