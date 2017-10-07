/**
 * Bean工具通用类。<br>
 */
package com.integrity.framework.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Bean工具类。<br>
 */
public final class BeanUtils {
    /**
     * 忽略属性名
     */
    public static final String PROPERTY_NAME_IGNORE = "class";
    /**
     * 文案后缀属性
     */
    public static final String POSTFIX_PROPERTY_NAME_TEXT = "Text";

    /**
     * 私有构造函数。<br>
     */
    private BeanUtils() {
    }

    /**
     * 获取非空只读属性个数。<br>
     *
     * @param bean Bean对象
     * @return 非空属性个数
     */
    public static int readPropertyNotEmptyCount(Object bean) {
        // 非空属性个数
        int count = 0;

        if (null == bean) {
            // 对象为空
            return count;
        }

        try {
            // Bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            // 属性信息
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                // 属性名
                String propertyName = property.getName();

                if (0 == propertyName.compareToIgnoreCase(PROPERTY_NAME_IGNORE)) {
                    // 忽略的属性名
                    continue;
                }

                // 获取只读属性
                Method getter = property.getReadMethod();

                if (null == getter) {
                    // 只读属性为空
                    continue;
                }

                // 属性值
                Object value = getter.invoke(bean);

                if (DataUtils.isNullOrEmpty(value)) {
                    // 对象为空
                    continue;
                }

                if (!StringUtils.isStringType(value)) {
                    // 非字符串类型
                    continue;
                }

                if (StringUtils.isEmpty((String) value)) {
                    // 空字符串
                    continue;
                }

                // 统计增量
                count++;
            }
        } catch (Exception e) {
            return count;
        }

        return count;
    }

    /**
     * 将给定的bean转换为Map, 排除属性值为null的属性，Key按照升序方式排序。
     * 非系统类型对象，整体转换为json字符串，作为字符串处理。<br>
     *
     * @param obj bean对象
     * @return 字段集合
     * @throws Exception 异常输出
     */
    public static SortedMap<String, Object> beanToMap(Object obj) throws Exception {
        if (null == obj) {
            // 对象为空
            return null;
        }

        // 定义对象属性map
        SortedMap<String, Object> map = new TreeMap<>();
        // Bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        // 属性信息
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            // 属性名
            String key = property.getName();

            if (key.compareToIgnoreCase(PROPERTY_NAME_IGNORE) == 0) {
                // 忽略的属性名
                continue;
            }

            // 获取属性方法
            Method getter = property.getReadMethod();
            // 获取属性值
            Object value = (null == getter) ? null : getter.invoke(obj);

            if (null != value) {
                // 添加属性数据对
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * 复制Bean中属性值到另一个Bean（属性名相同的属性）。<br>
     *
     * @param src  源数据Bean
     * @param desc 目标数据Bean
     * @throws Exception 异常输出
     */
    public static void copyBeanValue(Object src, Object desc) throws Exception {
        copyBeanValue(src, desc, true, false);
    }

    /**
     * 复制Bean中属性值到另一个Bean（属性名相同的属性）。<br>
     *
     * @param src    源数据Bean
     * @param desc   目标数据Bean
     * @param ignore 忽略属性集合
     * @throws Exception 异常输出
     */
    public static void copyBeanValue(Object src, Object desc, List<String> ignore) throws Exception {
        if (DataUtils.isNullOrEmpty(ignore)) {
            copyBeanValue(src, desc, false, true);
        } else {
            copyBeanValue(src, desc, false, true, ignore.toArray(new String[ignore.size()]));
        }
    }

    /**
     * 复制Bean中属性值到另一个Bean（属性名相同的属性）。<br>
     *
     * @param src        源数据Bean
     * @param desc       目标数据Bean
     * @param emptyIgone 是否忽略NULL或空字符串
     * @param emptyInit  是否空字段初始化赋值
     * @param ignore     忽略属性集合
     * @throws Exception 异常输出
     */
    public static void copyBeanValue(Object src, Object desc, boolean emptyIgone,
                                     boolean emptyInit, String... ignore) throws Exception {
        if (null == src || null == desc) {
            // 对象为空
            return;
        }

        // 目标Bean信息
        BeanInfo descBeanInfo = Introspector.getBeanInfo(desc.getClass());
        // 属性信息
        PropertyDescriptor[] descProperty = descBeanInfo.getPropertyDescriptors();

        if (DataUtils.isNullOrEmpty(descProperty)) {
            // 目标属性为空
            return;
        }

        // 获取源Bean属性信息
        Map<String, Method> srcReadMethod = getReadMethod(src);

        if (DataUtils.isNullOrEmpty(srcReadMethod)) {
            // 源Bean属性信息为空
            return;
        }

        // 忽略属性集合
        List<String> igoneProperties = new ArrayList<>();
        // 系统忽略属性
        igoneProperties.add(PROPERTY_NAME_IGNORE);

        if (!DataUtils.isNullOrEmpty(ignore)) {
            // 忽略字段集合为空
            igoneProperties.addAll(Arrays.asList(ignore));
        }

        // 设置目标Bean属性
        for (PropertyDescriptor property : descProperty) {
            // 属性名
            String propertyName = property.getName();

            if (igoneProperties.contains(propertyName)) {
                // 忽略的属性名
                continue;
            }

            // 获取设置属性
            Method setter = property.getWriteMethod();

            if (null == setter) {
                // 设置属性为空
                continue;
            }

            // 根据目标属性名获取源属性
            Method getter = srcReadMethod.get(propertyName);

            if (null == getter) {
                // 源Bean的只读属性为空
                continue;
            }

            // 获取只读属性值
            Object value = getter.invoke(src);

            if (DataUtils.isNullOrEmpty(value)) {
                if (emptyIgone) {
                    // 忽略空对象时
                    continue;
                }

                if (emptyInit) {
                    // 初始化对象时
                    value = ClazzUtils.create(getter.getReturnType());
                }
            }

            // 数据类型不同
            if (1 != setter.getParameterCount()) {
                // 参数个数不正确
                continue;
            }

            // 获取目标属性类型的源对象
            Object result = ClazzUtils.convertObject(getter.getReturnType(), setter.getParameterTypes()[0], value);

            // 设置属性值
            setter.invoke(desc, result);
        }
    }

    /**
     * 指定字段复制Bean中属性值到另一个Bean（属性名相同的属性）。<br>
     *
     * @param src        源数据Bean
     * @param desc       目标数据Bean
     * @param properties 复制字段信息
     * @throws Exception 异常输出
     */
    public static void filterCopyBeanValue(Object src, Object desc, List<String> properties) throws Exception {
        if (DataUtils.isNullOrEmpty(properties)) {
            copyBeanValue(src, desc);
        } else {
            filterCopyBeanValue(src, desc, properties.toArray(new String[properties.size()]));
        }
    }

    /**
     * 指定字段复制Bean中属性值到另一个Bean（属性名相同的属性）。<br>
     *
     * @param src        源数据Bean
     * @param desc       目标数据Bean
     * @param properties 复制字段信息
     * @throws Exception 异常输出
     */
    public static void filterCopyBeanValue(Object src, Object desc, String... properties) throws Exception {
        if (null == src || null == desc) {
            // 对象为空
            return;
        }

        if (DataUtils.isNullOrEmpty(properties)) {
            // 待复制字段集合为空
            return;
        }

        // 目标Bean信息
        BeanInfo descBeanInfo = Introspector.getBeanInfo(desc.getClass());
        // 属性信息
        PropertyDescriptor[] descProperty = descBeanInfo.getPropertyDescriptors();

        if (DataUtils.isNullOrEmpty(descProperty)) {
            // 目标属性为空
            return;
        }

        // 获取源Bean属性信息
        Map<String, Method> srcReadMethod = getReadMethod(src);

        if (DataUtils.isNullOrEmpty(srcReadMethod)) {
            // 源Bean属性信息为空
            return;
        }

        // 忽略属性集合
        List<String> copyProperties = new ArrayList<>();

        if (!DataUtils.isNullOrEmpty(properties)) {
            // 待处理字段集合为空
            copyProperties.addAll(Arrays.asList(properties));
        }

        // 设置目标Bean属性
        for (PropertyDescriptor property : descProperty) {
            // 属性名
            String propertyName = property.getName();

            if (!(copyProperties.contains(propertyName) ||
                    copyProperties.contains(propertyName + POSTFIX_PROPERTY_NAME_TEXT))) {
                // 不是要复制的属性名
                continue;
            }

            // 获取设置属性
            Method setter = property.getWriteMethod();

            if (null == setter) {
                // 设置属性为空
                continue;
            }

            // 根据目标属性名获取源属性
            Method getter = srcReadMethod.get(propertyName);

            if (null == getter) {
                // 源Bean的只读属性为空
                continue;
            }

            // 获取只读属性值
            Object value = getter.invoke(src);

            if (DataUtils.isNullOrEmpty(value)) {
                // 初始化对象时
                value = ClazzUtils.create(getter.getReturnType());
            }

            // 数据类型不同
            if (1 != setter.getParameterCount()) {
                // 参数个数不正确
                continue;
            }

            // 获取目标属性类型的源对象
            Object result = ClazzUtils.convertObject(getter.getReturnType(), setter.getParameterTypes()[0], value);

            // 设置属性值
            setter.invoke(desc, result);
        }
    }

    /**
     * 根据属性名获取bean的属性值。<br>
     *
     * @param bean         Bean对象
     * @param propertyName 属性名
     * @return 属性值
     * @throws Exception 异常输出
     */
    public static Object getValueByPropertyName(Object bean, String propertyName) throws Exception {
        // 获取属性对象
        PropertyDescriptor property = findBeanProperty(bean, propertyName);

        if (DataUtils.isNullOrEmpty(property)) {
            // 获取属性对象为空时
            return null;
        }

        // 获取只读属性
        Method getter = property.getReadMethod();

        if (null == getter) {
            // 只读属性方法为空时
            return null;
        }

        // 返回只读属性值
        return getter.invoke(bean);
    }

    /**
     * 根据属性名设置bean的属性值。<br>
     *
     * @param bean         Bean对象
     * @param propertyName 属性名
     * @param value        属性值
     * @throws Exception 异常输出
     */
    public static void setValueByPropertyName(Object bean, String propertyName, Object value) throws Exception {
        // 获取属性对象
        PropertyDescriptor property = findBeanProperty(bean, propertyName);

        if (DataUtils.isNullOrEmpty(property)) {
            // 获取属性对象为空时
            return;
        }

        // 获取可写属性
        Method setter = property.getWriteMethod();

        if (null == setter) {
            // 可写属性方法为空时
            return;
        }

        // 设置属性值
        setter.invoke(bean, value);
    }

    /**
     * 根据属性名称获取Bean属性对象。<br>
     *
     * @param bean         Bean对象
     * @param propertyName 属性名
     * @return Bean属性对象
     * @throws Exception 异常输出
     */
    public static PropertyDescriptor findBeanProperty(Object bean, String propertyName) throws Exception {
        if (null == bean) {
            // 对象为空
            return null;
        }

        if (StringUtils.isEmpty(propertyName)) {
            // 属性名称为空
            return null;
        }

        // 目标Bean信息
        BeanInfo descBeanInfo = Introspector.getBeanInfo(bean.getClass());
        // 属性信息
        PropertyDescriptor[] propertyDescriptors = descBeanInfo.getPropertyDescriptors();

        if (DataUtils.isNullOrEmpty(propertyDescriptors)) {
            // 属性集合为空
            return null;
        }

        for (PropertyDescriptor property : propertyDescriptors) {
            if (StringUtils.isEquals(propertyName, property.getName())) {
                // 找到属性对象
                return property;
            }
        }

        return null;
    }

    /**
     * 获取Bean读取属性信息。<br>
     *
     * @param bean 解析Bean信息
     * @return 读取属性集合
     * @throws Exception 异常输出
     */
    private static Map<String, Method> getReadMethod(Object bean) throws Exception {
        if (null == bean) {
            // 对象为空
            return null;
        }

        // 目标Bean信息
        BeanInfo descBeanInfo = Introspector.getBeanInfo(bean.getClass());
        // 属性信息
        PropertyDescriptor[] propertyDescriptors = descBeanInfo.getPropertyDescriptors();

        if (DataUtils.isNullOrEmpty(propertyDescriptors)) {
            // 属性集合为空
            return null;
        }

        // 创建属性集合
        Map<String, Method> readMethod = new HashMap<>();

        for (PropertyDescriptor property : propertyDescriptors) {
            // 属性名
            String propertyName = property.getName();

            if (0 == propertyName.compareToIgnoreCase(PROPERTY_NAME_IGNORE)) {
                // 忽略的属性名
                continue;
            }

            // 获取只读属性
            Method getter = property.getReadMethod();

            if (null == getter) {
                // 只读属性为空
                continue;
            }

            // 添加只读属性
            readMethod.put(propertyName, getter);
        }

        return readMethod;
    }
}
