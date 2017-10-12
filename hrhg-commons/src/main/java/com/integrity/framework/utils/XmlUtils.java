/**
 * Json工具通用类。<br>
 */
package com.integrity.framework.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Json工具通用类。<br>
 */
public final class XmlUtils {
    /**
     * 私有构造函数。<br>
     */
    private XmlUtils() {
    }

    /**
     * 将对象转换为json字符串。<br>
     *
     * @param obj 实体对象
     * @param <T> 类型参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    public static <T extends Object> String object2Xml(T obj) throws Exception {
        if (null == obj) {
            // 对象为空
            return StringUtils.EMPTY_STRING;
        }

        // 创建jackson序列化映射对象
        XmlMapper mapper = new XmlMapper();

        return mapper.writeValueAsString(obj);
    }

    /**
     * 将json字符串转换为对象。<br>
     *
     * @param xml   json字符串
     * @param clazz 待转换对象类型
     * @param <T>   类型参数
     * @return 转换后对象
     * @throws Exception 系统异常
     */
    public static <T extends Object> T xml2Object(String xml, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(xml)) {
            // json字符串为空
            return null;
        }

        // 创建jackson序列化映射对象
        XmlMapper mapper = new XmlMapper();
        // 忽略未识别字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(xml, clazz);
    }

    /**
     * 将对象集合转换为json字符串。<br>
     *
     * @param objs 实体对象集合
     * @param <T>  类型参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    public static <T extends Object> String xml2ObjectArray(List<T> objs) throws Exception {
        if (DataUtils.isNullOrEmpty(objs)) {
            // 对象集合为空
            return StringUtils.EMPTY_STRING;
        }

        // 创建jackson序列化映射对象
        XmlMapper mapper = new XmlMapper();

        return mapper.writeValueAsString(objs);
    }

    /**
     * 将json字符串转换为对象集合。<br>
     *
     * @param xml   json字符串
     * @param clazz 待转换对象类型
     * @param <T>   类型参数
     * @return 转换后对象集合
     * @throws Exception 系统异常
     */
    public static <T extends Object> List<T> xml2ObjectArray(String xml, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(xml)) {
            // json字符串为空
            return null;
        }

        // 创建jackson序列化映射对象
        XmlMapper mapper = new XmlMapper();
        // 忽略未识别字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 获取类型序列化类型
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        // 生成列表类型结果
        return mapper.readValue(xml, javaType);
    }
}
