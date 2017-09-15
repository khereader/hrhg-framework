/**
 * 编码类型接口。<br>
 */
package com.hrhg.framework.code;

/**
 * 编码类型接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface CodeType {
    /**
     * 编码基础
     */
    String CODE_BASE = "";
    /**
     * 分隔符-冒号
     */
    String SEPARATOR_COLON = ":";
    /**
     * 编码格式化_默认编码
     */
    String FORMAT_CODE = "%d";

    /**
     * 获取编码。<br>
     *
     * @return 编码
     */
    String getCode();

    /**
     * 获取全编码。<br>
     *
     * @return 全编码
     */
    String getFullCode();

    /**
     * 获取根编码。<br>
     *
     * @return 根编码
     */
    CodePath getRoot();
}
