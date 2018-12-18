package com.bagevent.core.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.core.presenter.RecyclerViewPresenter;
import com.bagevent.core.view.IRecyclerViewIView;
import com.bagevent.utils.PageTool;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/7
 * <p>
 * desp 普通列表
 * <p>
 * <p>
 * =============================================
 */
public abstract class RecyclerViewFragment<P extends RecyclerViewPresenter, V extends IRecyclerViewIView> extends BaseFragment<P, V> implements IRecyclerViewIView {
    @BindView(R.id.fl_container)
    protected FrameLayout fl_container;
    @BindView(R.id.fl_container_top)
    protected FrameLayout fl_container_top;

    protected RecyclerViewBind recyclerViewBind;
    protected LoadingViewBind loadingViewBind;
    protected EmptyViewBind emptyViewBind;
    protected ErrorViewBind errorViewBind;
    protected NoNetworkViewBind noNetworkViewBind;

    @Override
    protected int setLayoutId() {
        return R.layout.core_base_recyclerview_fragment;
    }

    @Override
    protected void initLayout() {
        if (getTopView() != null) {
            initTopView(getTopView());
            fl_container_top.setVisibility(View.VISIBLE);
            PageTool.loadViewToConstainer(fl_container_top, getTopView());
        }
    }


    /**
     * 显示无数据布局
     */
    @Override
    public void showEmptyView(RecyclerView.LayoutManager layoutManger, BaseQuickAdapter adapter) {
        if (emptyViewBind == null) {
            emptyViewBind = getEmptyViewBind();
        }
        if (getEmptyMsg() != -1) {
            emptyViewBind.tv_msg.setText(getEmptyMsg());
        }

        if (recyclerViewBind != null) {
            setRecyclerData(layoutManger, adapter);
            adapter.setEmptyView(emptyViewBind.itemView);
        } else {
            PageTool.loadViewToConstainer(fl_container, emptyViewBind.itemView);
        }
    }

    /**
     * 显示加载中布局
     */
    @Override
    public void showLoading() {
        if (loadingViewBind == null) {
            loadingViewBind = getLoadingViewBind();
        }
        PageTool.loadViewToConstainer(fl_container, loadingViewBind.itemView);
    }

    /**
     * 显示rrecyclerview
     */
    @Override
    public void showRecyclerView() {
        if (recyclerViewBind == null) {
            recyclerViewBind = getRecyclerViewBind();
        }
        PageTool.loadViewToConstainer(fl_container, recyclerViewBind.itemView);
    }

    /**
     * 显示数据加载错误布局
     */
    @Override
    public void showErrorView() {
        if (errorViewBind == null) {
            errorViewBind = getErroViewBind();
        }
        PageTool.loadViewToConstainer(fl_container, errorViewBind.itemView);
    }

    /**
     * 设置recyclerview布局结构
     *
     * @param layoutManger
     * @param adapter
     */
    @Override
    public void setRecyclerData(RecyclerView.LayoutManager layoutManger, BaseQuickAdapter adapter) {
        recyclerViewBind.recyclerview.setLayoutManager(layoutManger);
        recyclerViewBind.recyclerview.setAdapter(adapter);
        if (enableRefresh()) {
            recyclerViewBind.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //刷新数据
                    presenter.refreshData();
                }
            });
        } else {
            recyclerViewBind.refresh.setEnabled(false);
        }

        if (enableLoadMore()) {
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    //加载更多数据
                    presenter.loadMoreData();
                }
            }, recyclerViewBind.recyclerview);
        } else {
            adapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void refreshEnd() {
        if (recyclerViewBind != null) {
            recyclerViewBind.refresh.setRefreshing(false);
        }
    }


    /**
     * 是否启用下拉刷新
     *
     * @return
     */
    protected boolean enableRefresh() {
        return true;
    }

    /**
     * 是否启用加载更多
     *
     * @return
     */
    protected boolean enableLoadMore() {
        return true;
    }

    /**
     * 给recyclerview添加头部view
     */
    @Override
    public void addRecyclerHeadView(BaseQuickAdapter adapter) {

    }

    /**
     * 无网络布局展示
     */
    @Override
    public void noNetworkView() {
        if (noNetworkViewBind == null) {
            noNetworkViewBind = getNoNetworkViewBind();
        }
        PageTool.loadViewToConstainer(fl_container, noNetworkViewBind.itemView);
    }

    /**
     * 返回顶部显示的view
     *
     * @return
     */
    protected View getTopView() {
        return null;
    }

    /**
     * 顶部显示的view相关初始化
     *
     * @param topView
     */
    protected void initTopView(View topView) {

    }

    /**
     * 数据为空的信息提示
     *
     * @return 字符串资源id
     */
    protected int getEmptyMsg() {
        return -1;
    }

    protected View getRecyclerView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.core_base_recyclerview, null);
    }

    protected RecyclerViewBind getRecyclerViewBind() {
        return new RecyclerViewBind(getRecyclerView());
    }

    protected View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.core_base_loading, fl_container, false);
    }

    protected LoadingViewBind getLoadingViewBind() {
        return new LoadingViewBind(getLoadingView());
    }

    protected View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.core_base_empty_view, null);
    }

    protected EmptyViewBind getEmptyViewBind() {
        return new EmptyViewBind(getEmptyView());
    }

    protected View getErroView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.core_base_error_view, null);
    }

    protected ErrorViewBind getErroViewBind() {
        return new ErrorViewBind(getErroView());
    }

    protected View getNoNetworkView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.core_no_network, null);
    }

    protected NoNetworkViewBind getNoNetworkViewBind() {
        return new NoNetworkViewBind(getNoNetworkView());
    }

    public class RecyclerViewBind {
        public View itemView;
        @BindView(R.id.refresh)
        public SwipeRefreshLayout refresh;
        @BindView(R.id.recyclerview)
        public RecyclerView recyclerview;

        public RecyclerViewBind(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class LoadingViewBind {
        public View itemView;

        public LoadingViewBind(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class EmptyViewBind {
        public View itemView;
        @BindView(R.id.tv_msg)
        public TextView tv_msg;

        public EmptyViewBind(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class ErrorViewBind {
        public View itemView;

        public ErrorViewBind(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class NoNetworkViewBind {
        public View itemView;
        @BindView(R.id.iv_no_network)
        public ImageView iv_no_network;
        @BindView(R.id.tv_no_network)
        public TextView tv_no_network;
        @BindView(R.id.tv_click_retry)
        public TextView tv_click_retry;

        NoNetworkViewBind(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
