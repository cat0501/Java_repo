package com.itheima.search;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/19 14:33
 */

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@EnableScheduling // 定时器
@Component
public class ElasticsearchImpl {
    @Autowired
    @Resource
    // 这个是要被同步的数据表所对应的Mapper
    private PogOfficialInfoMapper pogOfficialInfoMapper;

    // 定时器注解，每过去5秒执行这个方法
    @Scheduled(cron = "0/5 * * * * ?")
    public void Escalculating() {
        // 查询 tb_newbee_mall_goods_category 表中的所有数据，待会这些数据全部放在 es 里
        List<GoodsCategory> categoryList = pogOfficialInfoMapper.selectList(null);

        // 调用高层对象
        // ElasticSearchClientConfig 为之前写的 Elasticsearch 配置类，restHighLevelClient 是其中的静态方法
        RestHighLevelClient client = ElasticSearchClientConfig.restHighLevelClient();

        // 存储刚刚 categoryList 里获得的数据
        for (GoodsCategory category : categoryList) {
            // 创建构造器指定index索引
            IndexRequest indexRequest = new IndexRequest("officialinfo");  // 索引的名字
            indexRequest.id(category.getCategoryId().toString());

            // 创建批量操作的对象
            BulkRequest request = new BulkRequest();

            // 将查询到的数据转化为Json
            indexRequest.source(JSONObject.toJSONString(category), XContentType.JSON);
            // Json数据放入批量操作对象中
            request.add(indexRequest);

            // 执行操作
            try {
                client.bulk(request, ElasticSearchClientConfig.COMMON_OPTIONS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(category);
        }
    }
}
