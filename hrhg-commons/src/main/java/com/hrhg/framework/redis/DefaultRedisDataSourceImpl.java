/**
 * Redis数据源实例。<br>
 */
package com.hrhg.framework.redis;

import com.hrhg.framework.utils.DataUtils;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Redis数据源实例。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Repository("defaultRedisDataSourceImpl")
public class DefaultRedisDataSourceImpl implements RedisDataSource {
    /**
     * Redis切片数据连接池
     */
    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    /**
     * 获取Redis客户端。<br>
     *
     * @return Redis客户端
     * @throws Exception 系统异常
     */
    @Override
    public ShardedJedis getRedisClient() throws Exception {
        return this.shardedJedisPool.getResource();
    }

    /**
     * 释放Redis客户端资源。<br>
     *
     * @param shardedJedis 客户端资源
     * @throws Exception 系统异常
     */
    @Override
    public void close(ShardedJedis shardedJedis) throws Exception {
        if (DataUtils.isNullOrEmpty(shardedJedis)) {
            // Redis客户端对象为空
            return;
        }

        // 关闭连接
        shardedJedis.close();
    }
}
