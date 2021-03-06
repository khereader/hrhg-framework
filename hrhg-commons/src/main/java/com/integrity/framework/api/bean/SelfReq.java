/**
 * 自定义请求对象。<br>
 */
package com.integrity.framework.api.bean;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 自定义请求对象。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SelfReq implements Serializable {
    /**
     * 请求数据
     */
    private String request;
}
