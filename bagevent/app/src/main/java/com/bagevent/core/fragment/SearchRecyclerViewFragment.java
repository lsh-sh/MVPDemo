package com.bagevent.core.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.core.presenter.RecyclerViewPresenter;
import com.bagevent.core.view.IRecyclerViewIView;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/23
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public abstract class SearchRecyclerViewFragment<P extends RecyclerViewPresenter, V extends IRecyclerViewIView> extends TitleRecyclerViewFragment<P, V> {

    @Override
    protected View getTopView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.core_base_recyclerview_search, null);
    }


    @Override
    protected void initTopView(View topView) {
        super.initTopView(topView);
        TextView tv_search = topView.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClicked();
            }
        });
    }


    protected abstract void onSearchClicked();
}
