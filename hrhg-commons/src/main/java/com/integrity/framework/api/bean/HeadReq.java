/**
 * 请求消息头。<br>
 */
package com.integrity.framework.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.integrity.framework.api.pojo.Device;
import com.integrity.framework.api.pojo.HttpRequestInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 请求消息头。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HeadReq extends Head {
    /**
     * 客户端类型
     */
    @NotNull
    private Integer appId = 1;
    /**
     * 客户端软件版本（例如：1.0.0）
     */
    @NotNull
    private String appVer = "1.0.0";
    /**
     * 终端设备信息
     */
    @JsonIgnore
    private Device device = new Device();
    ;
    /**
     * 客户端请求URI
     */
    @JsonIgnore
    private HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
}
