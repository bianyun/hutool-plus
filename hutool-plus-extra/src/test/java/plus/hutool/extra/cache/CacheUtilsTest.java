package plus.hutool.extra.cache;

import cn.hutool.cache.impl.*;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class CacheUtilsTest {

    @Test
    void testNewFIFOCache1() {
        final Duration duration = Duration.ofMillis(20);

        final FIFOCache<String, String> result = CacheUtils.newFIFOCache(1, duration);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewFIFOCache2() {
        final FIFOCache<String, String> result = CacheUtils.newFIFOCache(1, 20, TimeUnit.MILLISECONDS);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewLFUCache1() {
        final Duration duration = Duration.ofMillis(20);
        final LFUCache<String, String> result = CacheUtils.newLFUCache(1, duration);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewLFUCache2() {
        final LFUCache<String, String> result = CacheUtils.newLFUCache(1, 20, TimeUnit.MILLISECONDS);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewLRUCache1() {
        final Duration duration = Duration.ofMillis(20);
        final LRUCache<String, String> result = CacheUtils.newLRUCache(1, duration);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewLRUCache2() {
        final LRUCache<String, String> result = CacheUtils.newLRUCache(1, 20, TimeUnit.MILLISECONDS);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewTimedCache1() {
        final Duration duration = Duration.ofMillis(20);
        final TimedCache<String, String> result = CacheUtils.newTimedCache(duration);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewTimedCache2() {
        final TimedCache<String, String> result = CacheUtils.newTimedCache(20, TimeUnit.MILLISECONDS);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewWeakCache1() {
        final Duration duration = Duration.ofMillis(20);
        final WeakCache<String, String> result = CacheUtils.newWeakCache(duration);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }

    @Test
    void testNewWeakCache2() {
        final WeakCache<String, String> result = CacheUtils.newWeakCache(20, TimeUnit.MILLISECONDS);
        result.put("key", "value");

        assertThat(result.get("key")).isEqualTo("value");
        ThreadUtil.safeSleep(25);
        assertThat(result.get("key")).isNull();
    }
}
