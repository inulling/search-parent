package net.vipmro.search.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author fengxiangyang
 * @date 2018/6/12
 */

@Configuration
@ComponentScan(basePackageClasses = EsClientSpringFactory.class)
@ConditionalOnClass(value = {EsHandler.class})
public class EsConfig {

    @Value("${es.urls}")
    private String[] urls;
    @Value("${es.connectNum}")
    private Integer connectNum = 20;
    @Value("${es.connectPerRoute}")
    private Integer connectPerRoute = 10;

    @Bean
    public HttpHost[] httpHost() {
        return Arrays.stream(urls).map(HttpHost::create).collect(Collectors.toList()).toArray(new HttpHost[]{});
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public EsClientSpringFactory getFactory() {
        return EsClientSpringFactory.
                build(httpHost(), connectNum, connectPerRoute);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient() {
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRestHighLevelClient() {
        return getFactory().getRestHighLevelClient();
    }

    @Bean
    @ConditionalOnMissingBean(EsHandler.class)
    public EsHandler esHandler(){
        return new EsHandler();
    }

}
