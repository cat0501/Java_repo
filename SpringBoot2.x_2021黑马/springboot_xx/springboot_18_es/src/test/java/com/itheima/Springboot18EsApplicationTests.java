package com.itheima;

import com.alibaba.fastjson.JSON;
import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import com.itheima.test.User;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Springboot18EsApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ElasticsearchRestTemplate template;


    @BeforeEach
    void setUp() {
        HttpHost host = HttpHost.create("http://localhost:9200");
        RestClientBuilder builder = RestClient.builder(host);
        client = new RestHighLevelClient(builder);
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }

    private RestHighLevelClient client;

//    @Test
//    void testCreateClient() throws IOException {
//        HttpHost host = HttpHost.create("http://localhost:9200");
//        RestClientBuilder builder = RestClient.builder(host);
//        client = new RestHighLevelClient(builder);
//
//        client.close();
//    }

    /**
     * @description ????????????
     * @author Lemonade
     * @param:
     * @updateTime 2022/8/19 9:50
     */
    @Test
    void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("books");
        //???????????????????????????????????????????????????????????????
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * @description ????????????
     * @author Lemonade
     * @param:
     * @updateTime 2022/8/19 9:50
     */
    @Test
    void testCreateIndexByIK() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("books");
        String json = "{\n" +
                "    \"mappings\":{\n" +
                "        \"properties\":{\n" +
                "            \"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "            },\n" +
                "            \"name\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"type\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "            },\n" +
                "            \"description\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        //????????????????????????
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    //????????????
    void testCreateDoc() throws IOException {
        Book book = bookDao.selectById(1);
        IndexRequest request = new IndexRequest("books").id(book.getId().toString());
        String json = JSON.toJSONString(book);
        request.source(json,XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }

    @Test
    //????????????
    void testCreateDocAll() throws IOException {
        List<Book> bookList = bookDao.selectList(null);
        BulkRequest bulk = new BulkRequest();
        for (Book book : bookList) {
            IndexRequest request = new IndexRequest("books").id(book.getId().toString());
            String json = JSON.toJSONString(book);
            request.source(json,XContentType.JSON);
            bulk.add(request);
        }
        client.bulk(bulk,RequestOptions.DEFAULT);
    }

    @Test
    //???id??????
    void testGet() throws IOException {
        GetRequest request = new GetRequest("books","1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        System.out.println(json);
    }

    @Test
    //???????????????
    void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("books");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("all","spring"));
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            //System.out.println(source);
            Book book = JSON.parseObject(source, Book.class);
            System.out.println(book);
        }
    }







    // ????????????????????????????????????
    @Test
    public void getTest() throws Exception {
        // 1,????????????
        GetRequest getRequest = new GetRequest("employee", "2");
        // 2,????????????
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        // 3,????????????
        if (getResponse.isExists()){
            System.out.println(getResponse.getSourceAsString());//???String????????????
        }

        // {"id":"1","name":"??????","department":{"id":"1","deptName":"?????????","describe":"????????????????????????"}}
        // {"name":"??????","id":"8"}
    }

    //????????????
    @Test
    public void insertTest() throws Exception {
        // 1???????????????
        IndexRequest indexRequest = new IndexRequest("employee");
        indexRequest.id("8");
        Map<String, String> insertInfo = new HashMap<>();
        insertInfo.put("id", "8");
        insertInfo.put("name", "??????");
        indexRequest.source(JSON.toJSONString(insertInfo), XContentType.JSON);
        // 2???????????????
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        // 3?????????????????????
        System.out.println("ID: " + indexResponse.getId() + ", RESULT: " + indexResponse.getResult());

        // ID: 8, RESULT: CREATED
    }

    //????????????
    @Test
    public void updateTest() throws Exception {
        // 1???????????????
        UpdateRequest updateRequest = new UpdateRequest("employee", "8");
        //updateRequest.type("_doc");
        Map<String, String> updateInfo = new HashMap<>();
        updateInfo.put("name", "????????????");
        updateRequest.doc(JSON.toJSONString(updateInfo), XContentType.JSON);

        //updateRequest.docAsUpsert(true);

        //// ??????????????????
        //updateRequest.timeout("1s");
        //// ??????????????????
        //updateRequest.retryOnConflict(3);

        // 2???????????????
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        // 3?????????????????????
        System.out.println("ID: " + updateResponse.getId() + ", RESULT: " + updateResponse.getResult());

    }

    // ????????????
    @Test
    public void deleteTest() throws Exception {
        // 1,????????????
        DeleteRequest deleteRequest = new DeleteRequest("employee", "8");
        // 2,????????????
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        // 3,????????????
        System.out.println("ID: " + deleteResponse.getId() + ", RESULT: " + deleteResponse.getResult());
        // ID: 8, RESULT: DELETED

        // {"id":"1","name":"??????","department":{"id":"1","deptName":"?????????","describe":"????????????????????????"}}
        // {"name":"??????","id":"8"}
    }



    @Test
    public void bulkTest() throws Exception{
        // 1,????????????
        BulkRequest bulkRequest = new BulkRequest();
        Map<String, String> insertInfo = new HashMap<>();
        insertInfo.put("id", "10");
        insertInfo.put("name", "?????????");

        System.out.println("--------------------------" + JSON.toJSONString(insertInfo));

        bulkRequest.add(new IndexRequest("post").id("900").source(JSON.toJSONString(insertInfo), XContentType.JSON));
        bulkRequest.add(new IndexRequest("employee").id("800").source(JSON.toJSONString(insertInfo), XContentType.JSON));

        // 2,????????????
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 3,????????????
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse response = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()){
                case INDEX:
                    IndexResponse indexResponse = (IndexResponse) response;
                    System.out.println("INDEX:" + indexResponse.getResult());
                    break;
                case CREATE:
                    IndexResponse createResponse = (IndexResponse) response;
                    System.out.println("CREATE:" + createResponse.getResult());
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) response;
                    System.out.println("UPDATE:" + updateResponse.getResult());
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) response;
                    System.out.println("DELETE:" + deleteResponse.getResult());
                    break;
            }
        }

        // INDEX:CREATED
        // INDEX:CREATED
        // INDEX:CREATED
    }

}
