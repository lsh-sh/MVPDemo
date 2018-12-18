package com.bagevent.presenter.dynamic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bagevent.R;
import com.bagevent.adapter.dynamic.DaynamicAdapter;
import com.bagevent.bean.UserInfo;
import com.bagevent.core.presenter.RecyclerViewPresenter;
import com.bagevent.core.view.IRecyclerViewIView;
import com.bagevent.http.ServiceApi;
import com.bagevent.utils.APITool;

import java.util.ArrayList;

import io.reactivex.Observable;


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
public class DynamicPresenter extends RecyclerViewPresenter<IRecyclerViewIView, UserInfo, DaynamicAdapter> {
    @Override
    protected String getUrl() {
        return ServiceApi.AUTO_LOGIN;
    }

    @Override
    protected Observable<String> getCallAPI(int page, int pageSize, int action, String time) {
        return APITool.autoLogin("");
    }


    @Override
    protected UserInfo getBean(JSONObject jsonObject) {

        return JSON.parseObject(jsonObject.toJSONString(), UserInfo.class);
    }

    @Override
    protected DaynamicAdapter getAdapter(ArrayList<UserInfo> listData) {
        return new DaynamicAdapter(R.layout.common_title, listData);
    }


}
