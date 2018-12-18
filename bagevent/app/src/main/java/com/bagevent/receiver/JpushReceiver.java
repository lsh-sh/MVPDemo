package com.bagevent.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.app.AppManager;
import com.bagevent.app.Keys;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.utils.LogUtil;
import com.bagevent.utils.ShpUtil;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/26
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class JpushReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            receivingNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String userId = ShpUtil.getUserString(Keys.KEY_USER_ID, "");
            if (!TextUtils.isEmpty(userId)) {
                if (bundle != null) {
//                    Intent intent1 = new Intent(context, JPushActionActivity.class);
//                    intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                    intent1.putExtras(bundle);
//                    context.startActivity(intent1);
                }
            } else {
                if (bundle != null) {
                    int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                    JPushInterface.clearNotificationById(context, notificationId);
                }
            }

        }

    }

    private void receivingNotification(Context context, Bundle bundle) {
//        int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (AppManager.getAppManager().activityStackCount() > 0) {
            EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_CHATTING_MESSAGE_RECEIVED, bundle));
        } else {
            LogUtil.d("未打开");
        }
    }
}
