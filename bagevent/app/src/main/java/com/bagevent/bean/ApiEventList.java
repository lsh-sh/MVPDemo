package com.bagevent.bean;

import com.alibaba.fastjson.JSONObject;

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
public class ApiEventList {
    private String address;
    private int attendeeCount;
    private int auditCount;
    private int brand;
    private int checkinCount;
    private int collectInvoice;
    private String endTime;
    private int eventId;
    private String eventName;
    private int eventType;
    private double income;
    private String logo;
    private String officialWebsite;
    private int participantsCount;
    private String startTime;
    private int status;
    private int ticketCount;
    private long websiteId;
    private int nameType;
    private int stType;
    private String collectionName;
    private int collectPointId;
    private int export;
    private int isRepeat;
    private String ticketIds;

    public ApiEventList(JSONObject jsonObject) {
        address = jsonObject.getString("address");
        endTime = jsonObject.getString("endTime");
        eventName = jsonObject.getString("eventName");
        logo = jsonObject.getString("logo");
        officialWebsite = jsonObject.getString("officialWebsite");
        startTime = jsonObject.getString("startTime");
        collectionName = jsonObject.getString("collectionName");
        ticketIds = jsonObject.getString("ticketIds");

        if(jsonObject.containsKey("attendeeCount")){
            attendeeCount = jsonObject.getIntValue("attendeeCount");
        }
        if(jsonObject.containsKey("auditCount")){
            auditCount = jsonObject.getIntValue("auditCount");
        }
        if(jsonObject.containsKey("brand")){
            brand = jsonObject.getIntValue("brand");
        }
        if(jsonObject.containsKey("checkinCount")){
            checkinCount = jsonObject.getIntValue("checkinCount");
        }
        if(jsonObject.containsKey("collectInvoice")){
            collectInvoice = jsonObject.getIntValue("collectInvoice");
        }
        if(jsonObject.containsKey("eventId")){
            eventId = jsonObject.getIntValue("eventId");
        }
        if(jsonObject.containsKey("eventType")){
            eventType = jsonObject.getIntValue("eventType");
        }
        if(jsonObject.containsKey("participantsCount")){
            participantsCount = jsonObject.getIntValue("participantsCount");
        }
        if(jsonObject.containsKey("status")){
            status = jsonObject.getIntValue("status");
        }
        if(jsonObject.containsKey("ticketCount")){
            ticketCount = jsonObject.getIntValue("ticketCount");
        }
        if(jsonObject.containsKey("nameType")){
            nameType = jsonObject.getIntValue("nameType");
        }
        if(jsonObject.containsKey("stType")){
            stType = jsonObject.getIntValue("stType");
        }
        if(jsonObject.containsKey("collectPointId")){
            collectPointId = jsonObject.getIntValue("collectPointId");
        }
        if(jsonObject.containsKey("export")){
            export = jsonObject.getIntValue("export");
        }
        if(jsonObject.containsKey("isRepeat")){
            isRepeat = jsonObject.getIntValue("isRepeat");
        }
        if(jsonObject.containsKey("income")){
            income = jsonObject.getFloatValue("income");
        }
        if(jsonObject.containsKey("websiteId")){
            websiteId = jsonObject.getLongValue("websiteId");
        }
    }

    public String getAddress() {
        return address;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public int getAuditCount() {
        return auditCount;
    }

    public int getBrand() {
        return brand;
    }

    public int getCheckinCount() {
        return checkinCount;
    }

    public int getCollectInvoice() {
        return collectInvoice;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventType() {
        return eventType;
    }

    public double getIncome() {
        return income;
    }

    public String getLogo() {
        return logo;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getStatus() {
        return status;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public long getWebsiteId() {
        return websiteId;
    }

    public int getNameType() {
        return nameType;
    }

    public int getStType() {
        return stType;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public int getCollectPointId() {
        return collectPointId;
    }

    public int getExport() {
        return export;
    }

    public int getIsRepeat() {
        return isRepeat;
    }

    public String getTicketIds() {
        return ticketIds;
    }
}
