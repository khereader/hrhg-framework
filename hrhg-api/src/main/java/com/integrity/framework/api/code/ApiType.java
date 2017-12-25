/**
 * 提供服务类型。<br>
 */
package com.integrity.framework.api.code;


import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 提供服务类型。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface ApiType {
    /**
     * 提供服务类型-业务应用服务
     */
    String APP_APP = "业务应用服务";
    /**
     * 提供服务类型-后台MIS
     */
    String APP_MIS = "后台MIS";
    /**
     * 提供服务类型-内部批处理应用服务
     */
    String APP_BATCH = "内部批处理应用服务";
    /**
     * 提供服务类型-运营活动页应用服务
     */
    String APP_OP_ACTIVE = "运营活动页应用服务";
    /**
     * 提供服务类型-开放平台接口服务
     */
    String APP_OPEN = "开放平台接口服务";
    /**
     * 提供服务类型-供应商回调接口服务
     */
    String APP_CALLBACK = "供应商回调接口服务";

    /**
     * 类型。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Type implements CodeName {
        /**
         * 业务应用服务
         */
        APP(1, APP_APP),
        /**
         * 后台MIS
         */
        MIS(2, APP_MIS),
        /**
         * 内部批处理应用服务
         */
        BATCH(3, APP_BATCH),
        /**
         * 运营活动页服务
         */
        OP_ACTIVE(21, APP_OP_ACTIVE),
        /**
         * 开放平台接口服务
         */
        OPEN(91, APP_OPEN),
        /**
         * 供应商回调接口服务
         */
        CALLBACK(92, APP_CALLBACK);

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

        /**
         * 生成所有类型集合。<br>
         *
         * @return 所有类型集合
         */
        public static Map<String, Integer> allTypeMap() {
            return allTypeMap(null);
        }

        /**
         * 生成所有类型集合。<br>
         *
         * @param exclude 排除类型
         * @return 所有类型集合
         */
        public static Map<String, Integer> allTypeMap(Type exclude) {
            // 生成所有类型集合
            Map<String, Integer> allTypes = new LinkedHashMap<>();

            for (Type type : Type.values()) {
                if (!DataUtils.isNullOrEmpty(exclude) && exclude == type) {
                    // 排除数据
                    continue;
                }

                // 枚举所有类型
                allTypes.put(type.getCodeName(), type.getCode());
            }

            return allTypes;
        }
    }
}