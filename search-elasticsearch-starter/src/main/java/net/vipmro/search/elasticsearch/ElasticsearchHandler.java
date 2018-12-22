package net.vipmro.search.elasticsearch;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * ES处理类
 * @author fengxiangyang
 * @date 2018/6/14
 */
public class ElasticsearchHandler {
    private final static Logger logger = LoggerFactory.getLogger(ElasticsearchHandler.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 插入
     *
     * @param indexRequest
     */
    public void insert(IndexRequest indexRequest) {
        try {
            restHighLevelClient.index(indexRequest);
        } catch (IOException e) {
            logger.error("插入执行异常：ex={}", e.getMessage());
            throw new ElasticsearchException(e);
        }
    }

    /**
     * 更新
     *
     * @param updateRequest
     */
    public void update(UpdateRequest updateRequest) {
        try {
            restHighLevelClient.update(updateRequest);
        } catch (IOException e) {
            logger.error("更新执行异常：ex={}", e.getMessage());
            throw new ElasticsearchException(e);
        }
    }

    /**
     * 删除
     *
     * @param deleteRequest
     */
    public void delete(DeleteRequest deleteRequest) {
        try {
            restHighLevelClient.delete(deleteRequest);
        } catch (IOException e) {
            logger.error("删除执行异常：ex={}", e.getMessage());
            throw new ElasticsearchException(e);
        }
    }

    /**
     * 批量操作
     *
     * @param bulkRequest
     */
    public void bulk(BulkRequest bulkRequest) {
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest);
            if (bulkResponse.hasFailures()) {
                logger.info(bulkResponse.buildFailureMessage());
                throw new ElasticsearchException();
            }
        } catch (IOException e) {
            logger.error("批量执行异常：ex={}", e.getMessage());
            throw new ElasticsearchException(e);
        }
    }
}
