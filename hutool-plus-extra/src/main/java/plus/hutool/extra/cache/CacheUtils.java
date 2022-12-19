package plus.hutool.extra.cache;

import cn.hutool.cache.impl.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaLowerCamelCaseVariableNaming", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class CacheUtils {
    private CacheUtils() {}

    /**
     * 创建FIFO(first in first out) 先进先出缓存.
     *
     * @param capacity 容量
     * @param duration 过期时长
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link FIFOCache}
     */
    public static <K, V> FIFOCache<K, V> newFIFOCache(int capacity, Duration duration) {
        return new FIFOCache<>(capacity, duration.toMillis());
    }

    /**
     * 创建FIFO(first in first out) 先进先出缓存.
     *
     * @param capacity 容量
     * @param duration 过期时长
     * @param timeUnit 时长的单位
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link FIFOCache}
     */
    public static <K, V> FIFOCache<K, V> newFIFOCache(int capacity, long duration, TimeUnit timeUnit) {
        return new FIFOCache<>(capacity, timeUnit.toMillis(duration));
    }

    /**
     * 创建LFU(least frequently used) 最少使用率缓存.
     *
     * @param capacity 容量
     * @param duration 过期时长
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link LFUCache}
     */
    public static <K, V> LFUCache<K, V> newLFUCache(int capacity, Duration duration) {
        return new LFUCache<>(capacity, duration.toMillis());
    }

    /**
     * 创建LFU(least frequently used) 最少使用率缓存.
     *
     * @param capacity 容量
     * @param duration 过期时长
     * @param timeUnit 时长的单位
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link LFUCache}
     */
    public static <K, V> LFUCache<K, V> newLFUCache(int capacity, long duration, TimeUnit timeUnit) {
        return new LFUCache<>(capacity, timeUnit.toMillis(duration));
    }

    /**
     * 创建LRU (least recently used)最近最久未使用缓存.
     *
     * @param capacity 容量
     * @param duration 过期时长
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link LRUCache}
     */
    public static <K, V> LRUCache<K, V> newLRUCache(int capacity, Duration duration) {
        return new LRUCache<>(capacity, duration.toMillis());
    }

    /**
     * 创建LRU (least recently used)最近最久未使用缓存.
     *
     * @param capacity 容量
     * @param duration 过期时长
     * @param timeUnit 时长的单位
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link LRUCache}
     */
    public static <K, V> LRUCache<K, V> newLRUCache(int capacity, long duration, TimeUnit timeUnit) {
        return new LRUCache<>(capacity, timeUnit.toMillis(duration));
    }

    /**
     * 创建定时缓存.
     *
     * @param duration 过期时长
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link TimedCache}
     */
    public static <K, V> TimedCache<K, V> newTimedCache(Duration duration) {
        return new TimedCache<>(duration.toMillis());
    }

    /**
     * 创建定时缓存.
     *
     * @param duration 过期时长
     * @param timeUnit 时长的单位
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link TimedCache}
     */
    public static <K, V> TimedCache<K, V> newTimedCache(long duration, TimeUnit timeUnit) {
        return new TimedCache<>(timeUnit.toMillis(duration));
    }

    /**
     * 创建弱引用缓存.
     *
     * @param duration 过期时长
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link WeakCache}
     */
    public static <K, V> WeakCache<K, V> newWeakCache(Duration duration) {
        return new WeakCache<>(duration.toMillis());
    }

    /**
     * 创建弱引用缓存.
     *
     * @param duration 过期时长
     * @param timeUnit 时长的单位
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return {@link WeakCache}
     */
    public static <K, V> WeakCache<K, V> newWeakCache(long duration, TimeUnit timeUnit) {
        return new WeakCache<>(timeUnit.toMillis(duration));
    }

}
