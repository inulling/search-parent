package net.vipmro.search.elasticsearch;

/**
 * 缓存异常
 *
 * @author fengxiangyang
 * @date 2018/12/3
 */
public class ElasticsearchException extends RuntimeException {

    public ElasticsearchException() {
        super();
    }

    public ElasticsearchException(String message) {
        super(message);
    }

    public ElasticsearchException(Throwable e) {
        super(e);
    }

    public ElasticsearchException(String message, Throwable e) {
        super(message, e);
    }
}
