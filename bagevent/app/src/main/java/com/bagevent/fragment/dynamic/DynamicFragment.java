package com.bagevent.fragment.dynamic;

import com.bagevent.core.fragment.RecyclerViewFragment;
import com.bagevent.core.view.IRecyclerViewIView;
import com.bagevent.presenter.dynamic.DynamicPresenter;

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
public class DynamicFragment extends RecyclerViewFragment<DynamicPresenter, IRecyclerViewIView> {

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @Override
    protected IRecyclerViewIView getVIew() {
        return this;
    }

    @Override
    protected DynamicPresenter getPresenter() {
        return new DynamicPresenter();
    }

}
