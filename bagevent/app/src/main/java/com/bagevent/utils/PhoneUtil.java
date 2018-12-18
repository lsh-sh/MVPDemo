package com.bagevent.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/7
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class PhoneUtil {
    public static boolean isPhone(String num) {
        String phoneRule = "^((13[0-9])|(14[5,7])|(15[^4])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(phoneRule);
        Matcher m = p.matcher(num);
        return m.matches();
    }
}
