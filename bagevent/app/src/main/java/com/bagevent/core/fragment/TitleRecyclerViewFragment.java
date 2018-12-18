package com.bagevent.core.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.core.presenter.RecyclerViewPresenter;
import com.bagevent.core.view.IRecyclerViewIView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
public abstract class TitleRecyclerViewFragment<P extends RecyclerViewPresenter, V extends IRecyclerViewIView> extends RecyclerViewFragment<P, V> {
    protected TopViewBind topViewBind;

    @Override
    protected View getTopView() {
//        ViewGroup viewGroup = LayoutInflater.from(getContext()).inflate(R.layout.common_title, null);
        topViewBind = new TopViewBind(LayoutInflater.from(getContext()).inflate(R.layout.common_title, fl_container_top, false));
        return topViewBind.itemView;
    }


    @Override
    protected void initTopView(View topView) {

        topViewBind.tvTitle.setText(getRecyclerTitle());

        if (getBackImgId() > 0) {
            topViewBind.ivBack.setVisibility(View.VISIBLE);
            topViewBind.ivBack.setImageResource(getBackImgId());
        }

        if (getRightImgId() > 0) {
            topViewBind.ivRight.setVisibility(View.VISIBLE);
            topViewBind.ivRight.setImageResource(getRightImgId());
        }

        if (getRight2ImgId() > 0) {
            topViewBind.ivRight2.setVisibility(View.VISIBLE);
            topViewBind.ivRight2.setImageResource(getRight2ImgId());
        }
    }

    protected int getBackImgId() {
        return -1;
    }

    protected int getRightImgId() {
        return -1;
    }


    protected int getRight2ImgId() {
        return -1;
    }

    protected void onBackClick() {

    }

    protected void onRightClick() {

    }

    protected void onRight2Click() {

    }

    /**
     * 返回标题的内容
     *
     * @return 字符串对应的资源的id
     */
    protected abstract int getRecyclerTitle();

    public class TopViewBind {
        @BindView(R.id.iv_back)
        public ImageView ivBack;
        @BindView(R.id.tv_title)
        public TextView tvTitle;
        @BindView(R.id.iv_right)
        public ImageView ivRight;
        @BindView(R.id.iv_right2)
        public ImageView ivRight2;
        public View itemView;

        public TopViewBind(View topView) {
            this.itemView = topView;
            ButterKnife.bind(this, topView);
        }

        @OnClick({R.id.iv_back, R.id.iv_right, R.id.iv_right2})
        public void onClicked(View view) {
            switch (view.getId()) {
                case R.id.iv_back:
                    onBackClick();
                    break;
                case R.id.iv_right:
                    onRightClick();
                    break;
                case R.id.iv_right2:
                    onRight2Click();
                    break;
            }
        }
    }
}
