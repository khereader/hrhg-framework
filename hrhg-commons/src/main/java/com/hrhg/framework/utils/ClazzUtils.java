/**
 * 类工具。<br>
 */
package com.hrhg.framework.utils;

import java.util.Date;

/**
 * 类工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class ClazzUtils {
    /**
     * 基本包装类类型
     */
    public static String CLAZZ_WRAP_TYPE = "TYPE";

    /**
     * 私有构造函数。<br>
     */
    private ClazzUtils() {
    }

    /**
     * 创建工厂对象。<br>
     *
     * @param clazz 对象类型名称
     * @param <T>   实例化对象类型
     * @return 实例化对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T create(Class<T> clazz) {
        try {
            // 实例化对象
            return (T) Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查类型是否为原始或包装基本类型。<br>
     *
     * @param clazz 检查数据类型
     * @return 检查结果：true、基本包装类型(Integer、Double、Float、Long、Short、
     * Boolean、Byte、Char、Void)；false、自定义包装类型
     */
    public static boolean isWrapClass(Class clazz) {
        try {
            return ((Class) clazz.getField(CLAZZ_WRAP_TYPE).get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查类型是否为原始或包装基本类型。<br>
     *
     * @param clazz 检查数据类型
     * @return 检查结果：true、基本类型(int/Integer、double/Double、float/Float、long/Long、short/Short、
     * boolean/Boolean、byte/Byte、char/Char、void/Void)；false、自定义包装类型
     */
    public static boolean isWrapOrPrimitive(Class clazz) {
        // 根据类型字段属性，判断是否为基本类型数据
        if (clazz.isPrimitive()) {
            // 原始基本类型
            return true;
        }

        // 检查是否基本包装类型
        return isWrapClass(clazz);
    }

    /**
     * 检查类型是否为字符串类型。<br>
     *
     * @param clazz 检查类型对象
     * @return true、字符串类型；false、非字符串类型
     */
    public static boolean isStringClass(Class clazz) {
        return String.class == clazz;
    }

    /**
     * 检查类型是否为字符串或基本类型。<br>
     *
     * @param clazz 检查类型对象
     * @return true、是否为字符串或基本类型；false、非是否为字符串或基本类型
     */
    public static boolean isStringOrWrapPrimitive(Class clazz) {
        if (isWrapOrPrimitive(clazz)) {
            // 基本类型的情况
            return true;
        }

        // 字符串类型检查结果
        return isStringClass(clazz);
    }

    /**
     * 将源类型转换为目标类型。<br>
     *
     * @param clazzSrc  源类型
     * @param clazzDesc 目标类型
     * @param src       源对象
     * @return 目标类型的对象
     */
    public static Object convertObject(Class<?> clazzSrc, Class<?> clazzDesc, Object src) {
        if (null == clazzSrc || null == clazzDesc) {
            // 类型为空
            return null;
        }

        if (DataUtils.isNullOrEmpty(src)) {
            // 数据源对象为空
            return null;
        }

        if (clazzSrc == clazzDesc) {
            // 源类型和目标类型相同
            return src;
        }

        // 数据类型不同时
        if (isStringOrWrapPrimitive(clazzSrc) && isStringOrWrapPrimitive(clazzDesc)) {
            // 都是基本类型或字符串
            if (isStringClass(clazzDesc)) {
                // 基本类型转换为字符串
                return src.toString();
            } else if (isStringClass(clazzSrc)) {
                // 字符串转换为基本类型
                if (Integer.class == clazzDesc) {
                    return Integer.parseInt((String) src);
                } else if (Double.class == clazzDesc) {
                    return Double.parseDouble((String) src);
                } else if (Float.class == clazzDesc) {
                    return Float.parseFloat((String) src);
                } else if (Long.class == clazzDesc) {
                    return Long.parseLong((String) src);
                } else if (Short.class == clazzDesc) {
                    return Short.parseShort((String) src);
                } else if (Boolean.class == clazzDesc) {
                    return Boolean.parseBoolean((String) src);
                } else if (Byte.class == clazzDesc) {
                    return Byte.parseByte((String) src);
                }
            } else {
                // 基本类型，直接转化
                return clazzDesc.cast(src);
            }
        } else if (Date.class == clazzSrc && Long.class == clazzDesc) {
            // 日期->时间戳
            return DateUtils.toTimeStamp((Date) src);
        } else if (Date.class == clazzDesc && Long.class == clazzSrc) {
            // 时间戳->日期
            return DateUtils.toDate((Long) src);
        }

        return null;
    }
}
