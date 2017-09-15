/**
 * Redis自定义数据源。<br>
 */
package com.hrhg.framework.redis;

import redis.clients.jedis.ShardedJedis;

/**
 * Redis自定义数据源。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface RedisDataSource {
    /**
     * 获取Redis客户端。<br>
     *
     * @return Redis客户端
     * @throws Exception 系统异常
     */
    ShardedJedis getRedisClient() throws Exception;

    /**
     * 释放Redis客户端资源。<br>
     *
     * @param shardedJedis 客户端资源
     * @throws Exception 系统异常
     */
    void close(ShardedJedis shardedJedis) throws Exception;
}
