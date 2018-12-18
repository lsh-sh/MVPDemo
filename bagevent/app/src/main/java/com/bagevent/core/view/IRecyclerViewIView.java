package com.bagevent.core.view;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/20
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public interface IRecyclerViewIView extends IView {
    void showLoading();

    void showRecyclerView();

    void showEmptyView(RecyclerView.LayoutManager layoutManger, BaseQuickAdapter adapter);

    void showErrorView();

    void setRecyclerData(RecyclerView.LayoutManager layoutManger, BaseQuickAdapter adapter);

    void addRecyclerHeadView(BaseQuickAdapter adapter);

    void refreshEnd();

}