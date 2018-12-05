package net.vipmro.search.redis;

/**
 * 缓存异常
 *
 * @author fengxiangyang
 * @date 2018/12/3
 */
public class JedisException extends RuntimeException {

    public JedisException() {
        super();
    }

    public JedisException(String message) {
        super(message);
    }

    public JedisException(Throwable e) {
        super(e);
    }

    public JedisException(String message, Throwable e) {
        super(message, e);
    }
}
