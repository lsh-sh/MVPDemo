package com.bagevent.page;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.core.page.BaseActivity;
import com.bagevent.core.page.CommonActivity;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.fragment.event.ActivityFragment;
import com.bagevent.fragment.event.EventPandectFragment;
import com.bagevent.fragment.message.MessageFragment;
import com.bagevent.fragment.setting.SettingFragment;
import com.bagevent.presenter.MainPresenter;
import com.bagevent.view.MainView;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends CommonActivity<MainPresenter, MainView> implements MainView {


    @BindView(R.id.tv_activity)
    TextView tvActivity;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.view_point)
    View viewPoint;


    private static final byte TAB_ACTIIVTY = 0x11;
    private static final byte TAB_MESSAGE = 0x12;
    private static final byte TAB_SETTING = 0x13;
    private static final int REQUECT_CODE_SDCARD = 1;


    private int index = TAB_ACTIIVTY;
    private FragmentManager fragmentManager;
    private EventPandectFragment eventPandectFragment;
    private MessageFragment messageFragment;
    private SettingFragment settingFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContent() {
        fragmentManager = getSupportFragmentManager();
        setDefaultPage(TAB_ACTIIVTY);
        presenter.init();
        MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected MainView getVIew() {
        return this;
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }


    @OnClick({R.id.tv_activity, R.id.tv_message, R.id.tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_activity:
                if (index != TAB_ACTIIVTY) {
                    setTabUnCheck();
                    index = TAB_ACTIIVTY;
                    setTabCheck();
                    setDefaultPage(TAB_ACTIIVTY);
                }
                break;
            case R.id.tv_message:
                if (index != TAB_MESSAGE) {
                    setTabUnCheck();
                    index = TAB_MESSAGE;
                    setTabCheck();
                    setDefaultPage(TAB_MESSAGE);
                }
                break;
            case R.id.tv_setting:
                if (index != TAB_SETTING) {
                    setTabUnCheck();
                    index = TAB_SETTING;
                    setTabCheck();
                    setDefaultPage(TAB_SETTING);
                }
                break;
        }
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
    }

    @Override
    protected short[] getActions() {
        return new short[]{MessageAction.ACTION_CHATTING_MESSAGE_RECEIVED};
    }

    @Override
    protected void receiveMessage(MessageEvent event) {
        if (MessageAction.ACTION_CHATTING_MESSAGE_RECEIVED == event.getAction()) {
            showMessagePoint();
        }
    }

    private void setDefaultPage(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideTransaction(transaction);
        switch (index) {
            case TAB_ACTIIVTY:
                if (eventPandectFragment == null) {
                    eventPandectFragment = new EventPandectFragment();
                    transaction.add(R.id.fl_container, eventPandectFragment);
                } else {
                    transaction.show(eventPandectFragment);
                }
                break;
            case TAB_MESSAGE:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.fl_container, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case TAB_SETTING:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.fl_container, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideTransaction(FragmentTransaction transaction) {
        if (eventPandectFragment != null) {
            transaction.hide(eventPandectFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }

        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }

    /**
     * tab被选择
     */
    private void setTabCheck() {

        switch (index) {
            case TAB_ACTIIVTY:
                setActivityCheck();
                break;
            case TAB_MESSAGE:
                setMessageCheck();
                break;
            case TAB_SETTING:
                setSettingCheck();
                break;
        }

    }

    /**
     * tab没有被选择
     */
    private void setTabUnCheck() {

        switch (index) {
            case TAB_ACTIIVTY:
                setActivityUnCheck();
                break;
            case TAB_MESSAGE:
                setMessageUnCheck();
                break;
            case TAB_SETTING:
                setSettingUnCheck();
                break;

        }
    }

    private void setMessageUnCheck() {
        Drawable drawable = getResources().getDrawable(R.mipmap.message_uncheck);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getIntrinsicHeight());
        tvMessage.setCompoundDrawables(null, drawable, null, null);
        tvMessage.setTextColor(getResources().getColor(R.color.grey));
    }

    private void setSettingUnCheck() {
        Drawable drawable = getResources().getDrawable(R.mipmap.usermanager_uncheck);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getIntrinsicHeight());
        tvSetting.setCompoundDrawables(null, drawable, null, null);
        tvSetting.setTextColor(getResources().getColor(R.color.grey));
    }

    private void setActivityUnCheck() {
        Drawable drawable = getResources().getDrawable(R.mipmap.myevent_uncheck);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getIntrinsicHeight());
        tvActivity.setCompoundDrawables(null, drawable, null, null);
        tvActivity.setTextColor(getResources().getColor(R.color.grey));
    }


    private void setMessageCheck() {
        Drawable drawable = getResources().getDrawable(R.mipmap.message_check);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getIntrinsicHeight());
        tvMessage.setCompoundDrawables(null, drawable, null, null);
        tvMessage.setTextColor(getResources().getColor(R.color.ff9a3b));
    }

    private void setSettingCheck() {
        Drawable drawable = getResources().getDrawable(R.mipmap.usermanager_check);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getIntrinsicHeight());
        tvSetting.setCompoundDrawables(null, drawable, null, null);
        tvSetting.setTextColor(getResources().getColor(R.color.ff9a3b));
    }

    private void setActivityCheck() {
        Drawable drawable = getResources().getDrawable(R.mipmap.myevent_check);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getIntrinsicHeight());
        tvActivity.setCompoundDrawables(null, drawable, null, null);
        tvActivity.setTextColor(getResources().getColor(R.color.ff9a3b));
    }


    /**
     * 显示消息中心tab上的点
     */
    @Override
    public void showMessagePoint() {
        viewPoint.setVisibility(View.VISIBLE);
    }

    @Override
    public void noNetworkView() {

    }
}
