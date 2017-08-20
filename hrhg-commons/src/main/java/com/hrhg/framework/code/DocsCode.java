package com.hrhg.framework.code;

import lombok.Getter;

/**
 * 系统编码编码接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface DocsCode {
    /**
     * 在线文档-－swagger文档。
     */
    String PATH_SWAGGER = "swagger";

    /**
     * 默认编码。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Path implements CodePath {
        /**
         * 在线文档-－swagger文档。
         */
        SWAGGER("swagger文档", DocsCode.PATH_SWAGGER);

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
         * 业务名称
         */
        @Getter
        private final String bizzName;
        /**
         * 缓存状态
         */
        @Getter
        private final boolean cashe;
        /**
         * 默认根路径
         */
        @Getter
        private final SysRoot.Root root = SysRoot.Root.DOCS;

        /**
         * 私有构造函数。<br>
         *
         * @param bizzName 业务名称
         * @param path     URI路径
         */
        Path(String bizzName, String path) {
            this(bizzName, path, false);
        }

        /**
         * 私有构造函数。<br>
         *
         * @param bizzName 业务名称
         * @param path     URI路径
         * @param cashe    是否缓存标记
         */
        Path(String bizzName, String path, boolean cashe) {
            this.code = String.format(FORMAT_CODE_PATH, ordinal() + 1);
            this.fullCode = root.getFullCode() + code;
            this.bizzName = bizzName;
            this.path = path;
            this.cashe = cashe;
            this.fullPath = root.getFullPath() + SEPARATOR_SLASH + path;
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
            return HEAD_PATH + SEPARATOR_COLON + root.name() + SEPARATOR_COLON + name();
        }

        /**
         * 根据编码获取枚举类型。<br>
         *
         * @param code 编码
         * @return 枚举类型
         */
        public static Path fromCode(final String code) {
            for (Path cd : Path.values()) {
                if (cd.code == code) {
                    return cd;
                }
            }

            return null;
        }
    }

    /**
     * 默认编码。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Message implements CodeMessage {
        /**
         * 响应成功
         */
        OK("0", "%s请求数据响应成功！");

        /**
         * 业务编码
         */
        private final String code;
        /**
         * 业务全编码
         */
        private final String fullCode;
        /**
         * 系统消息(消息格式)
         */
        private final String message;
        /**
         * 业务编码
         */
        private final SysRoot.Root root = SysRoot.Root.DOCS;

        /**
         * 私有构造函数。<br>
         *
         * @param code    业务编码
         * @param message 系统消息(消息格式)
         */
        Message(String code, String message) {
            this.code = code;
            this.fullCode = root.getFullCode() + code;
            this.message = message;
        }

        /**
         * 获取编码。<br>
         *
         * @return 编码
         */
        @Override
        public String getCode() {
            return this.code;
        }

        /**
         * 获取全编码。<br>
         *
         * @return 全编码
         */
        @Override
        public String getFullCode() {
            // OK返回自身编码0；其他所有异常编码，均为带业务编码的异常编码
            return (this == OK) ? this.code : this.fullCode;
        }

        /**
         * 获取根编码。<br>
         *
         * @return 根编码
         */
        @Override
        public CodePath getRoot() {
            return this.root;
        }

        /**
         * 获取路径。<br>
         *
         * @return 路径
         */
        public String getMessage() {
            return this.message;
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
            return HEAD_MESSAGE + SEPARATOR_COLON + root.name() + SEPARATOR_COLON + name();
        }

        /**
         * 根据编码获取枚举类型。<br>
         *
         * @param code 编码
         * @return 枚举类型
         */
        public static Message fromCode(final String code) {
            for (Message cd : Message.values()) {
                if (cd.code == code) {
                    return cd;
                }
            }

            return null;
        }
    }
}
