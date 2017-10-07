/**
 * 响应参数。<br>
 */
package com.integrity.framework.api.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应参数。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Response<H extends HeadResp, B extends BodyResp> extends BaseRest<H, B> {
}
