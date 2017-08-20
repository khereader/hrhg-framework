/**
 * 请求消息头。<br>
 */
package com.hrhg.framework.bean;

import com.hrhg.framework.pojo.Device;
import com.hrhg.framework.pojo.HttpRequestInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Device device;
    /**
     * 客户端请求URI
     */
    @JsonIgnore
    private HttpRequestInfo httpRequestInfo = new HttpRequestInfo();

    /**
     * 创建设备对象(由于设备属性为可选属性，用户使用时，根据具体是否需要由具体信息设置处创建)。<br>
     */
    public void createDevice() {
        this.device = new Device();
    }
}
