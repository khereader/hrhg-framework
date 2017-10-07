/**
 * 编码父枚举。<br>
 */
package com.integrity.framework.api.code;

import lombok.Getter;

/**
 * 编码父枚举接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface SysRoot {
    /**
     * 基础路径
     */
    String PATH_BASE = "/services/cm";
    /**
     * 默认服务路径
     */
    String PATH_SYSTEM = "system";
    /**
     * 默认服务路径
     */
    String PATH_DEFAULT = "default";
    /**
     * 在线文档接口路径
     */
    String PATH_DOCS = "docs";

    /**
     * 编码父枚举。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Root implements CodePath {
        /**
         * 系统编码（无业务请求）
         */
        SYSTEM("01000000", "系统", SysRoot.PATH_SYSTEM),
        /**
         * 默认业务编码
         */
        DEFAULT("01000001", "默认", SysRoot.PATH_DEFAULT),
        /**
         * 在线文档业务编码
         */
        DOCS("01000002", "在线文档", SysRoot.PATH_DOCS);

        /**
         * 业务编码
         */
        @Getter
        private final String code;
        /**
         * 业务全编码
         */
        @Getter
        private final String fullCode;
        /**
         * 业务名称
         */
        @Getter
        private final String bizzName;
        /**
         * URI路径
         */
        @Getter
        private final String path;
        /**
         * URI全路径
         */
        @Getter
        private final String fullPath;
        /**
         * 缓存状态
         */
        @Getter
        private final boolean cashe = false;
        /**
         * 默认根路径
         */
        @Getter
        private final CodePath root = null;

        /**
         * 业务编码分组（大部分无分组枚举，有分组信息才进行枚举输出）
         */
        public enum Family {
            /**
             * 默认组
             */
            DEFAULT("01001");
            /**
             * 分组编码
             */
            private String code;

            /**
             * 私有构造函数。<br>
             *
             * @param code 分组编码
             */
            Family(final String code) {
                this.code = code;
            }

            /**
             * 根据业务编码信息，获取分组枚举信息。<br>
             *
             * @param code 业务编码
             * @return 业务编码分组信息;无分组信息，返回空
             */
            public static SysRoot.Root.Family familyOf(final String code) {
                for (SysRoot.Root.Family cd : SysRoot.Root.Family.values()) {
                    if (null == code || 0 == code.length()) {
                        return null;
                    }

                    if (code.startsWith(cd.code)) {
                        return cd;
                    }
                }

                return null;
            }
        }

        /**
         * 私有构造函数。<br>
         *
         * @param code     业务编码
         * @param bizzName 业务名称
         * @param path     URI路径
         */
        Root(String code, String bizzName, String path) {
            this.code = code;
            this.fullCode = (null == root ? CODE_BASE : root.getFullCode()) + code;
            this.bizzName = bizzName;
            this.path = path;
            this.fullPath = (null == root ? PATH_BASE : root.getFullPath()) + SEPARATOR_SLASH + path;
        }

        /**
         * Returns the name of this enum code, as contained in the
         * declaration.  This method may be overridden, though it typically
         * isn't necessary or desirable.  An enum type should override this
         * method when a more "programmer-friendly" string form exists.
         *
         * @return the name of this enum code
         */
        @Override
        public String toString() {
            return name();
        }

        /**
         * 根据编码获取枚举类型。<br>
         *
         * @param code 编码
         * @return 枚举类型
         */
        public static Root fromCode(final String code) {
            for (Root cd : Root.values()) {
                if (cd.code == code) {
                    return cd;
                }
            }

            return null;
        }
    }
}