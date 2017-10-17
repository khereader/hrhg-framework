/**
 * 字符串工具。<br>
 */
package com.integrity.framework.utils;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class StringUtils {
    /**
     * 字符集_GBK
     */
    public static final String ENCODING_GBK = "GBK";
    /**
     * 字符集_UTF8
     */
    public static final String ENCODING_UTF8 = "UTF-8";
    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = new String();
    /**
     * 检查字段字符串
     */
    public static final String CHECK_STRING = "check";
    /**
     * 逗号字符串
     */
    public static final String COMMA_STRING = ",";
    /**
     * 点号字符串
     */
    public static final String DOT_STRING = ".";
    /**
     * 空字符串
     */
    public static final String NULL_STRING = "";
    /**
     * 线字符串
     */
    public static final String LINE_STRING = "-";
    /**
     * 空字符串数组
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * 正则表达式_整型数字
     */
    public static final String PATTERN_INT_TEXT = "^\\d+$";
    /**
     * 正则表达式_逗号分割
     */
    public static final String PATTERN_COMMA_SPLIT_TEXT = "\\s*[,]+\\s*";
    /**
     * 正则表达式_点号分割
     */
    public static final String PATTERN_DOT_SPLIT_TEXT = "\\s*[.]+\\s*";
    /**
     * 正则表达式_Key&Value数据对
     */
    public static final String PATTERN_KVP_TEXT = "([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)";
    /**
     * 正则表达式_手机号码格式
     */
    public static final String PATTERN_IS_PHONE_TEXT = "^1[0-9]{10}$";
    /**
     * 正则表达式_邮箱格式
     */
    public static final String PATTERN_IS_EMAIL_TEXT = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    /**
     * 正则表达式_手机号码查询
     */
    public static final String PATTERN_PHONE_TEXT = "(\\d{3})\\d{4}(\\d{4})";
    /**
     * 正则表达式_手机号码隐藏字段替换
     */
    public static final String PATTERN_PHONE_REPLACE = "$1****$2";
    /**
     * 正则表达式_浮点百分比
     */
    public static final String PATTERN_PERCENTAGE_FLOAT = "%.2f%%";
    /**
     * 正则表达式_浮点百分比分母基数
     */
    public static final double BASE_PERCENTAGE_FLOAT = 100.0;
    /**
     * 正则匹配模式_整型数字
     */
    private static final Pattern PATTERN_INT = Pattern.compile(PATTERN_INT_TEXT);
    /**
     * 正则匹配模式_逗号分割
     */
    private static final Pattern PATTERN_COMMA_SPLIT = Pattern.compile(PATTERN_COMMA_SPLIT_TEXT);
    /**
     * 正则匹配模式_Key&Value数据对
     */
    private static final Pattern PATTERN_KVP = Pattern.compile(PATTERN_KVP_TEXT);
    /**
     * 随机数默认位数
     */
    private static final int RANDOM_BIT_NUM = 4;
    /**
     * 随机数格式
     */
    private static final String RANDOM_CODE_FORMAT = "%%0%dd";

    /**
     * 私有构造函数。<br>
     */
    private StringUtils() {
    }

    /**
     * 检查是否为字符串类型。<br>
     *
     * @param str 检查字符串类型
     * @return true:字符串；false:非字符串
     */
    public static boolean isStringType(Object str) {
        if (null == str) {
            return false;
        }

        return String.class == str.getClass();
    }

    /**
     * 检查是否为非空字符串。<br>
     *
     * @param str 检查字符串
     * @return true:非空；false:空
     */
    public static boolean isNotEmpty(String str) {
        return null != str && str.length() > 0;
    }

    /**
     * 检查是否为空字符串。<br>
     *
     * @param str 检查字符串
     * @return true:空；false:非空
     */
    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    /**
     * 判断两个非空字符串是否相同。<br>
     *
     * @param s1 比较字符串1
     * @param s2 比较字符串2
     * @return 比较结果（true：相同或均为空；false：不同）
     */
    public static boolean isNotEmptyEquals(String s1, String s2) {
        return isNotEmptyEquals(s1, s2, false);
    }

    /**
     * 判断两个非空字符串是否相同。<br>
     *
     * @param s1         比较字符串1
     * @param s2         比较字符串2
     * @param ignoreCase 是否区分大小写(true:不区分；false：区分)
     * @return 比较结果（true：相同或均为空；false：不同）
     */
    public static boolean isNotEmptyEquals(String s1, String s2, boolean ignoreCase) {
        if (isEmpty(s1) && isEmpty(s2)) {
            // 两字符串均为空
            return true;
        }

        if (isEmpty(s1) || isEmpty(s2)) {
            // 其中一个为空
            return false;
        }

        return ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
    }

    /**
     * 判断两个字符串是否相同。<br>
     *
     * @param s1 比较字符串1
     * @param s2 比较字符串2
     * @return 比较结果（true：相同；false：不同）
     */
    public static boolean isEquals(String s1, String s2) {
        return isEquals(s1, s2, false);
    }

    /**
     * 判断两个字符串是否相同。<br>
     *
     * @param s1         比较字符串1
     * @param s2         比较字符串2
     * @param ignoreCase 是否区分大小写(true:不区分；false：区分)
     * @return 比较结果（true：相同；false：不同）
     */
    public static boolean isEquals(String s1, String s2, boolean ignoreCase) {
        if (null == s1 && null == s2) {
            // 均为空
            return true;
        }

        if (null == s1 || null == s2) {
            // 其中一个为空
            return false;
        }

        return ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
    }

    /**
     * 检查字符串是否为数字
     *
     * @param str 检查字符串
     * @return true：数字字符；false：非数字字符
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            // 空字符串
            return false;
        }

        for (int ii = 0; ii < str.length(); ii++) {
            if (!Character.isDigit(str.charAt(ii))) {
                // 非数字字符
                return false;
            }
        }

        return true;
    }

    /**
     * 检查是否为整数数字字符串。<br>
     *
     * @param str 检查字符串
     * @return true:整型数字；false：非整型类型数字
     */
    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            // 空字符串
            return false;
        }

        // 整型数字模式匹配
        return PATTERN_INT.matcher(str).matches();
    }

    /**
     * 解析数字类型字符串为整数。<br>
     *
     * @param str 字符串
     * @return 解析后整数
     */
    public static int parseInteger(String str) {
        if (!isInteger(str)) {
            // 非整型数字字符串
            return 0;
        }

        return Integer.parseInt(str);
    }

    /**
     * 检查字符串是否为有效Java标识。<br>
     *
     * @param str 检查字符串
     * @return true：有效标识；false：无效标识
     */
    public static boolean isJavaIdentifier(String str) {
        if (isEmpty(str)) {
            // 空字符串
            return false;
        }

        if (!Character.isJavaIdentifierStart(str.charAt(0))) {
            // 首字符为无效Java标识字符
            return false;
        }

        for (int ii = 1; ii < str.length(); ii++) {
            // 逐个字符检查
            if (!Character.isJavaIdentifierPart(str.charAt(ii))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查逗号分割字符串中，是否包含某字符串。<br>
     *
     * @param contents 逗号分割字符串
     * @param str      检查字符串
     * @return true：包含；false：不包含
     */
    public static boolean isContains(String contents, String str) {
        if (isEmpty(contents)) {
            // 逗号分割字符串为空
            return false;
        }

        return isContains(PATTERN_COMMA_SPLIT.split(contents), str);
    }

    /**
     * 检查字符串数组中是否包含某个字符串。<br>
     *
     * @param contents 字符串数组内容
     * @param str      检查字符串
     * @return true：包含；false：不包含
     */
    public static boolean isContains(String[] contents, String str) {
        if (isEmpty(str)) {
            // 检查字符串为空
            return false;
        }

        if (null == contents || 0 == contents.length) {
            // 字符串数组内容为空
            return false;
        }

        for (String data : contents) {
            if (str.equals(data)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 将字符串数组链接为字符串。<br>
     *
     * @param array 字符串数组
     * @return 生成的字符串
     */
    public static String join(String... array) {
        return join(array, EMPTY_STRING);
    }

    /**
     * 字符串数组按照分隔符方式生成字符串。<br>
     *
     * @param array 字符串数组
     * @param split 分割符
     * @return 生成的字符串
     */
    public static String join(String[] array, char split) {
        return join(array, new String(new char[]{split}));
    }

    /**
     * 字符串集合按照分隔符方式生成字符串。<br>
     *
     * @param coll 字符串集合
     * @return 生成的字符串
     */
    public static String join(Collection<String> coll) {
        return join(coll, EMPTY_STRING);
    }

    /**
     * 字符串集合按照分隔符方式生成字符串。<br>
     *
     * @param coll  字符串集合
     * @param split 分割字符串
     * @return 生成的字符串
     */
    public static String join(Collection<String> coll, String split) {
        if (null == coll || coll.isEmpty()) {
            // 集合为空
            return EMPTY_STRING;
        }

        return join(coll.toArray(EMPTY_STRING_ARRAY), split);
    }

    /**
     * 字符串数组按照分隔符方式生成字符串。<br>
     *
     * @param array 字符串数组
     * @param split 分割字符串
     * @return 生成的字符串
     */
    public static String join(String[] array, String split) {
        if (null == array || 0 == array.length) {
            // 字符串数组为空
            return EMPTY_STRING;
        }

        // 字符串生成器
        StringBuilder sb = new StringBuilder();

        for (int ii = 0; ii < array.length; ii++) {
            if (ii > 0) {
                // 中间过程
                sb.append(split);
            }

            sb.append(array[ii]);
        }

        return sb.toString();
    }

    /**
     * 将字符串按照逗号分割成字符串数组。<br>
     *
     * @param str 字符串
     * @return 分割后字符串数组
     */
    public static String[] splitCommaStr(String str) {
        return split(str, PATTERN_COMMA_SPLIT_TEXT);
    }

    /**
     * 将字符串按照点号分割成字符串数组。<br>
     *
     * @param str 字符串
     * @return 分割后字符串数组
     */
    public static String[] splitDotStr(String str) {
        return split(str, PATTERN_DOT_SPLIT_TEXT);
    }

    /**
     * 将字符串按照指定字符（正则表达式）分割成字符串数组。<br>
     *
     * @param str   字符串
     * @param regex 分割符正则表达式
     * @return 分割后字符串数组
     */
    public static String[] split(String str, String regex) {
        if (isEmpty(str)) {
            // 字符串为空
            return EMPTY_STRING_ARRAY;
        }

        if (isEmpty(regex)) {
            // 分割符为空
            return new String[]{str};
        }

        return str.split(regex);
    }

    /**
     * 解析字符串中的Key-Value对。<br>
     *
     * @param str   解析字符串
     * @param split 分割符号
     * @return Key-Value对集合
     */
    public static Map<String, String> parseKeyValuePair(String str, String split) {
        // 生成Key-Value对字符串数组
        String[] keyValues = split(str, split);
        // 创建Key-Value集合
        Map<String, String> mapResult = new HashMap<String, String>();

        for (int i = 0; i < keyValues.length; i++) {
            // 生成匹配对象
            Matcher matcher = PATTERN_KVP.matcher(keyValues[i]);

            if (!matcher.matches()) {
                // 匹配失败
                continue;
            }

            // 添加匹配结果
            mapResult.put(matcher.group(1), matcher.group(2));
        }

        return mapResult;
    }

    /**
     * 生成单引号包围，逗号分隔的字符串。<br>
     *
     * @param datas 拼接字符串参数
     * @return 逗号分隔字符串
     */
    public static String makeSingleQuotation(String... datas) {
        if (null == datas || 0 == datas.length) {
            // 没有数据参数
            return EMPTY_STRING;
        }

        // 字符串生成器
        StringBuilder sb = new StringBuilder();

        for (String data : datas) {
            if (isEmpty(data)) {
                continue;
            }

            // 逐个格式化
            if (sb.length() > 0) {
                // 中间元素
                sb.append(COMMA_STRING);
            }

            // 添加当前字符串
            sb.append(data);
        }

        return sb.toString();
    }

    /**
     * 隐藏手机中间4为信息。<br>
     *
     * @param phone 手机号码
     * @return 隐藏号码
     */
    public static String hidePhone(String phone) {
        if (isEmpty(phone)) {
            return phone;
        }

        // 正则替换隐藏字符
        // return phone.replaceAll(PATTERN_PHONE_TEXT, PATTERN_PHONE_REPLACE);
        return phone;
    }

    /**
     * 隐藏手机中间4为信息。<br>
     *
     * @param phone 手机号码
     * @return 隐藏号码
     */
    public static String hidePhoneMiddle(String phone) {
        if (isEmpty(phone)) {
            return phone;
        }

        // 正则替换隐藏字符
        return phone.replaceAll(PATTERN_PHONE_TEXT, PATTERN_PHONE_REPLACE);
    }

    /**
     * 校验是否为手机号
     *
     * @param param
     * @return
     */
    public static boolean isPhone(String param) {
        if (DataUtils.isNullOrEmpty(param)) {
            return false;
        }
        return Pattern.matches(PATTERN_IS_PHONE_TEXT, param);
    }

    /**
     * 校验是否为邮箱
     *
     * @param param
     * @return
     */
    public static boolean isEmail(String param) {
        if (DataUtils.isNullOrEmpty(param)) {
            return false;
        }
        return Pattern.matches(PATTERN_IS_EMAIL_TEXT, param);
    }

    /**
     * 生成指定位数的随机数字符串。<br>
     *
     * @return 生成随机数字符串
     */
    public static String makeBitRandom() {
        return makeBitRandom(RANDOM_BIT_NUM);
    }

    /**
     * 生成指定位数的随机数字符串。<br>
     *
     * @param bit 随机数位数
     * @return 生成随机数字符串
     */
    public static String makeBitRandom(int bit) {
        if (bit <= 0) {
            // 位数不正确
            return EMPTY_STRING;
        }

        // 生成指定位数随机数字
        int code = (int) (Math.pow(10, bit) * Math.random());
        // 指定位数随机数格式
        String fromat = String.format(RANDOM_CODE_FORMAT, bit);
        // 生成指定位数随机数字符串
        return String.format(fromat, code);
    }
}
