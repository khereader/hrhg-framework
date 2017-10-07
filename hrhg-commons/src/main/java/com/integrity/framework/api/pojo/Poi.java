/**
 * POI信息。
 */
package com.integrity.framework.api.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * POI信息。
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Poi extends GeoSpatial {
    /**
     * POI名称
     */
    private String name;
    /**
     * POI位置地址
     */
    private String addr;
}
