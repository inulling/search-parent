package net.vipmro.search.redis;

import net.vipmro.search.core.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fengxiangyang
 * @date 2018/12/3
 */
public class JedisHandler {
    private static final Logger logger = LoggerFactory.getLogger(JedisHandler.class);

    @Autowired
    private JedisConfig jedisConfig;

    private Jedis getJedis() {
        return jedisConfig.redisPoolFactory().getResource();
    }

    /**
     * 缓存
     *
     * @param key
     * @param data
     * @param <T>
     * @return
     */
    public <T> T cache(String key, CacheData data) {
        return cache(key, data, CacheTime.HOUR_1);
    }

    /**
     * 缓存
     *
     * @param key
     * @param data
     * @param expireTime
     * @param <T>
     * @return
     */
    public <T> T cache(String key, CacheData data, int expireTime) {
        Object result = get(key);
        if (result == null) {
            result = data.findData();
            this.setex(key, result, expireTime);
        }
        return (T) result;
    }

    /**
     * 设置
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, Object value) {
        String back;
        try (Jedis jedis = getJedis()) {
            back = jedis.set(key.getBytes(), ObjectUtils.serialize(value));
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}", e.getMessage(), key, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将值 value 关联到 key , 并将 key 的生存时间设为 seconds (以秒为单位)。
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String setex(String key, Object value, int seconds) {
        String back;
        try (Jedis jedis = getJedis()) {
            back = jedis.setex(key.getBytes(), seconds, ObjectUtils.serialize(value));
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}, seconds={}", e.getMessage(), key, value, seconds);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * key是否存在
     * @param key
     * @param seconds
     * @return
     */
    public Boolean setnx(String key, int seconds) {
        boolean result;
        try (Jedis jedis = getJedis()) {
            result = jedis.setnx(key, "1") == 1;
            if (result) {
                jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, seconds={}", e.getMessage(), key, seconds);
            throw new JedisException(e);
        }
        return result;
    }

    /**
     * 删除key对应的value
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        Long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.del(key);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 获取string类型
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        Object back;
        try (Jedis jedis = getJedis()) {
            final byte[] bytes = jedis.get(key.getBytes());
            back = ObjectUtils.unserialize(bytes);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 返回哈希表 key 中, 所有的域和值。
     * 在返回值里, 紧跟每个域名(field name)之后是域的值(value), 所以返回值的长度是哈希表大小的两倍。
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> back;
        try (Jedis jedis = getJedis()) {
            back = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在, 一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中, 旧值将被覆盖。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(String key, String field, String value) {
        Long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, field={}, value={}", e.getMessage(), key, field, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        String back;
        try (Jedis jedis = getJedis()) {
            back = jedis.hget(key, field);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, field={}", e.getMessage(), key, field);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域, 不存在的域将被忽略。
     *
     * @param key
     * @param field
     * @return
     */
    public long hdel(String key, String field) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.hdel(key, field);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, field={}", e.getMessage(), key, field);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, String... value) {
        Long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.lpush(key, value);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}", e.getMessage(), key, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾
     *
     * @param key
     * @param value
     * @return
     */
    public long rpush(String key, String... value) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.rpush(key, value);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}", e.getMessage(), key, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 通过下标替换元素的内容
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(String key, long index, String value) {
        String back;
        try (Jedis jedis = getJedis()) {
            back = jedis.lset(key, index, value);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, index={}, value={}", e.getMessage(), key, index, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 移除有序集合lsit中的参数
     * 0所有
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lrem(String key, long count, String value) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.lrem(key, count, value);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, count, value={}", e.getMessage(), key, count, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 返回列表 key 的长度。
     * 如果 key 不存在, 则 key 被解释为一个空列表, 返回 0 .
     * 如果 key 不是列表类型, 返回一个错误。
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.llen(key);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 返回列表 key 中指定区间内的元素
     * -1 表示列表的最后一个元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, int start, int end) {
        List<String> back;
        try (Jedis jedis = getJedis()) {
            back = jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, start={}, end={}", e.getMessage(), key, start, end);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key
     * @return
     */
    public long incr(String key) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.incr(key);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将 key 中储存的数字值减一。
     *
     * @param key
     * @return
     */
    public long decr(String key) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.decr(key);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 为给定 key 设置生存时间, 当 key 过期时(生存时间为 0 ), 它会被自动删除。
     *
     * @param key
     * @param seconds
     * @return
     */
    public long expire(String key, int seconds) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, seconds={}", e.getMessage(), key, seconds);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将一个或多个 member 元素加入到集合 key 当中, 已经存在于集合的 member 元素将被忽略。
     *
     * @param key
     * @param value
     * @return
     */
    public long sadd(String key, String value) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.sadd(key, value);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}", e.getMessage(), key, value);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 检查给定 key 是否存在。
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        boolean back;
        try (Jedis jedis = getJedis()) {
            back = jedis.exists(key);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}", e.getMessage(), key);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 返回有序集 key 中, 所有 score 值介于 min 和 max 之间
     * (包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key, String min, String max) {
        Set<String> back;
        try (Jedis jedis = getJedis()) {
            back = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, min={}, max={}", e.getMessage(), key, min, max);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 有序集合
     * 根据分数降序排列
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Set<String> back;
        try (Jedis jedis = getJedis()) {
            back = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, max={}, min={}", e.getMessage(), key, max, min);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public double zadd(String key, double score, String member) {
        double back;
        try (Jedis jedis = getJedis()) {
            back = jedis.zadd(key, score, member);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, score={}, member={}", e.getMessage(), key, score, member);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 有序集合
     * score增加或减少值
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
        double back;
        try (Jedis jedis = getJedis()) {
            back = jedis.zincrby(key, score, member);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, score={}, member={}", e.getMessage(), key, score, member);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 移除有序系列中的指定范围
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zremrangeByScore(String key, String start, String end) {
        long back;
        try (Jedis jedis = getJedis()) {
            back = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, start={}, end={}", e.getMessage(), key, start, end);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 有序集合
     * 降序排列
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, long start, long end) {
        Set<String> back;
        try (Jedis jedis = getJedis()) {
            back = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, start={}, end={}", e.getMessage(), key, start, end);
            throw new JedisException(e);
        }
        return back;
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param value
     * @param exptime
     * @throws Exception
     */
    public String setWithExpireTime(String key, String value, int exptime) {
        String back;
        try (Jedis jedis = getJedis()) {
            back = jedis.set(key, value, "NX", "EX", exptime);
        } catch (Exception e) {
            logger.info("jedis执行失败：ex={}, key={}, value={}, exptime={}", e.getMessage(), key, value, exptime);
            throw new JedisException(e);
        }
        return back;
    }

}
