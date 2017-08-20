/**
 * 查询字段响应消息体。<br>
 */
package com.hrhg.framework.bean;

import com.hrhg.framework.pojo.Simple;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询字段响应消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchBodyResp extends SBodyResp {
    /**
     * 查询标题头(key:字段名称；value：标题名称)
     */
    private Map<String, Object> title = new LinkedHashMap<>();
    /**
     * 数据内容
     */
    private List<Simple> datas = new ArrayList<>();
}
