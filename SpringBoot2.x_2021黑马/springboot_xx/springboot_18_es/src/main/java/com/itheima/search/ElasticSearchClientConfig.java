package com.itheima.search;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/19 14:32
 */
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Elasticsearch的配置类【配置es链接】
@Configuration
public class ElasticSearchClientConfig {
    // 这一段感觉应该是初始化了一个 RequestOptions 变量
    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    /**
     * 无账号密码登录
     * @return RestHighLevelClient
     */
    @Bean
    public static RestHighLevelClient restHighLevelClient() {
        // 集群配置法
        RestHighLevelClient client = new RestHighLevelClient
                (RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        return client;
    }
}

