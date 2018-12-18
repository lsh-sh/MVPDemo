package com.bagevent.http;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class HttpMsg {
    private String url;
    private Object tag; //设置绑定数据

    public HttpMsg(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
