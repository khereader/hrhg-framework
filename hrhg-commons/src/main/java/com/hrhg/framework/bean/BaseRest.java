/**
 * Rest通讯参数基类。<br>
 */
package com.hrhg.framework.bean;

import lombok.Getter;

import javax.validation.constraints.NotNull;
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
public abstract class BaseRest<H extends Head, B extends Body> implements Serializable {
    /**
     * 消息头
     */
    @NotNull
    @Getter
    protected H head;
    /**
     * 消息体
     */
    @NotNull
    @Getter
    protected B body;

    /**
     * Rest消息构造函数。<br>
     */
    public BaseRest() {
        super();
        // 创建消息头(消息头为必选信息，因此，对象创建时自动创建)
        createHead();
        // 创建消息体
        createBody();
    }

    /**
     * 创建消息头对象。<br>
     */
    protected abstract void createHead();

    /**
     * 创建消息体对象。<br>
     */
    protected abstract void createBody();
}
