/**
 * 分页信息。<br>
 */
package com.integrity.framework.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页信息。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class PageInfo implements Serializable {
    /**
     * 当前页(1为首页)
     */
    private Integer cur;
    /**
     * 动作(请求时使用：0、所有数据；1、跳到当前页；2、前一页；3、后一页；4、首页；5、末页)
     */
    private Integer act;
    /**
     * 每页条数(请求时使用)
     */
    private Integer per;
    /**
     * 总记录数（响应时使用）
     */
    private Integer cnt;

    /**
     * 初始化分页数据。<br>
     */
    public void init() {
        init(0, 10);
    }

    /**
     * 初始化分页数据。<br>
     *
     * @param act 请求动作
     * @param per 每页记录书
     */
    public void init(Integer act, Integer per) {
        this.cur = 1;
        this.act = act;
        this.per = per;
        this.cnt = 0;
    }
}
