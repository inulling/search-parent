package net.vipmro.search.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Es客户端工厂
 *
 * @author fengxiangyang
 * @date 2018/6/12
 */
public class EsClientSpringFactory {
    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
    public static int MAX_CONN_PER_ROUTE = 10;
    public static int MAX_CONN_TOTAL = 30;

    private static HttpHost[] HTTP_HOST;
    private RestClientBuilder builder;
    private RestClient restClient;
    private RestHighLevelClient restHighLevelClient;

    private static EsClientSpringFactory esClientSpringFactory = new EsClientSpringFactory();

    private EsClientSpringFactory() {
    }

    public static EsClientSpringFactory build(HttpHost[] httpHost,
                                              Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOST = httpHost;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return esClientSpringFactory;
    }

    public static EsClientSpringFactory build(HttpHost[] httpHost, Integer connectTimeOut, Integer socketTimeOut,
                                              Integer connectionRequestTime, Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOST = httpHost;
        CONNECT_TIMEOUT_MILLIS = connectTimeOut;
        SOCKET_TIMEOUT_MILLIS = socketTimeOut;
        CONNECTION_REQUEST_TIMEOUT_MILLIS = connectionRequestTime;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return esClientSpringFactory;
    }


    public void init() {
        builder = RestClient.builder(HTTP_HOST);
        setMutiConnectConfig();
        setConnectTimeOutConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
    }

    /**
     * 配置连接时间延时
     */
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    /**
     * 使用异步httpclient时设置并发连接数
     */
    public void setMutiConnectConfig() {
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            return httpClientBuilder;
        });
    }

    public RestClient getClient() {
        return restClient;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("close client");
    }


}
