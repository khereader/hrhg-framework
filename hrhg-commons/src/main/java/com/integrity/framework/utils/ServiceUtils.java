/**
 * 系统管理工具。<br>
 */
package com.integrity.framework.utils;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.integrity.framework.srv.blogic.BaseBLogic;
import org.springframework.context.ApplicationContext;

/**
 * 系统管理工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class ServiceUtils {
    /**
     * 默认构造函数。<br>
     */
    private ServiceUtils() {
    }

    /**
     * 获取指定类型服务Bean对象。<br>
     *
     * @param clazz 服务类型
     * @return 服务Bean8
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseBLogic> T getServiceBean(Class<?> clazz) {
        // 获取Spring中的Bean
        ApplicationContext context = ServiceBean.getSpringContext();
        // 获取bean
        T baseBLogic = (T) context.getBean(clazz);
        return baseBLogic;
    }
}