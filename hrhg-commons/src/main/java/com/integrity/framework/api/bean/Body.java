/**
 * 消息体基类。<br>
 */
package com.integrity.framework.api.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息体基类。Body对象最少有一个字段定义，否则，json序列化报错。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public abstract class Body implements Serializable {
}
