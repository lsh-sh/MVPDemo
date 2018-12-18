package com.bagevent.http.exception;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/2
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public interface HttpError {
    /**
     * 验证码已过期,请重新获取
     */
    int ERROR_199 = 199;

    /**
     * 参数为空
     */
    int ERROR_201 = 201;

    /**
     * 用户已存在
     */
    int ERROR_211 = 211;

    /**
     * 用户已存在
     */
    int ERROR_212 = 212;

    /**
     * 用户名或密码有误
     */
    int ERROR_213 = 213;

    /**
     * 帐号还未激活
     */
    int ERROR_214 = 214;

    /**
     * 手机格式有误
     */
    int ERROR_215 = 215;

    /**
     * 两次输入密码不一致
     */
    int ERROR_216 = 216;

    /**
     * 验证码有误
     */
    int ERROR_207 = 207;

    /**
     * 无效的登录token
     */
    int ERROR_221 = 221;

    /**
     * 处理异常
     */
    int ERROR_99 = 99;

    /**
     * 非法token
     */
    int ERROR_101 = 101;

    /**
     * 您不是活动组织者
     */
    int ERROR_253 = 253;

    /**
     * 短信含有风险词组
     */
    int ERROR_254 = 254;

    /**
     * 状态参数不对
     */
    int ERROR_1001 = 1001;

    /**
     * 状态参数不对
     */
    int ERROR_11001 = -1001;

    /**
     * 采集点不存在
     */
    int ERROR_1100 = -100;

    /**
     * 采集点类型不对
     */
    int ERROR_11002 = -1002;

    /**
     * 采集的User不存在
     */
    int ERROR_11 = -1;

    /**
     * 不在采集时间内
     */
    int ERROR_11003 = -1003;

    /**
     * 当前门票不能进入
     */
    int ERROR_11004 = -1004;

    /**
     * 人员重复入场
     */
    int ERROR_301 = 301;

    /**
     * 人员重复出场
     */
    int ERROR_302 = 302;

    /**
     * 请从入口进入
     */
    int ERROR_303 = 303;


    /**
     * 未知错误
     */
    int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    int PARSE_ERROR = 10010;
    /**
     * 无网络
     */
    int NETWORK_ERROR = 1002;
    /**
     * 证书出错
     */
    int SSL_ERROR = 1003;
}
