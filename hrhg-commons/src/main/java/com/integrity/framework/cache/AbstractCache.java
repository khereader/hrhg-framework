/**
 * 本地缓存抽象类。<br>
 */
package com.integrity.framework.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存抽象类。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AbstractCache<K extends Object, V extends Object> implements Cache<K, V> {
    /**
     * 类别名称
     */
    private final String category;
    /**
     * 混存数据集合
     */
    private final Map<K, V> cache;

    /**
     * 构造函数。<br>
     *
     * @param category 类别名称
     */
    protected AbstractCache(String category) {
        this.category = category;
        this.cache = new HashMap<K, V>();
    }

    /**
     * 获取缓存类别名称。<br>
     *
     * @return 缓存类标名称
     */
    @Override
    public String getCategory() {
        return this.category;
    }

    /**
     * 根据Key获取缓存对象。<br>
     *
     * @param key 缓存Key
     * @return 缓存值对象
     */
    @Override
    public V get(K key) {
        return this.cache.get(key);
    }

    /**
     * 根据Key集合遍历器，获取所有的Key-Value集合。<br>
     *
     * @param keys key集合遍历器
     * @return Key-Value集合
     */
    @Override
    public Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys) {
        // 创建返回结果
        Map<K, V> map = new HashMap<K, V>();

        while (keys.hasNext()) {
            // 逐个获取
            K key = keys.next();

            if (!containsKey(key)) {
                // 忽略当前不存在的Key
                continue;
            }

            map.put(key, cache.get(key));
        }

        return map;
    }

    /**
     * 检查是否缓存包含key信息。<br>
     *
     * @param key 待检查Key
     * @return 检查结果。true：包含；false：不包含
     */
    @Override
    public boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }

    /**
     * 添加Key-Value信息。<br>
     *
     * @param key   Key信息
     * @param value Value信息
     */
    @Override
    public void put(K key, V value) {
        this.put(key, value);
    }

    /**
     * 将整个集合添加为缓存信息。<br>
     *
     * @param entries Key-Value集合
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> entries) {
        this.cache.putAll(entries);
    }

    /**
     * 删除Key。<br>
     *
     * @param key 待删除的Key
     */
    @Override
    public void remove(K key) {
        this.cache.remove(key);
    }

    /**
     * 删除指定遍历器指定的Key集合。<br>
     *
     * @param keys Key集合
     */
    @Override
    public void removeAll(Iterator<? extends K> keys) {
        while (keys.hasNext()) {
            K key = keys.next();
            remove(key);
        }
    }

    /**
     * 检查缓存集合是否为空。<br>
     *
     * @return 检查结果。true：空；fasle：非空
     */
    @Override
    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    /***
     * 缓存元素个数。<br>
     * @return 元素个数
     */
    @Override
    public int size() {
        return this.cache.size();
    }

    /**
     * 清空缓存。<br>
     */
    @Override
    public void clear() {
        this.cache.clear();
    }

    /**
     * 输出缓存集合。<br>
     *
     * @return 缓存集合
     */
    @Override
    public Map<? extends K, ? extends V> asMap() {
        return new ConcurrentHashMap<K, V>(this.cache);
    }
}
