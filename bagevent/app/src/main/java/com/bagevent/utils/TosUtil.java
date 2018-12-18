package com.bagevent.utils;

import android.view.Gravity;
import android.widget.Toast;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp 显示toast的工具类
 * <p>
 * <p>
 * =============================================
 */
public class TosUtil {
    public static void show(String msg) {
        Toast.makeText(AppUtil.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(int stringId) {
        String msg = AppUtil.getContext().getString(stringId);
        show(msg);
    }

    public static void showCenter(String msg) {
        Toast toast = Toast.makeText(AppUtil.getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCenter(int stringId) {
        String msg = AppUtil.getContext().getString(stringId);
        showCenter(msg);
    }
}