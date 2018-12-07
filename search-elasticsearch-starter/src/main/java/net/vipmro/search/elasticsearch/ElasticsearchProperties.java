package net.vipmro.search.elasticsearch;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ES配置信息
 * @author fengxiangyang
 * @date 2017/11/30
 */
@ConfigurationProperties("elasticsearch")
public class ElasticsearchProperties {
    private String[] urls;
    private Integer connectNum = 20;
    private Integer connectPerRoute = 10;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public Integer getConnectNum() {
        return connectNum;
    }

    public void setConnectNum(Integer connectNum) {
        this.connectNum = connectNum;
    }

    public Integer getConnectPerRoute() {
        return connectPerRoute;
    }

    public void setConnectPerRoute(Integer connectPerRoute) {
        this.connectPerRoute = connectPerRoute;
    }

}
