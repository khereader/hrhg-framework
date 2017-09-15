/**
 * 自定义响应对象。<br>
 */
package com.hrhg.framework.bean;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 自定义响应对象。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SelfResp implements Serializable {
    /**
     * 响应结果
     */
    private String result;
}
