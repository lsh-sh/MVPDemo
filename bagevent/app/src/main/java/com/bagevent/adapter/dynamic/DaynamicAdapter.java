package com.bagevent.adapter.dynamic;

import android.support.annotation.Nullable;

import com.bagevent.bean.UserInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/22
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class DaynamicAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    public DaynamicAdapter(int layoutResId, @Nullable List<UserInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {

    }
}
