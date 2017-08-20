/**
 * Redis工具通用类。<br>
 */
package com.hrhg.framework.utils;

import com.hrhg.framework.redis.RedisDataSource;
import redis.clients.jedis.ShardedJedis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis工具通用类(支持Json数据格式的特殊使用，Jedis自带api，可以直接使用)。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class RedisUtils {
    /**
     * 每秒对应毫秒数
     */
    public static final long MILLISECONDS_PER_SECOND = 1000L;
    /**
     * 每分钟对应的秒数
     */
    public static final long SECONDS_PER_MINUTE = 60;
    /**
     * 每小时对应的分钟数
     */
    public static final long MINUTES_PER_HOUR = 60;
    /**
     * 默认失效时间(2小时,单位秒)
     */
    public static final long DEFAULT_EXPIRE_SECOND = 2 * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
    /**
     * 默认失效时间(2小时,单位毫秒)
     */
    public static final long DEFAULT_EXPIRE_MILLISECOND = DEFAULT_EXPIRE_SECOND * MILLISECONDS_PER_SECOND;

    /**
     * 私有构造函数。<br>
     */
    private RedisUtils() {
    }

    /**
     * 设置key超时时间。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             设置超时的Key
     * @param expire          超时时间（单位毫秒）
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static void expire(RedisDataSource redisDataSource, String key, long expire) throws Exception {
        if (DataUtils.isNullOrEmpty(key)) {
            // 没有待处理的Key
            return;
        }

        if (expire <= 0) {
            // 无需设置过期时间
            return;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 设置超时时间
            shardedJedis.pexpire(key, expire);
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 设置key到期时间。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             设置超时的Key
     * @param expire          到期时间
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static void expireAt(RedisDataSource redisDataSource, String key, Date expire) throws Exception {
        if (DataUtils.isNullOrEmpty(key)) {
            // 没有待处理的Key
            return;
        }

        if (DataUtils.isNullOrEmpty(expire)) {
            // 到期时间对象为空
            return;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 设置超时时间
            shardedJedis.pexpireAt(key, expire.getTime());
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取剩余时间（Time To Live）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             设置超时的Key
     * @return 剩余时间
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static long ttl(RedisDataSource redisDataSource, String key) throws Exception {
        if (DataUtils.isNullOrEmpty(key)) {
            // 没有待处理的Key
            return -2;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取剩余时间
            return shardedJedis.pttl(key);
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 删除Key。<br>
     *
     * @param redisDataSource Redis数据源
     * @param keys            待删除的Key
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static void del(RedisDataSource redisDataSource, String... keys) throws Exception {
        if (DataUtils.isNullOrEmpty(keys)) {
            // 没有待处理的Key
            return;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            for (String key : keys) {
                // 逐个删除
                shardedJedis.del(key);
            }
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取缓存数据（普通的原子数据）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    public static <T extends Object> T getJson(RedisDataSource redisDataSource, String key, Class<T> clazz) throws Exception {
        return getJson(redisDataSource, key, clazz, false);
    }

    /**
     * 获取缓存数据（普通的原子数据）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param del             是否删除标记
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T getJson(RedisDataSource redisDataSource, String key, Class<T> clazz, boolean del) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key
            return null;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            String data = shardedJedis.get(key);

            if (StringUtils.isEmpty(data)) {
                // 获取数据为空的情况
                return null;
            }

            // 将响应结果字符串，转换为Json对象
            T result = JsonUtils.json2Object(data, clazz);

            if (del) {
                // 删除原来Key
                shardedJedis.del(key);
            }

            // 返回响应结果
            return result;
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 设置缓存数据（普通的原子数据）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param data            指定类型数据对象
     * @param <T>             模版参数
     * @throws Exception 系统异常
     */
    public static <T extends Object> void setJson(RedisDataSource redisDataSource, String key, T data) throws Exception {
        setJson(redisDataSource, key, data, DEFAULT_EXPIRE_MILLISECOND);
    }

    /**
     * 设置缓存数据（普通的原子数据）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param data            指定类型数据对象
     * @param expire          过期时间(单位毫秒)
     * @param <T>             模版参数
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> void setJson(RedisDataSource redisDataSource, String key, T data, long expire) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key
            return;
        }

        if (DataUtils.isNullOrEmpty(data)) {
            // 设置数据为空
            return;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 将对象转换为json
            String result = JsonUtils.object2Json(data);

            if (StringUtils.isEmpty(result)) {
                // 生成Json字符串为空
                return;
            }

            if (expire > 0) {
                // 设置过期时间
                // 获取存储的字符串信息
                shardedJedis.psetex(key, expire, result);
            } else {
                // 不设置过期时间
                shardedJedis.set(key, result);
            }
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param field           哈希字段
     * @param clazz           对象类型
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    public static <T extends Object> T hgetJson(RedisDataSource redisDataSource,
                                                String key, String field, Class<T> clazz) throws Exception {
        return hgetJson(redisDataSource, key, field, clazz, false);
    }

    /**
     * 获取缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param field           哈希字段
     * @param clazz           对象类型
     * @param del             字段是否删除标记
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T hgetJson(RedisDataSource redisDataSource, String key,
                                                String field, Class<T> clazz, boolean del) throws Exception {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            // 没有待处理的Key或field
            return null;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            String data = shardedJedis.hget(key, field);

            if (StringUtils.isEmpty(data)) {
                // 获取数据为空的情况
                return null;
            }

            // 将响应结果字符串，转换为Json对象
            T result = JsonUtils.json2Object(data, clazz);

            if (del) {
                // 删除原来Key
                shardedJedis.hdel(key, field);
            }

            // 返回响应结果
            return result;
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param fields          哈希字段
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    public static <T extends Object> Map<String, T> hmgetJson(RedisDataSource redisDataSource, String key,
                                                              Class<T> clazz, String... fields) throws Exception {
        return hmgetJson(redisDataSource, key, clazz, false, fields);
    }

    /**
     * 获取缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param del             字段是否删除标记
     * @param fields          哈希字段
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> Map<String, T> hmgetJson(RedisDataSource redisDataSource, String key,
                                                              Class<T> clazz, boolean del, String... fields) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key或field
            return null;
        }

        if (DataUtils.isNullOrEmpty(fields)) {
            // 字段集合为空
            return null;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            List<String> datas = shardedJedis.hmget(key, fields);

            if (DataUtils.isNullOrEmpty(datas)) {
                // 获取数据为空的情况
                return null;
            }

            // 将响应结果字符串，转换为Json对象
            Map<String, T> results = new HashMap<String, T>();

            for (int ii = 0; ii < fields.length; ii++) {
                // 逐个处理Hash
                String data = datas.get(ii);
                // 生成对象
                T result = JsonUtils.json2Object(data, clazz);
                // 添加到结果集
                results.put(fields[ii], result);

                if (del) {
                    // 删除原来Key
                    shardedJedis.hdel(key, fields[ii]);
                }
            }

            // 返回响应结果
            return results;
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param field           哈希字段
     * @param clazz           对象类型
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    public static <T extends Object> Map<String, T> hgetAllJson(RedisDataSource redisDataSource,
                                                                String key, String field, Class<T> clazz) throws Exception {
        return hgetAllJson(redisDataSource, key, clazz, false);
    }

    /**
     * 获取缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param del             字段是否删除标记
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> Map<String, T> hgetAllJson(RedisDataSource redisDataSource, String key,
                                                                Class<T> clazz, boolean del) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key或field
            return null;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            Map<String, String> datas = shardedJedis.hgetAll(key);

            if (DataUtils.isNullOrEmpty(datas)) {
                // 获取数据为空的情况
                return null;
            }

            // 将响应结果字符串，转换为Json对象
            Map<String, T> results = new HashMap<String, T>();

            for (String field : datas.keySet()) {
                // 逐个处理Hash
                String data = datas.get(field);
                // 生成对象
                T result = JsonUtils.json2Object(data, clazz);
                // 添加到结果集
                results.put(field, result);
            }

            if (del) {
                // 删除原来Key
                shardedJedis.del(key);
            }

            // 返回响应结果
            return results;
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 设置缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param data            指定类型数据对象
     * @param <T>             模版参数
     * @throws Exception 系统异常
     */
    public static <T extends Object> void hsetJson(RedisDataSource redisDataSource, String key,
                                                   String field, T data) throws Exception {
        hsetJson(redisDataSource, key, field, data, DEFAULT_EXPIRE_MILLISECOND);
    }

    /**
     * 设置缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param data            指定类型数据对象
     * @param expire          过期时间(单位毫秒)
     * @param <T>             模版参数
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> void hsetJson(RedisDataSource redisDataSource, String key,
                                                   String field, T data, long expire) throws Exception {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            // 没有待处理的Key或field为空
            return;
        }

        if (DataUtils.isNullOrEmpty(data)) {
            // 设置数据为空
            return;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 将对象转换为json
            String result = JsonUtils.object2Json(data);

            if (StringUtils.isEmpty(result)) {
                // 生成Json字符串为空
                return;
            }

            // 设置字符串信息
            shardedJedis.hset(key, field, result);

            if (expire > 0) {
                // 设置过期时间(重新更新)
                shardedJedis.pexpire(key, expire);
            }
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 设置缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param datas           指定类型数据对象
     * @param <T>             模版参数
     * @throws Exception 系统异常
     */
    public static <T extends Object> void hmsetJson(RedisDataSource redisDataSource, String key,
                                                    Map<String, T> datas) throws Exception {
        hmsetJson(redisDataSource, key, datas, DEFAULT_EXPIRE_MILLISECOND);
    }

    /**
     * 设置缓存数据（Hash）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param datas           指定类型数据对象
     * @param expire          过期时间(单位毫秒)
     * @param <T>             模版参数
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> void hmsetJson(RedisDataSource redisDataSource, String key,
                                                    Map<String, T> datas, long expire) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key
            return;
        }

        if (DataUtils.isNullOrEmpty(datas)) {
            // 设置数据为空
            return;
        }

        // 创建数据集合
        Map<String, String> results = new HashMap<String, String>();

        for (String field : datas.keySet()) {
            // 数据逐个转换为Json字符串
            // 将对象转换为json
            String result = JsonUtils.object2Json(datas.get(field));

            if (StringUtils.isEmpty(result)) {
                // 转换结果为空
                continue;
            }

            // 添加到集合
            results.put(field, result);
        }

        if (DataUtils.isNullOrEmpty(results)) {
            // 结果集为空的情况
            return;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 设置字符串信息
            shardedJedis.hmset(key, results);

            if (expire > 0) {
                // 设置过期时间(重新更新)
                shardedJedis.pexpire(key, expire);
            }
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取缓存数据（List）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T lpopJson(RedisDataSource redisDataSource, String key, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key或field
            return null;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            String data = shardedJedis.lpop(key);

            if (StringUtils.isEmpty(data)) {
                // 获取数据为空的情况
                return null;
            }

            // 生成对象
            T result = JsonUtils.json2Object(data, clazz);

            // 返回响应结果
            return result;
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 设置缓存数据（List）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param datas           添加数据
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> void lpushJson(RedisDataSource redisDataSource, String key, T... datas) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key或field
            return;
        }

        if (DataUtils.isNullOrEmpty(datas)) {
            // 添加的数据集合为空
            return;
        }

        // 生成待处理json
        String[] results = new String[datas.length];

        for (int ii = 0; ii < datas.length; ii++) {
            // 逐个处理Json数据
            results[ii] = JsonUtils.object2Json(datas[ii]);
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            shardedJedis.lpush(key, results);
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 获取缓存数据（List）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param clazz           对象类型
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T rpopJson(RedisDataSource redisDataSource, String key, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key或field
            return null;
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            String data = shardedJedis.rpop(key);

            if (StringUtils.isEmpty(data)) {
                // 获取数据为空的情况
                return null;
            }

            // 生成对象
            T result = JsonUtils.json2Object(data, clazz);

            // 返回响应结果
            return result;
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }

    /**
     * 设置缓存数据（List）。<br>
     *
     * @param redisDataSource Redis数据源
     * @param key             key信息
     * @param datas           添加数据
     * @param <T>             模版参数
     * @return 生成数据对象
     * @throws Exception 系统异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> void rpushJson(RedisDataSource redisDataSource, String key, T... datas) throws Exception {
        if (StringUtils.isEmpty(key)) {
            // 没有待处理的Key或field
            return;
        }

        if (DataUtils.isNullOrEmpty(datas)) {
            // 添加的数据集合为空
            return;
        }

        // 生成待处理json
        String[] results = new String[datas.length];

        for (int ii = 0; ii < datas.length; ii++) {
            // 逐个处理Json数据
            results[ii] = JsonUtils.object2Json(datas[ii]);
        }

        // redis客户端对象
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();

        try {
            // 获取存储的字符串信息
            shardedJedis.rpush(key, results);
        } finally {
            // 释放资源
            redisDataSource.close(shardedJedis);
        }
    }
}
