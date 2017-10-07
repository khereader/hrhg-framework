/**
 * 业务操作状态。<br>
 */
package com.integrity.framework.api.code;

import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.DateUtils;
import com.integrity.framework.utils.StringUtils;

/**
 * 业务操作状态。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface OpStatus {
    /**
     * 业务操作状态-启用
     */
    String STATUS_ENABLE = "启用";
    /**
     * 业务操作状态-禁用
     */
    String STATUS_DISABLE = "禁用";
    /**
     * 未开始
     */
    String STATUS_UNSTART = "未开始";
    /**
     * 进行中
     */
    String STATUS_OPENING = "进行中";
    /**
     * 已结束
     */
    String STATUS_CLOSED = "已结束";

    /**
     * 类型。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Type implements CodeName {
        /**
         * 启用
         */
        ENABLE(1, STATUS_ENABLE),
        /**
         * 禁用
         */
        DISABLE(0, STATUS_DISABLE);

        /**
         * 类型
         */
        private final int code;
        /**
         * 类型名称
         */
        private final String codeName;

        /**
         * 私有构造函数。<br>
         *
         * @param code     类型值
         * @param codeName 类型名称
         */
        Type(int code, String codeName) {
            this.code = code;
            this.codeName = codeName;
        }


        /**
         * 获取编码。<br>
         *
         * @return 编码
         */
        @Override
        public int getCode() {
            return this.code;
        }

        /**
         * 获取编码名称。<br>
         *
         * @return 全编码
         */
        @Override
        public String getCodeName() {
            return this.codeName;
        }

        /**
         * 是否为当前类型。<br>
         *
         * @param code 枚举类型编码
         * @return true：是当前类型；false：不是当前类型
         */
        @Override
        public boolean isThisType(Integer code) {
            if (null == code) {
                return false;
            }

            return this.code == code;
        }

        /**
         * 根据类型值获取枚举类型。<br>
         *
         * @param code 类类型值
         * @return 枚举类型
         */
        public static Type fromCode(final Integer code) {
            if (null == code) {
                return null;
            }

            for (Type item : Type.values()) {
                if (item.code == code) {
                    return item;
                }
            }

            return null;
        }

        /**
         * 根据编码值，获取编码文案。<br>
         *
         * @param code 编码值
         * @return 编码文案
         */
        public static String codeText(final Boolean code) {
            if (null == code) {
                return StringUtils.EMPTY_STRING;
            }

            return codeText(code ? 1 : 0);
        }

        /**
         * 根据编码值，获取编码文案。<br>
         *
         * @param code 编码值
         * @return 编码文案
         */
        public static String codeText(final Integer code) {
            return codeText(fromCode(code));
        }

        /**
         * 根据编码类型，获取编码文案。<br>
         *
         * @param code 编码类型
         * @return 编码文案
         */
        public static String codeText(final Type code) {
            if (null == code) {
                // 编码类型为空
                return StringUtils.EMPTY_STRING;
            }

            return code.getCodeName();
        }

        /**
         * 获取状态文案。<br>
         *
         * @param begin 开始时间
         * @param end   结束时间
         * @return 返回结果
         */
        public String getStatusText(Long begin, Long end) {
            if (this == DISABLE) {
                // 禁用状态
                return OpStatus.STATUS_DISABLE;
            }

            if (DataUtils.isNullOrEmpty(begin) || DataUtils.isNullOrEmpty(end)) {
                return OpStatus.STATUS_OPENING;
            }

            // 启用状态
            // 当前时间
            Long now = DateUtils.getTimeStamp();

            if (now < begin) {
                // 未开始
                return OpStatus.STATUS_UNSTART;
            } else if (now <= end) {
                // 进行中
                return OpStatus.STATUS_OPENING;
            } else {
                // 已结束
                return OpStatus.STATUS_CLOSED;
            }
        }
    }
}