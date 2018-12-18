package com.bagevent.http;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
public interface ServiceStore {

    /**
     * 进行登录
     *
     * @param userName
     * @param uesrPwd
     * @return
     */
    @POST(ServiceApi.LOGIN)
    @FormUrlEncoded
    Observable<String> login(@Field("account") String userName, @Field("password") String uesrPwd);

    /**
     * 自动登录
     *
     * @param autoLoginToken
     * @return
     */
    @POST(ServiceApi.AUTO_LOGIN)
    @FormUrlEncoded
    Observable<String> autoLogin(@Field("autoLoginToken") String autoLoginToken);

    /**
     * 是否存在未读的消息
     *
     * @return
     */
    @GET(ServiceApi.EXIST_UNREAD_MSG)
    Observable<String> findExistNewMessage(@Header("User-token") String userToken, @Query("userId") String userId);



    /**
     * 获取活动列表
     *
     * @return
     */
    @GET
    Observable<String> getEeventList(@Header("User-token") String userToken, @Url String url);


}
