package com.bagevent.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.v4.content.FileProvider;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.BuildConfig;
import com.bagevent.app.BagEventApp;

import java.io.File;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp 操作app相关的方法
 * <p>
 * <p>
 * =============================================
 */
public class AppUtil {

    private static WeakHandler handler = new WeakHandler(Looper.getMainLooper());

    public static void runOnUI(Runnable runnable) {
        handler.post(runnable);
    }

    public static void runOnUIDelay(Runnable runnable, long delay) {
        handler.postDelayed(runnable, delay);
    }

    public static void runOnThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static Context getContext() {
        return BagEventApp.getInstance();
    }

    /**
     * 安装apk
     *
     * @param context
     * @param apkFile
     */
    public static void installApk(Context context, File apkFile) {
        if (apkFile != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
                installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(installIntent);

            }
        }
    }


    /**
     * 获得版本号
     */

    public static String getVersioncode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
