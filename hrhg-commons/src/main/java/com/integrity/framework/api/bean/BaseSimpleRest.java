/**
 * Rest通讯参数基类。<br>
 */
package com.integrity.framework.api.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Rest通讯参数基类。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseSimpleRest implements Serializable {
}
