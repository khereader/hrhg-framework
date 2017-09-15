/**
 * 同步安全本地缓存。<br>
 */
package com.hrhg.framework.cache;

import java.util.Iterator;
import java.util.Map;

/**
 * 同步安全本地缓存。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class SyncCache<K extends Object, V extends Object> extends AbstractCache<K, V> {
    /**
     * 构造函数。<br>
     *
     * @param category 类别名称
     */
    public SyncCache(String category) {
        super(category);
    }

    /**
     * 获取缓存类别名称。<br>
     *
     * @return 缓存类标名称
     */
    @Override
    public String getCategory() {
        return super.getCategory();
    }

    /**
     * 根据Key获取缓存对象。<br>
     *
     * @param key 缓存Key
     * @return 缓存值对象
     */
    @Override
    public synchronized V get(K key) {
        return super.get(key);
    }

    /**
     * 根据Key集合遍历器，获取所有的Key-Value集合。<br>
     *
     * @param keys key集合遍历器
     * @return Key-Value集合
     */
    @Override
    public synchronized Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys) {
        return super.getAll(keys);
    }

    /**
     * 检查是否缓存包含key信息。<br>
     *
     * @param key 待检查Key
     * @return 检查结果。true：包含；false：不包含
     */
    @Override
    public synchronized boolean containsKey(K key) {
        return super.containsKey(key);
    }

    /**
     * 添加Key-Value信息。<br>
     *
     * @param key   Key信息
     * @param value Value信息
     */
    @Override
    public synchronized void put(K key, V value) {
        super.put(key, value);
    }

    /**
     * 将整个集合添加为缓存信息。<br>
     *
     * @param entries Key-Value集合
     */
    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> entries) {
        super.putAll(entries);
    }

    /**
     * 删除Key。<br>
     *
     * @param key 待删除的Key
     */
    @Override
    public synchronized void remove(K key) {
        super.remove(key);
    }

    /**
     * 删除指定遍历器指定的Key集合。<br>
     *
     * @param keys Key集合
     */
    @Override
    public synchronized void removeAll(Iterator<? extends K> keys) {
        super.removeAll(keys);
    }

    /**
     * 检查缓存集合是否为空。<br>
     *
     * @return 检查结果。true：空；fasle：非空
     */
    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    /***
     * 缓存元素个数。<br>
     * @return 元素个数
     */
    @Override
    public synchronized int size() {
        return super.size();
    }

    /**
     * 清空缓存。<br>
     */
    @Override
    public synchronized void clear() {
        super.clear();
    }

    /**
     * 输出缓存集合。<br>
     *
     * @return 缓存集合
     */
    @Override
    public synchronized Map<? extends K, ? extends V> asMap() {
        return super.asMap();
    }
}
