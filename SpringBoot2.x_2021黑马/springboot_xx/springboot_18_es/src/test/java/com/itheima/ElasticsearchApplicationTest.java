package com.itheima;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/19 14:41
 */
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;



// 参考 ：https://www.cnblogs.com/Muhuai/p/16334246.html
@SpringBootTest
@Slf4j
public class ElasticsearchApplicationTest {
    // 连接客户端
    @Autowired
    RestHighLevelClient client;


    @BeforeEach
    public void init() {
        // 初始化客户端
        log.info("客户端client信息" + client);
        HttpHost serverHost = new HttpHost("localhost",9200);
        client = new RestHighLevelClient(RestClient.builder(serverHost));
    }

    @AfterEach
    public void afterDo() {
        // 销毁客户端 【同上，理论上应该用 @After 注解实现】
        if (client != null) {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testIndex() throws IOException {
        // 验证索引是否正常 - OK
        // 查询索引信息
        GetIndexRequest request = new GetIndexRequest("officialinfo");// officialinfo 为索引名称
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        // 获取别名
        System.out.println(response.getAliases().toString());//{officialinfo=[]}
        // 获取默认设置
        System.out.println(response.getDefaultSettings().toString());//{}
        // 获取索引信息
        System.out.println(Arrays.toString(response.getIndices()));//[officialinfo]
        // 获取映射信息
        System.out.println(response.getMappings().toString());
        //{officialinfo=org.elasticsearch.cluster.metadata.MappingMetadata@65b780fb}
    }

    // 文档查询【查询 id 为 1 的数据】
    @Test
    public void testClientSearchById() throws IOException {
        // 验证文档是否正常 - OK
        GetRequest request = new GetRequest().index("officialinfo").id("20");
        GetResponse response = client.get(request,RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.getSourceAsMap());
    }

    // 模糊查询
    @Test
    public void testClientSearch() throws IOException {
        // 1、创建查询request
        SearchRequest request = new SearchRequest("officialinfo");
        // 这个request.type() 可能因为 Elasticsearch 版本的问题，已经不用了，如果用的话，会报错
        // 【[types removal] Specifying types in search requests is deprecated."]】
        // request.types("text");

        // 2、指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 选择模糊查询匹配的模式是 and 还是 or
        // 也可以不加后面的 .operator(Operator.OR) ，如果不加，就是直接匹配
        builder.query(QueryBuilders.matchQuery("categoryRank","0").operator(Operator.OR));
        request.source(builder);
        // 3、执行查询
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);

        // 结果打印【为什么用 Hit ，需要去看 Elasticsearch 查询的结果，看结果很容易就明白了】
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("------------------------------hit--------------------------------");
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }

        System.out.println("------------------------------hit.getSourceAsMap()--------------------------------");
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}

