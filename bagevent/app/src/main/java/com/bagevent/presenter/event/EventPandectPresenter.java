package com.bagevent.presenter.event;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.bagevent.adapter.event.EventPandectAdapter;
import com.bagevent.app.Keys;
import com.bagevent.bean.ApiEventList;
import com.bagevent.bean.EventList;
import com.bagevent.core.presenter.RecyclerViewPresenter;
import com.bagevent.core.view.IRecyclerViewIView;
import com.bagevent.http.ServiceApi;
import com.bagevent.utils.APITool;
import com.bagevent.utils.CompareRexUtil;
import com.bagevent.utils.ShpUtil;

import java.util.ArrayList;

import io.reactivex.Observable;

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
public class EventPandectPresenter extends RecyclerViewPresenter<IRecyclerViewIView, EventList, EventPandectAdapter> {
    private int userId;

    @Override
    protected String getUrl() {
        return ServiceApi.EVENT_LIST;
    }

    @Override
    protected Observable<String> getCallAPI(int page, int pageSize) {
        String id = ShpUtil.getUserString(Keys.KEY_USER_ID, "");
        userId = Integer.parseInt(id);
        return APITool.getEventList(id);
    }

    @Override
    protected EventList getBean(JSONObject jsonObject) {
        ApiEventList event = new ApiEventList(jsonObject);
        EventList eventList = new EventList();
        if (TextUtils.isEmpty(event.getCollectionName())) {
            eventList.mark = "event";
            eventList.userId = userId;
            eventList.address = event.getAddress();
            eventList.attendeeCount = event.getAttendeeCount();
            eventList.auditCount = event.getAuditCount();
            eventList.brand = event.getBrand();
            eventList.checkinCount = event.getCheckinCount();
            eventList.collectInvoice = event.getCollectInvoice();
            eventList.endTime = event.getEndTime();
            eventList.eventId = event.getEventId();
            eventList.eventName = CompareRexUtil.escapeCharacter(event.getEventName());
            eventList.eventType = event.getEventType();
            eventList.income = event.getIncome();
            eventList.logo = event.getLogo();
            eventList.nameType = event.getNameType();
            eventList.officialWebsite = event.getOfficialWebsite();
            eventList.participantsCount = event.getParticipantsCount();
            eventList.startTime = event.getStartTime();
            eventList.status = event.getStatus();
            eventList.ticketCount = event.getTicketCount();
            eventList.sType = event.getStType();
        } else {
            eventList.mark = "collect";
            eventList.eventId = event.getEventId();
            eventList.eventName = event.getEventName();
            eventList.collectionName = event.getCollectionName();
            eventList.status = event.getStatus();
            eventList.startTime = event.getStartTime();
            eventList.endTime = event.getEndTime();
            eventList.eventTypes = event.getEventType() + "";
            eventList.collectPointId = event.getCollectPointId();
            eventList.checkinCount = event.getCheckinCount();
            eventList.export = event.getExport();
            eventList.isRepeat = event.getIsRepeat();
            eventList.userId = userId;
            eventList.ticketIds = event.getTicketIds();
        }
        return eventList;
    }

    @Override
    protected EventPandectAdapter getAdapter(ArrayList<EventList> listData) {
        return new EventPandectAdapter(listData);
    }
}
