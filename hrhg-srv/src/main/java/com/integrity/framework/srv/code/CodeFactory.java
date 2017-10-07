/**
 * 好人好股编码本地缓存。<br>
 */
package com.integrity.framework.srv.code;


import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.cache.RWCache;
import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 好人好股编码本地缓存。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class CodeFactory extends RWCache<String, CodePath> {
    /**
     * 构造函数。<br>
     *
     * @param category 类别名称
     */
    protected CodeFactory(String category) {
        super(category);
    }

    /**
     * 根据业务编码对象集合，生成业务编码map。<br>
     *
     * @param datas 业务编码对象集
     * @return 业务编码map
     */
    protected Map<String, CodePath> makeCodePathMap(CodePath... datas) {
        // 创建业务编码map
        Map<String, CodePath> codePathMap = new HashMap<>();

        if (DataUtils.isNullOrEmpty(datas)) {
            // 业务编码数组为空
            return codePathMap;
        }

        for (CodePath codePath : datas) {
            // 逐个添加到容器中
            codePathMap.put(codePath.getFullCode(), codePath);
        }

        return codePathMap;
    }

    /**
     * 根据URI信息获取对应的编码。<br>
     *
     * @param uri URI信息
     * @return 编码
     */
    @SuppressWarnings("unchecked")
    public String findCodeByUri(String uri) {
        if (StringUtils.isEmpty(uri)) {
            // URI信息为空
            return null;
        }

        // 获取编码路径表
        Map<String, CodePath> codePaths = (Map<String, CodePath>) asMap();

        if (DataUtils.isNullOrEmpty(codePaths)) {
            // 编码路径为空
            return null;
        }

        for (Map.Entry<String, CodePath> codePath : codePaths.entrySet()) {
            // 逐个遍历编码／逻辑对象
            if (StringUtils.isNotEmptyEquals(uri, codePath.getValue().getFullPath())) {
                // URI和编码路径相同
                return codePath.getKey();
            }
        }

        return null;
    }
}
