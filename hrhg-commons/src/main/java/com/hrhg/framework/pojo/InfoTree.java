/**
 * 树型结构信息接口。<br>
 */
package com.hrhg.framework.pojo;

import java.util.List;

/**
 * 树型结构信息接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface InfoTree<T extends Object> {
    /**
     * 获取自身ID。<br>
     *
     * @return 自身ID
     */
    String myId();

    /**
     * 获取父节点ID。<br>
     *
     * @return 父节点ID
     */
    String parentId();

    /**
     * 获取子节点列表。<br>
     *
     * @return 子节点
     */
    List<T> getSub();

    /**
     * 设置子节点列表。<br>
     *
     * @param sub 子节点列表
     */
    void setSub(List<T> sub);
}
