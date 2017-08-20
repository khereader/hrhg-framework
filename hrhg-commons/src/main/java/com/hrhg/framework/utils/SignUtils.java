/**
 * 签名工具通用类。<br>
 */
package com.hrhg.framework.utils;

import java.util.Map;

/**
 * 签名工具通用类。<br>
 */
public final class SignUtils {
    /**
     * KeyValue拼接格式。<br>
     */
    public static String FORMAT_KEYVALUE = "%s=%s";
    /**
     * KeyValue分隔符。<br>
     */
    public static String SPLIT_KEYVALUE = "&";
    /**
     * 签名key。<br>
     */
    public static String SIGN_KEY = "key";

    /**
     * 私有构造函数。<br>
     */
    private SignUtils() {
    }

    /**
     * 将给定的bean转换为Map, 排除属性值为null的属性
     *
     * @param obj     bean对象
     * @param signKey 生成签名的混淆Key
     * @return 生成的签名
     * @throws Exception 异常输出
     */
    public static String makeBeanSign(Object obj, String signKey) throws Exception {
        if (null == obj) {
            // 对象为空
            return StringUtils.EMPTY_STRING;
        }

        // 获取Bean对应的属性-值
        Map<String, Object> beanMaps = BeanUtils.beanToMap(obj);
        // 生成通讯报文集合
        String stringA = makeStringA(beanMaps);

        if (StringUtils.isEmpty(stringA)) {
            // 通讯报文集合为空
            return StringUtils.EMPTY_STRING;
        }

        // 生成签名
        return makeSignByStringAKey(stringA, signKey);
    }

    /**
     * 获取通讯报文信息集合。<br>
     * 将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），<br>
     * 使用URL键值对的格式（即key1=value1&key2=value2…）<br>
     * 拼接成字符串stringA。 特别注意以下重要规则：<br>
     * ◆ 参数名ASCII码从小到大排序（字典序）；<br>
     * ◆ 如果参数的值为空不参与签名；<br>
     * ◆ 参数名区分大小写；<br>
     * ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。<br>
     * 属性字段类型如果为自定义Class时，将其转换为json字符串，作为其值的内容。<br>
     *
     * @param keyValues keyValue值对。
     * @return 内容参数值排序字符串
     */
    public static String makeStringA(Map<String, Object> keyValues) throws Exception {
        if (DataUtils.isNullOrEmpty(keyValues)) {
            // 集合为空时
            return StringUtils.EMPTY_STRING;
        }

        // 通讯报文字符串生成器
        StringBuilder sb = new StringBuilder();

        // 逐个元素处理
        for (Map.Entry<String, Object> entry : keyValues.entrySet()) {
            if (StringUtils.isEmpty(entry.getKey())) {
                // key为空时
                continue;
            }

            if (DataUtils.isNullOrEmpty(entry.getValue())) {
                // 值为空时
                continue;
            }

            // 获取Value字符串
            String value;

            if (ClazzUtils.isStringOrWrapPrimitive(entry.getValue().getClass())) {
                // 基本类型取值时
                value = entry.getValue().toString();
            } else {
                // 自定义包装类型时
                value = JsonUtils.object2Json(entry.getValue());
            }

            if (StringUtils.isEmpty(value)) {
                // 获取字符串对象为空时
                continue;
            }

            if (sb.length() > 0) {
                // 非第一次添加报文数据
                sb.append(SPLIT_KEYVALUE);
            }

            // 添加key-value结果
            sb.append(String.format(FORMAT_KEYVALUE, entry.getKey(), value));
        }

        return sb.toString();
    }

    /**
     * 根据报文集合和签名混淆key，生成签名值。<br>
     * 在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，<br>
     * 再将得到的字符串所有字符转换为大写，得到sign值signValue。<br>
     * key：预先设定的apiKey，例：20a49e61-d21a-464d-8718-3e33b2cf1763<br>
     *
     * @param stringA 通讯报文集合
     * @param signKey 生成签名的混淆Key
     * @return 生成的签名
     */
    public static String makeSignByStringAKey(String stringA, String signKey) {
        if (StringUtils.isEmpty(stringA)) {
            // 通讯报文集合为空时
            return StringUtils.EMPTY_STRING;
        }

        // 临时签名字符串生成器
        StringBuilder sb = new StringBuilder(stringA);

        if (StringUtils.isNotEmpty(signKey)) {
            // 混淆关键字不为空时
            sb.append(SPLIT_KEYVALUE);
            // 添加混淆关键字key-value
            sb.append(String.format(FORMAT_KEYVALUE, SIGN_KEY, signKey));
        }

        // 临时签名字符串
        String signTemp = sb.toString();

        // 生成签名结果
        return DataUtils.toMd5(signTemp).toUpperCase();
    }
}
