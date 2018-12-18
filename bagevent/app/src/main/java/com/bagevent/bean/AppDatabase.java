package com.bagevent.bean;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/26
 * <p>
 * desp 数据创建
 *      数据库名称为AppDatabase
 *      数据库版本号=1
 * <p>
 * <p>
 * =============================================
 */
@Database(name = AppDatabase.NAME,version = AppDatabase.version)
public class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int version = 1;
}
