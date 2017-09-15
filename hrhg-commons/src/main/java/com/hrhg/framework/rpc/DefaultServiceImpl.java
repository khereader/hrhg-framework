/**
 * 默认Srv服务。<br>
 */
package com.hrhg.framework.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.hrhg.framework.bean.DefaultReq;
import com.hrhg.framework.bean.DefaultResp;
import com.hrhg.framework.bean.SearchReq;
import com.hrhg.framework.bean.SearchResp;
import com.hrhg.framework.bean.SelfReq;
import com.hrhg.framework.bean.SelfResp;
import com.hrhg.framework.exception.RespException;
import com.hrhg.framework.pojo.Simple;
import com.hrhg.framework.service.DefaultService;
import com.hrhg.framework.utils.BeanUtils;
import com.hrhg.framework.utils.DataUtils;
import com.hrhg.framework.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认Srv服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Service
public class DefaultServiceImpl implements DefaultService {
    /**
     * 简单数据类型接口。<br>
     *
     * @param req 请求参数
     * @return 简单响应结果
     */
    public String simple(String req) {
        return "请求数据：" + req + "\n" + "响应数据：已经响应！";
    }

    /**
     * 自定义数据类型接口。<br>
     *
     * @param req 请求参数
     * @return 自定义响应结果
     */
    public SelfResp self(SelfReq req) {
        SelfResp resp = new SelfResp();
        resp.setResult("自定义响应");
        return resp;
    }

    /**
     * 默认测试接口。<br>
     *
     * @param req 请求参数
     * @return 响应结果
     * @throws RespException 业务异常
     */
    public DefaultResp test(DefaultReq req) {
        // 创建响应对象
        DefaultResp resp = new DefaultResp();

        // 设置响应头信息
        resp.fillHeadByReq(req);

        // 设置响应状态及内容
        resp.getHead().setResult("0");
        resp.getHead().setMsg("访问接口OK！");

        // 请求时间格式，转换为日期类型
        Date date = DateUtils.toDateTypeByLength(req.getBody().getReq());

        // 设置消息体信息
        // 当前时间
        resp.getBody().setNow(DateUtils.getTimeStamp());
        // 响应时间
        resp.getBody().setResp(date.getTime());

        return resp;
    }

    /**
     * 查询字段接口。<br>
     *
     * @param req 请求参数
     * @return 查询字段响应结果
     */
    @Override
    public SearchResp search(SearchReq req) {
        // 响应结果
        SearchResp resp = new SearchResp();

        // 设置响应头信息
        resp.fillHeadByReq(req);

        // 设置响应状态及内容
        resp.getHead().setResult("0");
        resp.getHead().setMsg("查询字段接口访问成功 ！");

        // 标题全集
        Map<String, Object> allTitles = new LinkedHashMap<>();
        // spId
        allTitles.put("spId", "ID");
        // spName
        allTitles.put("spName", "名称");
        // 测试时间
        allTitles.put("time", "时间");

        // 数据集
        List<Simple> allDatas = new ArrayList<>();
        Simple simple;
        for (int ii = 0; ii < 10; ii++) {
            // 数据ii
            String idx = String.valueOf(ii + 1);
            simple = new Simple();
            simple.setSpId("测试id" + idx);
            simple.setSpName("测试名称" + idx);
            simple.setTime(DateUtils.getTimeStamp());
            allDatas.add(simple);
        }

        ///////////////////////////////////////////////////
        // 标题及数据输出过滤
        ///////////////////////////////////////////////////
        // 排除字段集合
        List<String> igoneFields = new ArrayList<>();

        if (DataUtils.isNullOrEmpty(req.getBody().getFields())) {
            // 全字段的情况
            resp.getBody().getTitle().putAll(allTitles);
            resp.getBody().getDatas().addAll(allDatas);
        } else {
            // 过滤字段的情况
            for (String key : allTitles.keySet()) {
                if (req.getBody().getFields().contains(key)) {
                    // 输出字段的情况
                    resp.getBody().getTitle().put(key, allTitles.get(key));
                } else {
                    // 排除字段
                    igoneFields.add(key);
                }
            }

            // 数据集过滤
            for (Simple sp : allDatas) {
                simple = new Simple();
                try {
                    // 复制对象数据
                    BeanUtils.copyBeanValue(sp, simple, igoneFields);
                } catch (Exception e) {
                }

                resp.getBody().getDatas().add(simple);
            }
        }

        // 分页信息
        resp.getBody().getPageInfo().setCnt(10);
        resp.getBody().getPageInfo().setCur(1);
        resp.getBody().getPageInfo().setPer(20);

        return resp;
    }
}
