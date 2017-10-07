/**
 * 系统查询类型。<br>
 */
package com.integrity.framework.api.code;

import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.StringUtils;

/**
 * 系统查询类型。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface SearchType {
    /**
     * 系统查询类型-列表数据输出
     */
    String SEARCH_LIST_DATA = "列表数据输出";
    /**
     * 系统查询类型-用户查询
     */
    String SEARCH_INIT_SYSTEM = "系统查询初始化";
    /**
     * 系统查询类型-系统查询
     */
    String SEARCH_SYSTEM = "系统查询";
    /**
     * 系统查询类型-用户查询
     */
    String SEARCH_INIT_USER = "用户查询初始化";
    /**
     * 系统查询类型-用户查询
     */
    String SEARCH_USER = "用户查询";
    /**
     * 系统查询类型-用户查询
     */
    String SEARCH_INIT_SUB = "用户子查询初始化";
    /**
     * 系统查询类型-用户子查询
     */
    String SEARCH_SUB = "用户子查询";

    /**
     * 类型。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Type implements CodeName {
        /**
         * 列表数据输出
         */
        LIST_DATA(1, SEARCH_LIST_DATA),
        /**
         * 系统查询初始化
         */
        INIT_SYSTEM(10, SEARCH_INIT_SYSTEM),
        /**
         * 系统查询
         */
        SYSTEM(11, SEARCH_SYSTEM),
        /**
         * 用户查询初始化
         */
        INIT_USER(20, SEARCH_INIT_USER),
        /**
         * 用户查询
         */
        USER(21, SEARCH_USER),
        /**
         * 用户子查询初始化
         */
        INIT_SUB(30, SEARCH_INIT_SUB),
        /**
         * 用户子查询
         */
        SUB(31, SEARCH_SUB);

        /**
         * 搜索类型分组
         */
        public enum Family {
            /**
             * 初始化
             */
            INIT,
            /**
             * 业务处理
             */
            BIZZ;

            /**
             * 根据业务类型编码信息，获取分组类型。<br>
             *
             * @param code 业务类型编码信息
             * @return 分组类型
             */
            public static Family familyOf(final Integer code) {
                if (DataUtils.isNullOrEmpty(code)) {
                    // 编码为空时
                    return null;
                }

                if (0 == code % 10) {
                    // 10的整数倍时
                    return INIT;
                }

                return BIZZ;
            }
        }

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
    }
}