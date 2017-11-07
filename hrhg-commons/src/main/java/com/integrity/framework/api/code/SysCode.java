package com.integrity.framework.api.code;

import lombok.Getter;

/**
 * 系统编码编码接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface SysCode {
    /**
     * 基础编码
     */
    String BASE_CODE_SYSTEM = "SYSTEM";
    /**
     * 基础路径
     */
    String BASE_PATH = "/services/integrity";
    /**
     * 默认服务路径
     */
    String BASE_PATH_SYSTEM = "system";

    /**
     * 默认服务路径-－测试。
     */
    String PATH_TEST = "test";
    /**
     * 默认服务路径-－简单接口。
     */
    String PATH_SIMPLE = "simple";
    /**
     * 默认服务路径-－自定义接口。
     */
    String PATH_SELF = "self";
    /**
     * 默认服务路径-－查询接口。
     */
    String PATH_SEARCH = "search";

    /**
     * 默认编码。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Path implements CodePath {
        /**
         * 默认服务路径-－系统测试。
         */
        TEST("系统测试", SysCode.PATH_TEST),
        /**
         * 默认服务路径-－简单接口。
         */
        SIMPLE("简单接口", SysCode.PATH_SIMPLE),
        /**
         * 默认服务路径-－自定义接口。
         */
        SELF("自定义接口", SysCode.PATH_SELF),
        /**
         * 默认服务路径-－自定义接口。
         */
        SEARCH("查询接口", SysCode.PATH_SEARCH);

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
            this.fullCode = BASE_PATH_SYSTEM + SEPARATOR_UNDERLINE + code;
            this.bizzName = bizzName;
            this.path = path;
            this.cashe = cashe;
            this.fullPath = BASE_PATH + SEPARATOR_SLASH + BASE_PATH_SYSTEM + SEPARATOR_SLASH + path;
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
            return HEAD_PATH + SEPARATOR_COLON + BASE_CODE_SYSTEM + SEPARATOR_COLON + name();
        }

        /**
         * 获取根编码。<br>
         *
         * @return 根编码
         */
        @Override
        public String getRootCode() {
            return BASE_CODE_SYSTEM;
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
        OK("%s请求数据响应成功！"),
        /**
         * 处理开始
         */
        I_REQ_BEGIN("%s业务处理开始！"),
        /**
         * 处理结束
         */
        I_REQ_END("%s业务处理结束！"),
        /**
         * 跟踪过滤器请求调用
         */
        I_FILTER_TRACE_REQ("跟踪过滤器请求调用！"),
        /**
         * 跟踪过滤器请求调用
         */
        I_FILTER_TRACE_RESP("跟踪过滤器响应调用！"),
        /**
         * 跟踪拦截器请求调用
         */
        I_FILTER_INTERCEPTOR_REQ("请求参数读取拦截器调用！"),
        /**
         * 跟踪拦截器请求调用
         */
        I_FILTER_INTERCEPTOR_RESP("响应参数写入拦截器调用！"),
        /**
         * 地址不存在
         */
        E_NOT_FOUND("请求地址不存在！"),
        /**
         * 参数格式错误
         */
        E_PARAM_FORMAT("请求参数格式错误！"),
        /**
         * 参数解析异常
         */
        E_PARAM_PARSE("请求参数解析异常！"),
        /**
         * 无法识别属性
         */
        E_PARAM_UNKNOWN("请求参数存在无法识别属性！"),
        /**
         * 远程服务异常
         */
        E_RPC_RESOURCE("远程业务服务资源异常！"),
        /**
         * 属性字段参数不匹配
         */
        E_NO_PROPERTY("属性字段参数不匹配！"),
        /**
         * 无效的应用类型
         */
        E_NOT_EXIST_APPTYPE("无效的应用类型编码参数！"),
        /**
         * 空业务编码
         */
        E_EMPTY_BIZZ("没有业务编码参数！"),
        /**
         * 无效业务
         */
        E_NOT_EXIST_BIZZ("无效的业务编码！"),
        /**
         * 没有鉴权服务
         */
        E_NO_AUTH_SERVICE("没有鉴权服务！"),
        /**
         * 用户鉴权失败
         */
        E_AUTH_USER("用户鉴权失败！"),
        /**
         * 系统异常
         */
        E_SYS_EXCEPTION("系统异常!");

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
         * 私有构造函数。<br>
         *
         * @param message 系统消息(消息格式)
         */
        Message(String message) {
            this.code = String.format((0 == ordinal() ? FORMAT_CODE : FORMAT_CODE_MESSAGE), ordinal());
            this.fullCode = BASE_CODE_SYSTEM + SEPARATOR_UNDERLINE + code;
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
            return HEAD_MESSAGE + SEPARATOR_COLON + BASE_CODE_SYSTEM + SEPARATOR_COLON + name();
        }

        /**
         * 获取根编码。<br>
         *
         * @return 根编码
         */
        @Override
        public String getRootCode() {
            return BASE_CODE_SYSTEM;
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
