package com.bagevent.utils;

import com.bagevent.http.exception.HttpError;

/**
 * Created by zwj on 2016/5/27.
 */
public class ErrCodeUtil implements HttpError {
    public static String ErrCode(int errCode) {
        if (errCode == ERROR_199) {
            return "验证码已过期,请重新获取";
        }
        if (errCode == ERROR_201) {
            return "参数为空";
        }
        if (errCode == ERROR_211) {
            return "用户已存在";
        }
        if (errCode == ERROR_212) {
            return "用户已存在";
        }
        if (errCode == ERROR_213) {
            return "用户名或密码有误";
        }
        if (errCode == ERROR_214) {
            return "帐号还未激活";
        }
        if (errCode == ERROR_215) {
            return "手机格式有误";
        }
        if (errCode == ERROR_216) {
            return "两次输入密码不一致";
        }
        if (errCode == ERROR_207) {
            return "验证码有误";
        }
        if (errCode == ERROR_221) {
            return "无效的登录token";
        }
        if (errCode == ERROR_99) {
            return "处理异常";
        }
        if (errCode == ERROR_101) {
            return "非法token";
        }
        if (errCode == ERROR_253 ) {
            return "您不是活动组织者";
        }
        if (errCode == ERROR_254 ) {
            return "短信含有风险词组";
        }
        if(errCode == ERROR_1001) {
            return "状态参数不对";
        }
        if(errCode == ERROR_11001) {
            return "状态参数不对";
        }
        if(errCode == ERROR_1100) {
            return "采集点不存在";
        }
        if(errCode == ERROR_11002) {
            return "采集点类型不对";
        }
        if(errCode == ERROR_11) {
            return "采集的User不存在";
        }
        if(errCode == ERROR_11003) {
            return "不在采集时间内";
        }
        if(errCode == ERROR_11004) {
            return "当前门票不能进入";
        }
        if(errCode == ERROR_301) {
            return "人员重复入场";
        }
        if(errCode == ERROR_302) {
            return "人员重复出场";
        }
        if(errCode == ERROR_303) {
            return "请从入口进入";
        }
        if(errCode == PARSE_ERROR){
            return "json解析错误";
        }
        if(errCode == NETWORK_ERROR){
            return "网络链接错误";
        }
        if(errCode == SSL_ERROR){
            return "证书出错";
        }
        return "未知错误";
    }
}
