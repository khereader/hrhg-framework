/**
 * 地理空间位置。<br>
 */
package com.integrity.framework.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 地理空间位置。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class GeoSpatial implements Serializable {
    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;
}
