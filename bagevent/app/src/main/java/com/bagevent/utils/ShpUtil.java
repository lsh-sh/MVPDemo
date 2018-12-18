package com.bagevent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bagevent.app.Keys;
import com.bagevent.bean.UserInfo;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/7
 * <p>
 * desp SharedPreferences封装类SPUtils
 * <p>
 * <p>
 * =============================================
 */
public class ShpUtil {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "UserInfo";
    private static final String ACCOUNT_FILE_NAME = "AccountInfo";
    private static Context context;


    private static void initContext() {
        if (context == null) {
            context = AppUtil.getContext();
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     */

    public static void putUserString(String key, String value) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putUserLong(String key, long value) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @return
     */
    public static String getUserString(String key, String value) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static long getUserLong(String key, long value) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, value);
    }

    public static void putAccountString(String key, String value) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(ACCOUNT_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
        // editor.apply();
    }

    public static String getAccountString(String key, String value) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(ACCOUNT_FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    /**
     * 清除所有数据
     */
    public static void clearUser() {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void clearAccount() {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(ACCOUNT_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean userContains(String key) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static boolean accoountContains(String key) {
        initContext();
        SharedPreferences sp = context.getSharedPreferences(ACCOUNT_FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static String getAutoLoginToken() {
        return getUserString(Keys.KEY_AUTOLOGIN_TOKEN, "");
    }

}
