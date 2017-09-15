/**
 * 日期工具。<br>
 */
package com.hrhg.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class DateUtils {
    /**
     * 1秒的毫秒数
     */
    public static final long SECOND_IN_MILLIS = 1000L;
    /**
     * 1分钟的毫秒数
     */
    public static final long MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;
    /**
     * 1小时的毫秒数
     */
    public static final long HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;
    /**
     * 1天的毫秒数
     */
    public static final long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;
    /**
     * 1周的毫秒数
     */
    public static final long WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS;
    /**
     * 90天
     */
    public static final int DAYS_90 = 90;

    /**
     * 日期形式(yyyy-MM-dd HH:mm)
     */
    public static final String DATE_FORMAT_MINUTE_W = "yyyy-MM-dd HH:mm";
    /**
     * 日期形式(yyyyMMdd)
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    /**
     * 日期形式(yyyy/MM/dd)
     */
    public static final String DATE_FORMAT_LONG = "yyyy/MM/dd";
    /**
     * 日期形式(yyyy-MM-dd)
     */
    public static final String DATE_FORMAT_LONG_S = "yyyy-MM-dd";
    /**
     * 日期形式(yyyy.MM.dd)
     */
    public static final String DATE_FORMAT_LONG_D = "yyyy.MM.dd";
    /**
     * 日期形式(yyyy年MM月dd日)
     */
    public static final String DATE_FORMAT_LONG_C = "yyyy年MM月dd日";
    /**
     * 日期形式(yyyy/MM)
     */
    public static final String DATE_FORMAT_YEAR_MONTH = "yyyy/MM";
    /**
     * 日期形式(yyyy-MM)
     */
    public static final String DATE_FORMAT_YEAR_MONTH_S = "yyyy-MM";
    /**
     * 日期形式(yyyy年MM月)
     */
    public static final String DATE_FORMAT_YEAR_MONTH_C = "yyyy年MM月";
    /**
     * 日期形式(yyyyMM)
     */
    public static final String DATE_FORMAT_YEAR_MONTH_SHORT = "yyyyMM";
    /**
     * 日期形式(MM/dd)
     */
    public static final String DATE_FORMAT_SHORT_MD = "MM/dd";
    /**
     * 日期形式(MM-dd)
     */
    public static final String DATE_FORMAT_SHORT_MD_S = "MM-dd";
    /**
     * 日期形式(MM月dd日)
     */
    public static final String DATE_FORMAT_SHORT_MD_C = "MM月dd日";
    /**
     * 日期形式(dd)
     */
    public static final String DATE_FORMAT_SHORT_DD = "dd";
    /**
     * 日期形式(借书开始日，结束日)
     */
    public static final String DATE_FORMAT_BORROW_DATE = "yyyy年MM月dd日HH点";
    /**
     * 日期形式(发表时间)
     */
    public static final String DATE_FORMAT_BORROW_DATE_FEN = "MM月dd日HH点";

    /**
     * 日期形式(微信模板消息时间)
     */
    public static final String DATE_FORMAT_WEIXIN_SEND_MSG_DATE = "yyyy年MM月dd日HH点mm分";

    /**
     * 日期形式(更新时间)
     */
    public static final String DATE_FORMAT_RECORD_DATE = "yyyy/MM/dd HH:mm:ss";

    /**
     * 日期形式(更新时间)
     */
    public static final String DATE_FORMAT_RECORD_DATE_S = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期形式(更新时间)
     */
    public static final String DATE_FORMAT_RECORD_DATE_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 日期形式(更新时间)
     */
    public static final String DATE_FORMAT_RECORD_DATE_SHORT = "yyyyMMdd|HH:mm:ss";

    /**
     * 日期形式(LOG输出)
     */
    public static final String DATE_FORMAT_RECORD_DATE_L = "yyyy/MM/dd|HH:mm:ss.SSS";

    /**
     * 获取系统时间戳。<br>
     *
     * @return 系统时间戳
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取系统日期。<br>
     *
     * @return Date 系统日期
     */
    public static Date getSystemDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取超时时间戳。<br>
     *
     * @param exp 超时时间
     * @return 超时时间戳
     */
    public static long getTimeStampExp(long exp) {
        return System.currentTimeMillis() + exp;
    }

    /**
     * 获取超时时间。<br>
     *
     * @param exp 超时时间
     * @return Date 超时时间
     */
    public static Date getDateExp(long exp) {
        return new Date(getTimeStampExp(exp));
    }

    /**
     * 时间戳转换为日期类型时间。<br>
     *
     * @param date 时间戳
     * @return 日期类型时间
     */
    public static Date toDate(long date) {
        return new Date(date);
    }

    /**
     * 日期类型时间转换为时间戳。<br>
     *
     * @param date 日期类型时间
     * @return 时间戳
     */
    public static long toTimeStamp(Date date) {
        if (null == date) {
            return 0L;
        }
        return date.getTime();
    }

    /**
     * 日期转换（日期转换成文字列）。<br>
     *
     * @param date    日期对象
     * @param pattern 日期格式
     * @return 转换后文字列
     */
    public static String toDateFormat(Long date, String pattern) {
        return toDateFormat(toDate(date), pattern);
    }

    /**
     * 日期转换（日期转换成文字列）。<br>
     *
     * @param data    日期对象
     * @param pattern 日期格式
     * @return 转换后文字列
     */
    public static String toDateFormat(Date data, String pattern) {
        if (DataUtils.isNullOrEmpty(data)) {
            // 日期对象为空
            return StringUtils.EMPTY_STRING;
        }

        // 日期格式对象
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        return formatter.format(data);

    }


    /**
     * 日期转换（文字列转换成日期）。<br>
     *
     * @param data   对象文字列
     * @param format 日期格式
     * @return 转换后日期
     */
    public static Date toDateType(String data, String format) {
        if (DataUtils.isNullOrEmpty(data)) {
            // 字符串为空
            return null;
        }

        // 日期格式对象
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            return formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期转换（文字列转换成文字列）。<br>
     *
     * @param data 对象文字列
     * @return 转换后文字列
     */
    public static Date toDateTypeByLength(String data) {
        if (StringUtils.isEmpty(data)) {
            // 字符串为空
            return null;
        }

        // 日期格式对象
        SimpleDateFormat sdfObject;

        // 根据文字列长度判断
        if (data.length() == 6) {
            // 转换成yyyyMM的形式
            sdfObject = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_SHORT);
        } else if (data.length() == 7) {
            // 转换成yyyy/MM的形式
            sdfObject = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH);
        } else if (data.length() == 8) {
            // 转换成yyyyMMdd的形式
            sdfObject = new SimpleDateFormat(DATE_FORMAT_SHORT);
        } else if (data.length() == 10) {
            // 转换成yyy/MM/dd的形式
            sdfObject = new SimpleDateFormat(DATE_FORMAT_LONG);
        } else if (data.length() == 19) {
            // 转换成yyyy/MM/dd|HH:mm:ss的形式
            sdfObject = new SimpleDateFormat(DATE_FORMAT_RECORD_DATE);
        } else if (data.length() == 23) {
            // 转换成yyyy/MM/dd|HH:mm:ss.SSS的形式
            sdfObject = new SimpleDateFormat(DATE_FORMAT_RECORD_DATE_S);
        } else {
            sdfObject = new SimpleDateFormat(DATE_FORMAT_BORROW_DATE_FEN);
        }

        try {
            // 日期解析
            return sdfObject.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取通用格式日期文案。<br>
     *
     * @param data 日期时间戳
     * @return 转换后文字列
     */
    public static String normalNullableDateText(Long data) {
        if (null == data) {
            // 时间戳为空
            return null;
        }

        return normalDateText(toDate(data));
    }

    /**
     * 获取通用格式日期文案。<br>
     *
     * @param data 日期时间戳
     * @return 转换后文字列
     */
    public static String normalDateText(Long data) {
        return normalDateText(null == data ? null : toDate(data));
    }

    /**
     * 获取通用格式日期文案。<br>
     *
     * @param data 日期时间戳
     * @return 转换后文字列
     */
    public static String normalDateText(Date data) {
        return toDateFormat(data, DATE_FORMAT_RECORD_DATE);
    }

    /**
     * 获取日格式日期文案。<br>
     *
     * @param data 日期时间戳
     * @return 转换后文字列
     */
    public static String longDateText(Long data) {
        return longDateText(null == data ? null : toDate(data));
    }

    /**
     * 获取通日格式日期文案。<br>
     *
     * @param data 日期时间戳
     * @return 转换后文字列
     */
    public static String longDateText(Date data) {
        return toDateFormat(data, DATE_FORMAT_LONG);
    }

    /**
     * 获取某日期N天后的时间戳
     *
     * @param beginTime 开始日期
     * @param days      N天后
     * @return N天后的时间戳
     */
    public static Long getDaysSpan(Long beginTime, Integer days) {
        if (DataUtils.isNullOrEmpty(beginTime)) {
            return null;
        }

        if (DataUtils.isNullOrEmpty(days)) {
            return beginTime;
        }

        //返回N天后的时间戳
        return beginTime + days * DAY_IN_MILLIS;
    }
}
