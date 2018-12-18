package com.bagevent.adapter.event;

import android.support.annotation.Nullable;
import android.view.View;

import com.bagevent.R;
import com.bagevent.bean.EventList;
import com.bagevent.utils.CompareRexUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/26
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class EventPandectAdapter extends BaseQuickAdapter<EventList, BaseViewHolder> {


    public EventPandectAdapter(@Nullable List<EventList> data) {
        super(R.layout.item_event,data);
//        setMultiTypeDelegate(new MultiTypeDelegate<EventList>() {
//            @Override
//            protected int getItemType(EventList eventList) {
//                if (eventList.mark.equals("event")) {
//                    return 0;
//                } else {
//                    return 1;
//                }
//            }
//        });
//
//        getMultiTypeDelegate()
//                .registerItemType(0, R.layout.item_event)
//                .registerItemType(1, R.layout.item_collect);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventList item) {
//        switch (helper.getItemViewType()) {
//            case 0:
                DecimalFormat format = new DecimalFormat("0.00");

                helper.setText(R.id.tv_home_event_name, item.eventName)
                        .setText(R.id.tv_home_event_income, "￥" + format.format(item.income));
                String time = CompareRexUtil.compareDiff(item.startTime, item.endTime);
                if (time.equals("start")) {
                    if (item.checkinCount == 0 || item.attendeeCount == 0) {
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join, item.attendeeCount + "")
                                .setText(R.id.tv_home_event_status, "已签到")
                                .setProgress(R.id.home_progress, (int) 0, item.attendeeCount);
                    } else {
                        float progress = new BigDecimal(((float) item.checkinCount / item.attendeeCount)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        progress = progress * 100;
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join, item.attendeeCount + "")
                                .setText(R.id.tv_home_event_status, "已签到");
                        helper.setProgress(R.id.home_progress, (int) progress, 100);
                    }

                } else {

                    if (item.ticketCount == 0 || item.attendeeCount == 0) {
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join, item.attendeeCount + "")
                                .setProgress(R.id.home_progress, 0, 100)
                                .setText(R.id.tv_home_event_status, "已报名");
                    } else {
                        float progress = new BigDecimal((float) item.attendeeCount / item.ticketCount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        progress = progress * 100;
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join, item.attendeeCount + "")
                                .setProgress(R.id.home_progress, (int) progress, 100)
                                .setText(R.id.tv_home_event_status, "已报名");
                    }

                }

                helper.setGone(R.id.ll_home_audit, false);
//
//                break;
//            case 1:
//                helper.setText(R.id.tv_event_name, item.eventName)
//                        .setText(R.id.tv_collect_name, item.collectionName)
//                        .setText(R.id.tv_collect_checkincounts, item.checkinCount + "");
//                break;
//        }
    }


}
