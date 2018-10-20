/**
 * 邀请码工具通用类。<br>
 */
package com.integrity.framework.utils;

import java.util.Random;

/**
 * 邀请码工具通用类。<br>
 * 邀请码生成器，算法原理：<br>
 * 1) 获取id: 1127738 <br>
 * 2) 使用自定义进制转为：gpm6 <br>
 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br>
 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br>
 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，
 * 这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br>
 */
public final class InviteCode {
    /**
     * 自定义进制(排除0,1；保留L，O作为补充字符)
     */
    private static final char[] radixData =
            new char[]{'W', 'Q', 'E', 'Z', 'X', '9', 'Y', 'B', '4', 'V', 'S',
                    'C', '7', 'P', '5', 'I', 'K', '3', 'M', 'J', 'U', 'F',
                    'R', '2', 'G', '8', 'H', 'A', 'L', 'T', 'N', '6', 'D'};
    /**
     * 自定义进制中不包含O，作为随机字符
     */
    private static final char randomChar = 'O';
    /**
     * 数进制长度
     */
    private static final int radix = radixData.length;
    /**
     * 邀请码长度
     */
    private static final int codeLen = 6;
    /**
     * 定义缓存字符长度
     */
    private static final int LENGTH_BUFFER = 32;

    /**
     * 根据ID生成随机邀请码。<br>
     *
     * @param id 用户ID
     * @return 邀请码
     */
    public static String makeInviteCode(long id) {
        return makeInviteCode(id, codeLen);
    }

    /**
     * 根据ID生成随机邀请码。<br>
     *
     * @param id         用户ID
     * @param minCodeLen 生成邀请码长度
     * @return 邀请码
     */
    public static String makeInviteCode(long id, int minCodeLen) {
        // 定义字符串缓存
        char[] buf = new char[LENGTH_BUFFER];
        // 索引位置
        int charPos = LENGTH_BUFFER;

        while ((id / radix) > 0) {
            // 按照数值进制数进行自定义进制数据转换
            int bitNum = (int) (id % radix);
            // 记录当前位数数值
            buf[--charPos] = radixData[bitNum];
            // 获取习以为常的数值
            id /= radix;
        }

        // 获取最高位数值
        buf[--charPos] = radixData[(int) (id % radix)];
        // 转换成自定义进制数值
        String radixNum = new String(buf, charPos, (LENGTH_BUFFER - charPos));

        // 不够长度的自动随机补全
        if (radixNum.length() < minCodeLen) {
            // 不足邀请码位数
            StringBuilder sb = new StringBuilder();
            // 补足一位字母O
            sb.append(randomChar);
            // 将剩余位数补足随机数
            Random random = new Random();

            for (int ii = 1; ii < minCodeLen - radixNum.length(); ii++) {
                // 补足随机数
                sb.append(radixData[random.nextInt(radix)]);
            }

            // 生成邀请码
            radixNum += sb.toString();
        }

        return radixNum;
    }

    /**
     * 根据随机邀请码生成ID。<br>
     *
     * @param code 邀请码
     * @return ID
     */
    public static long makeInviteId(String code) {
        // 邀请码转为字符数组
        char codeChars[] = code.toCharArray();

        // 计算结果ID
        long resultId = 0L;

        for (int ii = 0; ii < codeChars.length; ii++) {
            // 自定义进制位数
            int radixPos = 0;

            for (int jj = 0; jj < radix; jj++) {
                if (codeChars[ii] == radixData[jj]) {
                    // 找到自定义进制数值中的位置
                    radixPos = jj;
                    break;
                }
            }

            if (codeChars[ii] == randomChar) {
                // 随机字符，结束
                break;
            }

            if (ii > 0) {
                // 计算自定义进制数
                resultId = resultId * radix + radixPos;
            } else {
                // 第一位进制数
                resultId = radixPos;
            }
        }

        return resultId;
    }
}
