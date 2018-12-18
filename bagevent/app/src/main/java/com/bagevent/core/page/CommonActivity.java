package com.bagevent.core.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagevent.R;
import com.bagevent.core.presenter.ActivityPresenterImpl;
import com.bagevent.core.view.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp 普通的activity
 * <p>
 * <p>
 * =============================================
 */
public abstract class CommonActivity<P extends ActivityPresenterImpl, V extends IView> extends BaseActivity<P, V> {
    private Unbinder bind;


    @Override
    protected void initLayout() {
        bind = ButterKnife.bind(this);

        initContent();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != bind)
            bind.unbind();
    }

    protected abstract void initContent();


    protected void showNoNetwork(int viewgroupId) {
        if (viewgroupId > 0) {
            View view = findViewById(viewgroupId);
            if (view instanceof ViewGroup) {
                showNoNetwork((ViewGroup) view);
            }
        }
    }

    /**
     * 显示无网络布局
     *
     * @param viewGroup 为Fragment或者RelativeLayout
     *                  添加无网络布局的容器
     */
    protected void showNoNetwork(final ViewGroup viewGroup) {
        if (viewGroup != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.core_no_network, null);
            viewGroup.removeAllViews();
            viewGroup.addView(view);
            view.findViewById(R.id.tv_click_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewGroup.removeAllViews();
                    refreshRequestData();
                }
            });
        }
    }

    /**
     * 刷新请求数据
     */
    protected void refreshRequestData() {

    }
}
