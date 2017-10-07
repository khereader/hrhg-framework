/**
 * 搜索分页响应消息体。<br>
 */
package com.integrity.framework.api.bean;

import com.integrity.framework.api.pojo.PageInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlElement;

/**
 * 搜索分页响应消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SBodyResp extends BodyResp {
    /**
     * 分页信息
     */
    @JsonProperty("p")
    @XmlElement(name = "p")
    private PageInfo pageInfo = new PageInfo();
}
