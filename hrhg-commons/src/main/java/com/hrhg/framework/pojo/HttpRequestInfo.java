/**
 * HTTP客户端请求信息。
 */
package com.hrhg.framework.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP客户端请求信息。
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class HttpRequestInfo implements Serializable {
    /**
     * 请求URI
     */
    private String uri;
    /**
     * 请求IP
     */
    private String ip;
}
