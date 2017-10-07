/**
 * 抽象业务逻辑服务。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.model.LimitOrder;
import com.integrity.framework.api.pojo.PageInfo;
import com.integrity.framework.utils.StringUtils;

/**
 * 抽象业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AbstractBLogic<P extends Object, R extends Object> extends AbstractBaseBLogic<P, R> {
    /**
     * 首页
     */
    public static final int FIRST_RPAGE = 1;
    /**
     * 每页最小记录数
     */
    public static final int MIN_COUNT_PERPAGE = 1;
    /**
     * 每页最大记录数
     */
    public static final int MAX_COUNT_PERPAGE = 100;
    /**
     * Excel导出的时候每页最大记录数
     */
    public static final int MAX_COUNT_EXPORT = 10000;

    /**
     * 获取排序字段信息。<br>
     *
     * @return 排序字段信息
     */
    protected abstract String orderField();

    /**
     * 获取分组字段信息。<br>
     *
     * @return 分组字段信息
     */
    protected abstract String groupField();

    /**
     * 根据记录数及请求分页信息，计算响应分页信息。<br>
     *
     * @param req   请求分页信息
     * @param resp  响应分页信息
     * @param count 总记录数
     * @return 查询分页信息
     */
    protected LimitOrder calPageInfo(PageInfo req, PageInfo resp, int count) {
        return calPageInfo(req, resp, count, true);
    }

    /**
     * 根据记录数及请求分页信息，计算响应分页信息。<br>
     *
     * @param req             请求分页信息
     * @param resp            响应分页信息
     * @param count           总记录数
     * @param checkPerPageMax 检查每页最大记录数
     * @return 查询分页信息
     */
    protected LimitOrder calPageInfo(PageInfo req, PageInfo resp, int count, boolean checkPerPageMax) {
        // 创建分页信息
        LimitOrder lo = new LimitOrder();

        // 设置排序字段信息
        String order = orderField();

        if (StringUtils.isNotEmpty(order)) {
            // 排序字段非空
            lo.setOrderField(order);
        }

        // 设置分组字段
        String group = groupField();

        if (StringUtils.isNotEmpty(group)) {
            // 排序字段非空
            lo.setGroupField(group);
        }

        if (0 == count) {
            // 无请求数据
            resp.init(req.getAct(), req.getPer());
            return lo;
        }

        // 设置每页记录数信息
        // 检查每页最大记录数的情况
        if (null == req.getPer()) {
            // 超出最大记录数
            resp.setPer(MAX_COUNT_PERPAGE);
        } else if (req.getPer() == MAX_COUNT_EXPORT) {
            resp.setPer(MAX_COUNT_EXPORT);
        } else if (checkPerPageMax && req.getPer() > MAX_COUNT_PERPAGE) {
            // 超出最大记录数(同时检查最大记录数)
            resp.setPer(MAX_COUNT_PERPAGE);
        } else if (req.getPer() < MIN_COUNT_PERPAGE) {
            // 小于最小记录数
            resp.setPer(MIN_COUNT_PERPAGE);
        } else {
            // 正常每页记录数信息
            resp.setPer(req.getPer());
        }

        // 设置动作(默认为当前页)
        resp.setAct(null == req.getAct() ? 1 : req.getAct());
        // 设置总记录数
        resp.setCnt(count);
        // 末页
        int end = (int) Math.ceil(1.0 * count / resp.getPer());
        int cur = null == req.getCur() ? FIRST_RPAGE : req.getCur();

        switch (resp.getAct()) {
            case 1: {
                // 跳到当前页
                resp.setCur(cur);
                break;
            }
            case 2: {
                // 前一页
                resp.setCur(cur - 1);
                break;
            }
            case 3: {
                // 后一页
                resp.setCur(cur + 1);
                break;
            }
            case 4: {
                // 首页
                resp.setCur(FIRST_RPAGE);
                break;
            }
            case 5: {
                // 末页
                resp.setCur(end);
                break;
            }
            case 0:
            default: {
                // 默认处理及所有数据
                // 设置为首页
                resp.setCur(FIRST_RPAGE);
                // 每页最大条数
                resp.setPer(MAX_COUNT_PERPAGE);
                break;
            }
        }

        if (resp.getCur() > end) {
            // 末页
            resp.setCur(end + 1);
        } else if (resp.getCur() < FIRST_RPAGE) {
            // 首页
            resp.setCur(FIRST_RPAGE);
        }

        // 查询偏移量
        lo.setOffset((resp.getCur() - 1) * resp.getPer());
        // 查询条数
        lo.setSize(resp.getPer());

        return lo;
    }
}
