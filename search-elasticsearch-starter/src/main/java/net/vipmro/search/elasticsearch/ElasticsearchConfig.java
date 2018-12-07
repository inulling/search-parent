package net.vipmro.search.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ES配置
 * @author fengxiangyang
 * @date 2017/11/30
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
@ComponentScan(basePackageClasses=ElasticsearchClientFactory.class)
@ConditionalOnClass(value = ElasticsearchHandler.class)
public class ElasticsearchConfig {

    @Autowired
    private ElasticsearchProperties properties;

    @Bean
    public HttpHost[] httpHost(){
        return Arrays.stream(properties.getUrls()).map(HttpHost::create).collect(Collectors.toList()).toArray(new HttpHost[]{});
    }

    @Bean(initMethod="init", destroyMethod="close")
    public ElasticsearchClientFactory getFactory(){
        return ElasticsearchClientFactory.
                build(httpHost(), properties.getConnectNum(), properties.getConnectPerRoute());
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient(){
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRestHighLevelClient(){
        return getFactory().getRestHighLevelClient();
    }

    @Bean
    @ConditionalOnMissingBean(ElasticsearchHandler.class)
    public ElasticsearchHandler esHandler(){
        return new ElasticsearchHandler();
    }

}
