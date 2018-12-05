package net.vipmro.search.redis;

/**
 * @author fengxiangyang
 * @date 2018/12/03
 */
@FunctionalInterface
public interface CacheData {
    Object findData();
}
