/**
 * 读写安全本地缓存。<br>
 */
package com.hrhg.framework.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写安全本地缓存。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class RWCache<K extends Object, V extends Object> extends AbstractCache<K, V> {
    /**
     * 读写分离线程安全锁
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 构造函数。<br>
     *
     * @param category 类别名称
     */
    protected RWCache(String category) {
        super(category);
    }

    /**
     * 获取缓存类别名称。<br>
     *
     * @return 缓存类标名称
     */
    @Override
    public String getCategory() {
        lock.readLock().lock();
        try {
            return super.getCategory();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 根据Key获取缓存对象。<br>
     *
     * @param key 缓存Key
     * @return 缓存值对象
     */
    @Override
    public V get(K key) {
        lock.readLock().lock();
        try {
            return super.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 根据Key集合遍历器，获取所有的Key-Value集合。<br>
     *
     * @param keys key集合遍历器
     * @return Key-Value集合
     */
    @Override
    public Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys) {
        lock.readLock().lock();
        try {
            return super.getAll(keys);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 检查是否缓存包含key信息。<br>
     *
     * @param key 待检查Key
     * @return 检查结果。true：包含；false：不包含
     */
    @Override
    public boolean containsKey(K key) {
        lock.readLock().lock();
        try {
            return super.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 添加Key-Value信息。<br>
     *
     * @param key   Key信息
     * @param value Value信息
     */
    @Override
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            super.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 将整个集合添加为缓存信息。<br>
     *
     * @param entries Key-Value集合
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> entries) {
        lock.writeLock().lock();
        try {
            super.putAll(entries);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 删除Key。<br>
     *
     * @param key 待删除的Key
     */
    @Override
    public void remove(K key) {
        lock.writeLock().lock();
        try {
            super.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 删除指定遍历器指定的Key集合。<br>
     *
     * @param keys Key集合
     */
    @Override
    public void removeAll(Iterator<? extends K> keys) {
        lock.writeLock().lock();
        try {
            super.removeAll(keys);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 检查缓存集合是否为空。<br>
     *
     * @return 检查结果。true：空；fasle：非空
     */
    @Override
    public boolean isEmpty() {
        lock.readLock().lock();
        try {
            return super.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    /***
     * 缓存元素个数。<br>
     * @return 元素个数
     */
    @Override
    public int size() {
        lock.readLock().lock();
        try {
            return super.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 清空缓存。<br>
     */
    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            super.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 输出缓存集合。<br>
     *
     * @return 缓存集合
     */
    @Override
    public Map<? extends K, ? extends V> asMap() {
        lock.readLock().lock();
        try {
            return super.asMap();
        } finally {
            lock.readLock().unlock();
        }
    }
}
