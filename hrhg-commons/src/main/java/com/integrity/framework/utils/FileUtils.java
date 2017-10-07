/**
 * 文件操作管理工具。<br>
 */
package com.integrity.framework.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作管理工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class FileUtils {
    /**
     * 私有构造函数。<br>
     */
    private FileUtils() {
    }

    /**
     * 根据文件路径，获取安装包文件名。<br>
     *
     * @param path 文件路径
     * @return 安装包文件名
     */
    public static String getPkgFileName(String path) {
        if (StringUtils.isEmpty(path)) {
            // 路径为空
            return StringUtils.EMPTY_STRING;
        }

        // 获取文件路径对象
        Path filePath = Paths.get(path);
        // 根据文件对象获取文件名
        return filePath.getFileName().toString();
    }
}
