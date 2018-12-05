package net.vipmro.search.redis;

/**
 * @author fengxiangyang
 * @date 2018/12/3
 */
public enum CacheKey implements CacheTime {
    CACHE_BY_NAME,;
    private final String value;

    CacheKey() {
        this.value = "SEARCH:REINDEX" + name();
    }

    @Override
    public String key() {
        return value;
    }

    public String key(Object... params) {
        StringBuilder key = new StringBuilder(value);
        if (params != null && params.length > 0) {
            for (Object param : params) {
                key.append(':');
                key.append(String.valueOf(param));
            }
        }
        return key.toString();
    }
}
