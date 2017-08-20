/**
 * 编码路径类型接口。<br>
 */
package com.hrhg.framework.code;

/**
 * 编码路径类型接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface CodePath extends CodeType {
    /**
     * 路径前缀
     */
    String HEAD_PATH = "PATH";
    /**
     * 分隔符-斜线
     */
    String SEPARATOR_SLASH = "/";
    /**
     * 编码格式化_路径编码
     */
    String FORMAT_CODE_PATH = "%03d";

    /**
     * 获取路径。<br>
     *
     * @return 路径
     */
    String getPath();

    /**
     * 获取全路径。<br>
     *
     * @return 全路径
     */
    String getFullPath();

    /**
     * 获取业务名称。<br>
     *
     * @return 业务名称
     */
    String getBizzName();

    /**
     * 获取是否缓存。<br>
     *
     * @return 是否缓存
     */
    boolean isCashe();
}
