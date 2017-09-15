/**
 * 终端OS信息。<br>
 */
package com.hrhg.framework.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 终端OS信息。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@JsonIgnoreType
public class Device implements Serializable {
    /**
     * 设备类型（note5、iPhone6 ...）
     */
    private String type;
    /**
     * 设备型号（iphone、sumsang ...）
     */
    private String model;
    /**
     * 手机生产商
     */
    @JsonProperty("mnft")
    @XmlElement(name = "mnft")
    private String manufacturer;
    /**
     * 操作系统类型（iOS；Android）
     */
    private String osType;
    /**
     * 操作系统版本(iOS9.2;Android5.0 ...)
     */
    private String osVer;
    /**
     * 图像分辨率单位，像素密度（PC：72；iphone4:330）
     */
    private Integer ppi;
    /**
     * 网络类型（3G、4G）
     */
    private String net;
    /**
     * 手机IMEI
     */
    private String imei;
}
