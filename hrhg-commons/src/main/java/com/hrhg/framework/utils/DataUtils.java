/**
 * 数据工具。<br>
 */
package com.hrhg.framework.utils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 数据工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class DataUtils {
    /**
     * 逗号
     */
    private static final String COMMA = ",";
    /**
     * 格式定义——key，value
     */
    private static final String FORMAT_JSON_PARAM = "\"%s\":\"%s\"";
    /**
     * 格式定义——结果
     */
    private static final String FORMAT_JSON_RESULT = "{%s}";
    /**
     * 格式定义-协议
     */
    private static final String FORMAT_PROTO = "cm://";
    /**
     * 系统内功能-节分隔符
     */
    private static final String FORMAT_PROTO_SEC_SPLIT = "&";
    /**
     * 格式定义——key，value
     */
    private static final String FORMAT_URL_SEC_PARAM = "%s=%s";
    /**
     * 格式定义——指定位数数字格式化
     */
    private static final String FORMAT_STRINGBIT = "%%0%dd";
    /**
     * 默认随机数位数
     */
    private static final int DEFAULT_RANDOM_BIT = 4;
    /**
     * 每段版本位数
     */
    private static final int DEFAULT_VER_BIT = 4;
    /**
     * 加密类型_MD5
     */
    public static final String DIGEST_TYPE = "MD5";
    /**
     * 加密类型_SHA1
     */
    public static final String DIGEST_SHA1 = "SHA1";

    /**
     * 私有构造函数。<br>
     */
    private DataUtils() {
    }

    /**
     * 检查是否为空(String)。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(String data) {
        return (null == data || 0 == data.length());
    }

    /**
     * 检查是否为空（Boolean）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Boolean data) {
        return null == data;
    }

    /**
     * 检查是否为空（Integer）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Integer data) {
        return null == data;
    }

    /**
     * 检查是否为空（Long）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Long data) {
        return null == data;
    }

    /**
     * 检查是否为空（Long）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Date data) {
        return null == data;
    }

    /**
     * 检查是否为空（BigDecimal）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(BigDecimal data) {
        return null == data;
    }

    /**
     * 检查是否为空（Object）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Object data) {
        return null == data;
    }

    /**
     * 检查是否为空（Collection）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Collection data) {
        return null == data || 0 == data.size();
    }

    /**
     * 检查是否为空（Map）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Map data) {
        return null == data || 0 == data.size();
    }

    /**
     * 检查是否为空（Object[]）。<br>
     *
     * @param data 检查数据
     * @return 检查结果(空:true;非空:false)
     */
    public static boolean isNullOrEmpty(Object[] data) {
        return null == data || 0 == data.length;
    }

    /**
     * 比较两个整数是否相等。<br>
     *
     * @param one     第一个数
     * @param another 第二个数
     * @return true：相等；false：不相等
     */
    public static <T extends Comparable<T>> boolean equelsNumber(T one, T another) {
        if (null == one && null == another) {
            // 均为空
            return true;
        }

        if (null == one || null == another) {
            // 其中一个为空
            return false;
        }

        // 均不为空
        return 0 == one.compareTo(another);
    }

    /**
     * 生成默认4位长度的随机数字符串。<br>
     *
     * @return 随机字符串
     */
    public static String makeRandom() {
        return makeRandom(DEFAULT_RANDOM_BIT);
    }

    /**
     * 生成指定长度的随机数字符串。<br>
     *
     * @param bit 位数
     * @return 随机字符串
     */
    public static String makeRandom(int bit) {
        if (bit < 0) {
            return StringUtils.EMPTY_STRING;
        }

        // 指定位数随机数
        Random random = new Random();
        int num = random.nextInt((int) Math.pow(10, bit));
        // 获取指定位数的随机数
        return makeFormatNum(num, bit);
    }

    /**
     * 将数字格式化成指定位数的字符串。<br>
     *
     * @param num 数字
     * @return 格式化后指定
     */
    public static String makeFormatNum(int num) {
        return makeFormatNum(num, DEFAULT_VER_BIT);
    }

    /**
     * 将数字格式化成指定位数的字符串。<br>
     *
     * @param num 数字
     * @param bit 位数
     * @return 格式化后指定
     */
    public static String makeFormatNum(int num, int bit) {
        if (bit < 0) {
            return StringUtils.EMPTY_STRING;
        }

        // 生成位数字符串格式
        String format = String.format(FORMAT_STRINGBIT, bit);
        // 获取指定位数的数字
        return String.format(format, num);
    }

    /**
     * 按照指定字符位数分隔字符串，转换为整数数组。<br>
     *
     * @param data 字符串数组
     * @return 整数数组
     */
    public static List<Integer> splitString(String data) {
        return splitString(data, DEFAULT_VER_BIT);
    }

    /**
     * 按照指定字符位数分隔字符串，转换为整数数组。<br>
     *
     * @param data 字符串数组
     * @param bit  位数
     * @return 整数数组
     */
    public static List<Integer> splitString(String data, int bit) {
        // 数组集合
        List<Integer> nums = new ArrayList<Integer>();

        if (bit < 0) {
            return nums;
        }

        // 子字符串
        String subData = data;

        while (StringUtils.isNotEmpty(subData)) {
            // 解析字符串
            String paraseData = subData.substring(0, bit);

            // 整数结果
            Integer resultNum = 0;

            try {
                // 转化字符串为数字
                resultNum = Integer.parseInt(paraseData);
            } catch (NumberFormatException ex) {
                // 强制转化为0
                resultNum = 0;
            }

            // 添加结果
            nums.add(resultNum);
            // 获取剩余字符串
            subData = subData.substring(bit);
        }

        return nums;
    }

    /**
     * 生成ID工具(格式：前缀+参数序列+时间戳字符串+后缀)。<br>
     *
     * @param prefix 前缀参数
     * @param param  用户参数序列吧
     * @return 生成的ID
     */
    public static String makeId(String prefix, String... param) {
        // 字符串生成器
        StringBuilder builder = new StringBuilder();
        // 添加前缀字符
        builder.append(prefix);
        // 添加时间戳参数
        builder.append(DateUtils.getTimeStamp());

        for (String str : param) {
            // 逐个添加用户参数
            builder.append(str);
        }

        // 生成MD5加密字符串ID
        return toMd5(builder.toString());
    }

    /**
     * MD5加密。<br>
     *
     * @param inputStr 转换值
     * @return 密文
     * @throws Exception 异常类型
     */
    public static String toMd5(String inputStr) {
        // 初始化结果字符串
        String resultStr = StringUtils.EMPTY_STRING;

        try {
            // MD5加密
            MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_TYPE);
            // 获取字节序列
            byte[] byteMessage = messageDigest.digest(inputStr.getBytes(StringUtils.ENCODING_UTF8));
            resultStr = toHex(byteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultStr;
    }

    /**
     * 获取该输入流的MD5值。<br>
     *
     * @param is 输入流
     * @return md5字符串
     * @throws Exception 异常类型
     */
    public static String toMd5(InputStream is) throws Exception {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance(DIGEST_TYPE);
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return md5.toString();
    }

    /**
     * 16进制编码
     *
     * @param hash
     * @return String
     */
    private static String toHex(byte[] hash) {
        // 字符串生成器
        StringBuffer buf = new StringBuffer(hash.length * 2);
        for (int i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xFF) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xFF, 16));
        }
        return buf.toString();
    }

    /**
     * sha1加密。<BR>
     *
     * @param str 转换值
     * @return 密文
     */
    public static String toSha1(String str) {
        if (DataUtils.isNullOrEmpty(str)) {
            // 字符串为空
            return null;
        }

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance(DIGEST_SHA1);
            mdTemp.update(str.getBytes(StringUtils.ENCODING_GBK));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串数组，转换为以某个字符分割的字符串。<br>
     *
     * @param list  字符串数组
     * @param split 分隔符
     * @return 返回分割字符串，没有则返回空字符串
     */
    public static String listToString(List<String> list, String split) {
        if (DataUtils.isNullOrEmpty(list)) {
            // 空列表时
            return StringUtils.EMPTY_STRING;
        }

        if (null == split) {
            // 分隔符为空
            split = StringUtils.EMPTY_STRING;
        }

        return String.join(split, list.toArray(new String[list.size()]));
    }

    /**
     * 根据map对象生成json字符串。<br>
     *
     * @param params map对象
     * @return json字符串
     */
    public static String map2Json(LinkedHashMap<String, String> params) {
        return String.format(FORMAT_JSON_RESULT, map2KV(params, FORMAT_JSON_PARAM, COMMA));
    }

    /**
     * 根据map对象生成json字符串。<br>
     *
     * @param params map对象
     * @return json字符串
     */
    public static String map2URLSec(LinkedHashMap<String, String> params) {
        return FORMAT_PROTO + map2KV(params, FORMAT_URL_SEC_PARAM, FORMAT_PROTO_SEC_SPLIT);
    }

    /**
     * 根据map对象生成json字符串。<br>
     *
     * @param params map对象
     * @param format Key-Value组合方式
     * @param split  每段分隔符
     * @return json字符串
     */
    public static String map2KV(LinkedHashMap<String, String> params, String format, String split) {
        if (DataUtils.isNullOrEmpty(params)) {
            // 集合为空的情况
            return StringUtils.EMPTY_STRING;
        }

        // 参数生成器
        StringBuilder sbParam = new StringBuilder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            if (sbParam.length() > 0) {
                // 非第一组参数
                sbParam.append(split);
            }

            // 添加一组参数
            sbParam.append(String.format(format, param.getKey(), param.getValue()));
        }

        return sbParam.toString();
    }

    /**
     * 根据原排序结果集合，新序列ID集合，生成组合后新的排序集合。<br>
     *
     * @param sortMap 原排序结果集合(1、链表集合顺序为原排列顺序；2、Key为对象ID；3、Value为新的排序结果值，默认为空)
     * @param newAdd  新序列ID集合(1、列表元素为对象ID；2、顺序新的排序序列)
     * @return 组合后新的排序集合
     */
    public static Map<String, Integer> makeNewSortMap(Map<String, Integer> sortMap, List<String> newAdd) {
        // 检查参数
        if (isNullOrEmpty(sortMap)) {
            // 原排序结果集合为空
            sortMap = new LinkedHashMap<>();
        }

        if (isNullOrEmpty(newAdd)) {
            // 新的排序列表为空
            return sortMap;
        }

        for (int ii = 0; ii < newAdd.size(); ii++) {
            // 循环处理新添加列表集合
            sortMap.put(newAdd.get(ii), ii + 1);
        }

        // 返回组合后新的排序集合
        return sortMap;
    }

    /**
     * 添加非空对象到列表。<br>
     *
     * @param list 对象列表
     * @param obj  对象
     * @param <T>  类型
     */
    public static <T extends Comparable<T>> void addNotNull2List(List<T> list, T obj) {
        addNotNull2List(list, obj, false);
    }

    /**
     * 添加非空对象到列表。<br>
     *
     * @param list    对象列表
     * @param obj     对象
     * @param notFlag 排除对象标记：true、排除检查；false、不排除检查，直接添加
     * @param <T>     类型
     */
    public static <T extends Comparable<T>> void addNotNull2List(List<T> list, T obj, boolean notFlag) {
        if (null == list || isNullOrEmpty(obj)) {
            // 列表或对象为空
            return;
        }

        if (!notFlag) {
            // 排除对象为空
            list.add(obj);
            return;
        }

        if (list.contains(obj)) {
            // 已经存在的情况
            return;
        }

        // 添加新的电话
        list.add(obj);
    }
}
