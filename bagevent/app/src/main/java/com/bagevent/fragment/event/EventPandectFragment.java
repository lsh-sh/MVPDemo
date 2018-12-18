package com.bagevent.fragment.event;

import com.bagevent.R;
import com.bagevent.core.fragment.TitleRecyclerViewFragment;
import com.bagevent.core.view.IRecyclerViewIView;
import com.bagevent.presenter.event.EventPandectPresenter;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/26
 * <p>
 * desp 活动总览
 * <p>
 * <p>
 * =============================================
 */
public class EventPandectFragment extends TitleRecyclerViewFragment<EventPandectPresenter, IRecyclerViewIView> {
    @Override
    protected int getRecyclerTitle() {
        return R.string.title_event_pandect;
    }

    @Override
    protected IRecyclerViewIView getVIew() {
        return this;
    }

    @Override
    protected EventPandectPresenter getPresenter() {
        return new EventPandectPresenter();
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }
}
