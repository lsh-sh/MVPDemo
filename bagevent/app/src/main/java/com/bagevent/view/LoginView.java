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
public interface LoginView extends IView {
    String getUserName();
    String getPassword();
    void setBtnLoginTxt(int id);
    void toMainPage(UserInfo userInfo);
}
