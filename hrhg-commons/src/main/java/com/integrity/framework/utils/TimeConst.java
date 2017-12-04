/**
 * 时间常量定义。<br>
 */
package com.integrity.framework.utils;

/**
 * 时间常量定义。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface TimeConst {
    /**
     * 每秒对应毫秒数
     */
    long MILLISECONDS_PER_SECOND = 1000L;
    /**
     * 每分钟对应的秒数
     */
    long SECONDS_PER_MINUTE = 60;
    /**
     * 每小时对应的分钟数
     */
    long MINUTES_PER_HOUR = 60;
    /**
     * 默认失效时间(1分钟,单位毫秒)
     */
    long DEFAULT_EXPIRE_MINUTE_ONE = SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
    /**
     * 默认失效时间(3分钟,单位毫秒)
     */
    long DEFAULT_EXPIRE_MINUTE_THREE = 3 * DEFAULT_EXPIRE_MINUTE_ONE;
    /**
     * 默认失效时间(10分钟,单位毫秒)
     */
    long DEFAULT_EXPIRE_MINUTE_TEN = 10 * DEFAULT_EXPIRE_MINUTE_ONE;
    /**
     * 默认失效时间(30分钟,单位毫秒)
     */
    long DEFAULT_EXPIRE_MINUTE_THIRTY = 30 * DEFAULT_EXPIRE_MINUTE_ONE;
    /**
     * 默认失效时间(1小时,单位毫秒)
     */
    long DEFAULT_EXPIRE_HOUR_ONE = MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
    /**
     * 默认失效时间(2小时,单位毫秒)
     */
    long DEFAULT_EXPIRE_HOUR_TWO = 2 * DEFAULT_EXPIRE_HOUR_ONE;
    /**
     * 默认失效时间(3小时,单位毫秒)
     */
    long DEFAULT_EXPIRE_HOUR_THREE = 3 * DEFAULT_EXPIRE_HOUR_ONE;
    /**
     * 默认失效时间(5小时,单位毫秒)
     */
    long DEFAULT_EXPIRE_HOUR_FIME = 5 * DEFAULT_EXPIRE_HOUR_ONE;
    /**
     * 默认失效时间(10小时,单位毫秒)
     */
    long DEFAULT_EXPIRE_HOUR_TEN = 10 * DEFAULT_EXPIRE_HOUR_ONE;
    /**
     * 默认失效时间(12小时,单位毫秒)
     */
    long DEFAULT_EXPIRE_HOUR_TWELVE = 12 * DEFAULT_EXPIRE_HOUR_ONE;
    /**
     * 默认失效时间(1天,单位毫秒)
     */
    long DEFAULT_EXPIRE_DAY_ONE = 24 * DEFAULT_EXPIRE_HOUR_ONE;
    /**
     * 默认失效时间(90天,单位毫秒)
     */
    long DEFAULT_EXPIRE_DAY_NINETY = 90 * DEFAULT_EXPIRE_DAY_ONE;
    /**
     * 默认失效时间(1周,单位毫秒)
     */
    long DEFAULT_EXPIRE_WEEK_ONE = 7 * DEFAULT_EXPIRE_DAY_ONE;
}
