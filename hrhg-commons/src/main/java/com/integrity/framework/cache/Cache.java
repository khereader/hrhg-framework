/**
 * 本地缓存接口。<br>
 */
package com.integrity.framework.cache;

import java.util.Iterator;
import java.util.Map;

/**
 * 本地缓存接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface Cache<K extends Object, V extends Object> {
    /**
     * 获取缓存类别名称。<br>
     *
     * @return 缓存类标名称
     */
    String getCategory();

    /**
     * 根据Key获取缓存对象。<br>
     *
     * @param key 缓存Key
     * @return 缓存值对象
     */
    V get(K key);

    /**
     * 根据Key集合遍历器，获取所有的Key-Value集合。<br>
     *
     * @param keys key集合遍历器
     * @return Key-Value集合
     */
    Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys);

    /**
     * 检查是否缓存包含key信息。<br>
     *
     * @param key 待检查Key
     * @return 检查结果。true：包含；false：不包含
     */
    boolean containsKey(K key);

    /**
     * 添加Key-Value信息。<br>
     *
     * @param key   Key信息
     * @param value Value信息
     */
    void put(K key, V value);

    /**
     * 将整个集合添加为缓存信息。<br>
     *
     * @param entries Key-Value集合
     */
    void putAll(Map<? extends K, ? extends V> entries);

    /**
     * 删除Key。<br>
     *
     * @param key 待删除的Key
     */
    void remove(K key);

    /**
     * 删除指定遍历器指定的Key集合。<br>
     *
     * @param keys Key集合
     */
    void removeAll(Iterator<? extends K> keys);

    /**
     * 检查缓存集合是否为空。<br>
     *
     * @return 检查结果。true：空；fasle：非空
     */
    boolean isEmpty();

    /***
     * 缓存元素个数。<br>
     * @return 元素个数
     */
    int size();

    /**
     * 清空缓存。<br>
     */
    void clear();

    /**
     * 输出缓存集合。<br>
     *
     * @return 缓存集合
     */
    Map<? extends K, ? extends V> asMap();
}