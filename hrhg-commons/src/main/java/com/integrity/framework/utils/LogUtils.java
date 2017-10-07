package com.integrity.framework.utils;

import org.slf4j.Logger;

/**
 * 数据工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class LogUtils {
    /**
     * 日志输出格式(编码  消息内容)
     */
    private static String formatParam = "【业务编码：%s\t消息内容：%s】";

    /**
     * 跟踪类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void trace(Logger logger, String code, String format, Object... arguments) {
        trace(logger, null, code, format, arguments);
    }

    /**
     * 跟踪类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param ex        异常对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void trace(Logger logger, Throwable ex, String code, String format, Object... arguments) {
        if (null == logger) {
            // 日志对象为空
            return;
        }

        if (!logger.isTraceEnabled()) {
            // 不允许信息类型日志
            return;
        }

        if (null == ex) {
            // 普通消息
            logger.trace(makeMsg(code, format, arguments));
        } else {
            // 异常消息
            logger.trace(makeMsg(code, format, arguments), ex);
        }
    }

    /**
     * 调试类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void debug(Logger logger, String code, String format, Object... arguments) {
        debug(logger, null, code, format, arguments);
    }

    /**
     * 调试类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param ex        异常对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void debug(Logger logger, Throwable ex, String code, String format, Object... arguments) {
        if (null == logger) {
            // 日志对象为空
            return;
        }

        if (!logger.isDebugEnabled()) {
            // 不允许信息类型日志
            return;
        }

        if (null == ex) {
            // 普通消息
            logger.debug(makeMsg(code, format, arguments));
        } else {
            // 异常消息
            logger.debug(makeMsg(code, format, arguments), ex);
        }
    }

    /**
     * 信息类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void info(Logger logger, String code, String format, Object... arguments) {
        info(logger, null, code, format, arguments);
    }

    /**
     * 信息类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param ex        异常对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void info(Logger logger, Throwable ex, String code, String format, Object... arguments) {
        if (null == logger) {
            // 日志对象为空
            return;
        }

        if (!logger.isInfoEnabled()) {
            // 不允许信息类型日志
            return;
        }

        if (null == ex) {
            // 普通消息
            logger.info(makeMsg(code, format, arguments));
        } else {
            // 异常消息
            logger.info(makeMsg(code, format, arguments), ex);
        }
    }

    /**
     * 警告类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void warn(Logger logger, String code, String format, Object... arguments) {
        warn(logger, null, code, format, arguments);
    }

    /**
     * 警告类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param ex        异常对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void warn(Logger logger, Throwable ex, String code, String format, Object... arguments) {
        if (null == logger) {
            // 日志对象为空
            return;
        }

        if (!logger.isWarnEnabled()) {
            // 不允许信息类型日志
            return;
        }

        if (null == ex) {
            // 普通消息
            logger.warn(makeMsg(code, format, arguments));
        } else {
            // 异常消息
            logger.warn(makeMsg(code, format, arguments), ex);
        }
    }

    /**
     * 异常类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void error(Logger logger, String code, String format, Object... arguments) {
        error(logger, null, code, format, arguments);
    }

    /**
     * 异常类型日志输出。<br>
     *
     * @param logger    日志输出对象
     * @param ex        异常对象
     * @param code      业务编码
     * @param format    业务日志格式
     * @param arguments 日志参数
     */
    public static void error(Logger logger, Throwable ex, String code, String format, Object... arguments) {
        if (null == logger) {
            // 日志对象为空
            return;
        }

        if (!logger.isErrorEnabled()) {
            // 不允许信息类型日志
            return;
        }

        if (null == ex) {
            // 普通消息
            logger.error(makeMsg(code, format, arguments));
        } else {
            // 异常消息
            logger.error(makeMsg(code, format, arguments), ex);
        }
    }

    /**
     * 日志内容输出。<br>
     *
     * @param code      业务编码
     * @param format    业务格式
     * @param arguments 参数
     * @return 输出日志
     */
    private static String makeMsg(String code, String format, Object... arguments) {
        // 生成业务消息内容
        String msg = String.format(format, arguments);
        return String.format(formatParam, code, msg);
    }
}
