/**
 * Redis切片信息。<br>
 */
package com.hrhg.framework.redis;

import redis.clients.jedis.JedisShardInfo;

/**
 * Redis切片信息。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class BaseJedisShardInfo extends JedisShardInfo {
    /**
     * 构造函数。<br>
     *
     * @param uri               资源信息
     * @param connectionTimeout 连接超时时间
     * @param soTimeout         超时时间
     */
    public BaseJedisShardInfo(String uri, int connectionTimeout, int soTimeout) {
        super(uri);
        // 设置超时时间
        this.setConnectionTimeout(connectionTimeout);
        this.setSoTimeout(soTimeout);
    }
}
