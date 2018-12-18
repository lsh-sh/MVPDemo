package com.bagevent.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bagevent.R;
import com.bagevent.app.Constant;
import com.bagevent.app.Keys;
import com.bagevent.bean.UserInfo;
import com.bagevent.core.page.BaseActivity;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp 操作页面的方法
 * <p>
 * <p>
 * =============================================
 */
public class PageTool {


    /**
     * 页面调转
     *
     * @param activity
     * @param clzz
     * @param bundle
     */
    public static void go(Activity activity, Class clzz, Bundle bundle) {
        Intent intent = new Intent(activity, clzz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void go(Activity activity, Class clzz) {
        go(activity, clzz, null);
    }

    /**
     * 带返回结果的页面调转
     *
     * @param activity
     * @param clzz
     * @param requestCode
     * @param bundle
     */
    public static void goForResult(Activity activity, Class clzz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(activity, clzz);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void goForResult(Activity activity, Class clzz, int requestCode) {
        goForResult(activity, clzz, requestCode, null);
    }


    /**
     * 页面进入动画
     *
     * @param baseActivity
     */
    public static void setStartActivityAnimation(BaseActivity baseActivity) {
        setStartActivityAnimation(baseActivity, baseActivity.getActivityAnimation());
    }

    public static void setStartActivityAnimation(BaseActivity baseActivity, int animationId) {
        switch (animationId) {
            case Constant.ACTIVITY_ANIMATION_LEFT_RIGHT:
                baseActivity.overridePendingTransition(R.anim.into_right_to_left, R.anim.out_right_to_left);
                break;
        }
    }

    /**
     * 页面结束动画
     *
     * @param baseActivity
     */
    public static void setFinishActivityAnimation(BaseActivity baseActivity) {
        setFinishActivityAnimation(baseActivity, baseActivity.getActivityAnimation());
    }

    public static void setFinishActivityAnimation(BaseActivity baseActivity, int animationId) {
        switch (animationId) {
            case Constant.ACTIVITY_ANIMATION_LEFT_RIGHT:
                baseActivity.overridePendingTransition(R.anim.into_left_to_right, R.anim.out_left_to_right);
                break;
        }

    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     */
    public static void saveUserInfo(UserInfo userInfo) {
        ShpUtil.putUserString(Keys.KEY_USER_ID, userInfo.getReturnObj().getUserId() + "");
        ShpUtil.putUserString(Keys.KEY_EMAIL, userInfo.getReturnObj().getEmail());
        ShpUtil.putUserString(Keys.KEY_CELLPHONE, userInfo.getReturnObj().getCellphone());
        ShpUtil.putUserString(Keys.KEY_USER_NAME, userInfo.getReturnObj().getUserName());
        ShpUtil.putUserString(Keys.KEY_AVATAR, userInfo.getReturnObj().getAvatar());
        ShpUtil.putUserString(Keys.KEY_SOURCE, userInfo.getReturnObj().getSource() + "");
        ShpUtil.putUserString(Keys.KEY_TOKEN, userInfo.getReturnObj().getToken());
        ShpUtil.putUserString(Keys.KEY_STATE, userInfo.getReturnObj().getState() + "");
        ShpUtil.putUserString(Keys.KEY_AUTOLOGIN_TOKEN, userInfo.getReturnObj().getAutoLoginToken());
        ShpUtil.putUserString(Keys.KEY_AUTOLOGIN_EXPIRETIME, userInfo.getReturnObj().getAutoLoginExpireTime() + "");
    }

    /**
     * 向容器里添加view
     *
     * @param viewGroup
     * @param view
     */
    public static void loadViewToConstainer(ViewGroup viewGroup, View view) {
        loadViewToConstainer(viewGroup, view, null);
    }

    public static void loadViewToConstainer(ViewGroup viewGroup, View view, ViewGroup.LayoutParams layoutParams) {
        if (viewGroup != null && view != null) {
            viewGroup.removeAllViews();
            if (layoutParams != null) {
                viewGroup.addView(view, layoutParams);
            } else {
                viewGroup.addView(view);
            }
        }
    }

    /**
     * 清楚用户信息
     */
    public static void clearUserInfo() {
        ShpUtil.clearUser();
    }
}
