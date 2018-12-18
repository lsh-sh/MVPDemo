package com.bagevent.core.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bagevent.R;
import com.bagevent.app.Constant;
import com.bagevent.core.view.IRecyclerViewIView;
import com.bagevent.http.DealWithNone;
import com.bagevent.http.HttpMsg;
import com.bagevent.http.RetrofitTaskNone;
import com.bagevent.http.exception.ApiException;
import com.bagevent.http.exception.HttpError;
import com.bagevent.utils.TosUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import io.reactivex.Observable;


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
public abstract class RecyclerViewPresenter<V extends IRecyclerViewIView, E, A extends BaseQuickAdapter> extends FragmentPresenterImpl<V> {
    private ArrayList<E> listData = new ArrayList<>();
    private A adapter;
    protected HttpMsg httpMsg;
    private RetrofitTaskNone retrofitTaskNone;
    private int page;

    private final byte ACTION_RELOAD = 1;
    private final byte ACTION_REFRESH = 2;
    private final byte ACTION_LOADMORE = 3;


    @Override
    public void lazyLoad() {
        view.showLoading();
        page = getPageStart();
        retrofitTaskNone = new RetrofitTaskNone();
        reLoadData(ACTION_RELOAD);
    }

    /**
     * 获取起始页码
     *
     * @return
     */
    protected int getPageStart() {
        return 1;
    }

    /**
     * 获取一页显示列表的数量
     *
     * @return
     */
    protected int getPageSize() {
        return Constant.PAGE_SIZE;
    }


    /**
     * 首次加载数据
     */
    private void reLoadData(final byte action) {


        if (enableDataFormLoacal()) {
            ArrayList<E> dataFromLocal = getDataFromLocal(page);
            if (dataFromLocal != null && dataFromLocal.size() > 0) {
                handleDataFromLocal(dataFromLocal, action);
                return;
            }
        }
        retrofitTaskNone.getServiceData(getCallAPI(page, getPageSize(), action, getCurrentTime()), new DealWithNone() {
            @Override
            public void onSuccess(HttpMsg httpMsg, JSONObject result) {
                handleSuccess(httpMsg, result, action);

            }

            @Override
            public void onFailed(HttpMsg httpMsg, ApiException ex) {
                handleFailed(httpMsg, ex, action);
            }
        }, getHttpMsg());
    }

    /**
     * 获取上次更新时间
     *
     * @return
     */
    private String getCurrentTime() {
        return null;
    }

    protected HttpMsg getHttpMsg() {
        if (httpMsg == null) {
            httpMsg = new HttpMsg(getUrl());
        }
        return httpMsg;
    }

    /**
     * 处理本地返回的数据
     *
     * @param listData
     * @param action
     */
    private void handleDataFromLocal(ArrayList<E> listData, int action) {
        switch (action) {
            case ACTION_RELOAD:
                page++;
                this.listData.addAll(listData);
                setRecyclerView(listData);
                break;
            case ACTION_REFRESH:
                page = getPageStart();
                this.listData.clear();
                this.listData.addAll(listData);
                adapter.setNewData(this.listData);
                break;
            case ACTION_LOADMORE:
                page++;
                adapter.addData(listData);
                break;
        }
    }

    /**
     * 获取请求连接
     *
     * @return
     */
    protected abstract String getUrl();

    /**
     * 获取网络请求的API
     *
     * @param page
     * @return
     */
    protected abstract Observable<String> getCallAPI(int page, int pageSize, int action, String time);

    /**
     * 获取实体类
     *
     * @param jsonObject
     * @return
     */
    protected abstract E getBean(JSONObject jsonObject);

    /**
     * 是否开启从本地加载数据
     *
     * @return
     */
    protected boolean enableDataFormLoacal() {
        return false;
    }

    /**
     * 从本地加载数据
     *
     * @return
     */
    protected ArrayList<E> getDataFromLocal(int page) {
        return null;
    }

    /**
     * 处理正常情况
     *
     * @param httpMsg
     * @param result
     * @param action
     */
    private void handleSuccess(HttpMsg httpMsg, JSONObject result, byte action) {

        switch (action) {
            case ACTION_RELOAD:
                handleReloadData(httpMsg, result);
                break;
            case ACTION_REFRESH:
                handleRefreshData(httpMsg, result);
                break;
            case ACTION_LOADMORE:
                handleLoadMoreData(httpMsg, result);
                break;
        }

        requestDataComplete();
    }

    /**
     * 数据请求完毕
     */
    protected void requestDataComplete() {

    }

    /**
     * 加载更多载数据处理
     *
     * @param httpMsg
     * @param result
     */
    private void handleLoadMoreData(HttpMsg httpMsg, JSONObject result) {
        if (adapter != null) {
            adapter.loadMoreComplete();
        }

        if (result == null) {
            return;
        }

        JSONArray jsonArray = getJSONArray(httpMsg, result);
        if (jsonArray == null || jsonArray.size() == 0) {
            return;
        }

        if (listData != null) {
            page++;
            ArrayList<E> addData = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                addData.add(getBean(jsonArray.getJSONObject(i)));
            }
            adapter.addData(addData);
        }
    }


    /**
     * 获取json数组
     *
     * @param httpMsg
     * @param result
     * @return
     */
    private JSONArray getJSONArray(HttpMsg httpMsg, JSONObject result) {
        if (httpMsg.getUrl().startsWith("events")) {
            JSONArray apiEventList = result.getJSONObject("respObject").getJSONArray("apiEventList");
            JSONArray collectionEventList = result.getJSONObject("respObject").getJSONArray("collectionEventList");
            if (apiEventList.size() > 0) {
                return apiEventList;
            } else {
                return collectionEventList;
            }
        }
        JSONArray jsonArray = result.getJSONArray("list");
        return jsonArray;
    }

    /**
     * 刷新数据处理
     *
     * @param httpMsg
     * @param result
     */
    private void handleRefreshData(HttpMsg httpMsg, JSONObject result) {
        if (result == null) {
            view.refreshEnd();
            return;
        }

        JSONArray jsonArray = getJSONArray(httpMsg, result);
        if (jsonArray == null || jsonArray.size() == 0) {
            view.refreshEnd();
            return;
        }
        if (listData != null) {
            page++;
            listData.clear();
            for (int i = 0; i < jsonArray.size(); i++) {
                listData.add(getBean(jsonArray.getJSONObject(i)));
            }
        }
        view.refreshEnd();
        adapter.setNewData(listData);
    }

    /**
     * 首次加载数据处理
     *
     * @param httpMsg
     * @param result
     */
    private void handleReloadData(HttpMsg httpMsg, JSONObject result) {
        if (result == null) {
            setRecyclerView(listData);
            return;
        }


        JSONArray jsonArray = getJSONArray(httpMsg, result);
        if (jsonArray == null || jsonArray.size() == 0) {
            setRecyclerView(listData);
            view.showEmptyView(getLayoutManger(), adapter);
            return;
        }
        page++;
        for (int i = 0; i < jsonArray.size(); i++) {
            listData.add(getBean(jsonArray.getJSONObject(i)));
        }
        setRecyclerView(listData);
    }

    private void setRecyclerView(ArrayList<E> listData) {
        view.showRecyclerView();
        adapter = getAdapter(listData);
        view.addRecyclerHeadView(adapter);
        view.setRecyclerData(getLayoutManger(), adapter);
        if (listData.size() < getPageSize() && listData.size() > 0) {
            adapter.loadMoreEnd();
        }
    }


    /**
     * 获取recyclerview布局管理类
     *
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManger() {
        return new LinearLayoutManager(fragment.getContext());
    }

    /**
     * 获取列表适配器
     *
     * @param listData
     * @return
     */
    protected abstract A getAdapter(ArrayList<E> listData);

    /**
     * 处理失败情况
     *
     * @param httpMsg
     * @param ex
     * @param action
     */
    private void handleFailed(HttpMsg httpMsg, ApiException ex, byte action) {

        if (action == ACTION_RELOAD) {
            if (ex.getCode() == HttpError.NETWORK_ERROR) {
                view.noNetworkView();
            } else {
                view.showErrorView();
            }
        } else if (action == ACTION_REFRESH) {
            TosUtil.show(R.string.http_refresh_failed);
        } else {
            TosUtil.show(R.string.http_loadmore_failed);
        }
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        page = getPageStart();
        reLoadData(ACTION_REFRESH);
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        reLoadData(ACTION_LOADMORE);
    }

    @Override
    public void unbind() {
        retrofitTaskNone.cancel();
        super.unbind();
    }
}
