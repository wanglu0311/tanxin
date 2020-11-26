package com.tanxin.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName TestSearch
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/25 15:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    private RestClient restClient;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 查询所有
    @Test
    public void testSearchAll() throws IOException, ParseException {

        // 对应的目标
        SearchRequest searchRequest = new SearchRequest("tx_course");

        //TODO 设置类型
        searchRequest.types("doc");

        // 搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //TODO 设置分页参数
//        searchSourceBuilder.from(0);  // 起始位置下标 为当前页-1的差乘页数
//        searchSourceBuilder.size(2);

        //TODO 设置搜索方式
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery()); // 全部
//        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring")); // name中包含spring
//        String[] ids = new String[]{"1","2","3"};
//        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",ids)); // 根据id查询
//        searchSourceBuilder.query(QueryBuilders.matchQuery("description","spring开发框架").minimumShouldMatch("80%")); // 全文查询
//        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css","name","description").minimumShouldMatch("50%").field("name",10)); // 搜索多个域 name权重提升10倍

        //TODO 搜索多个域
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("开发框架", "name", "description").minimumShouldMatch("50%").field("name", 10);
        //TODO term Query
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel","201001");
        //TODO bool Query
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
//        boolQueryBuilder.must(termQueryBuilder);
        //TODO filter过滤器
//        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));

        // 放入searchSourceBuilder
        searchSourceBuilder.query(boolQueryBuilder);

        //TODO 添加排序
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.ASC);

//        //TODO 设置源字段过滤,第一个包括哪些,第二个不包括哪些
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp","description"},new String[]{});

        //TODO 设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");
        highlightBuilder.postTags("</tag>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);

        //TODO 向搜索对象中设置搜索源
        searchRequest.source(searchSourceBuilder);

        // 执行搜索,向es发起http请求
        SearchResponse search = highLevelClient.search(searchRequest);

        // 搜索的结果
        SearchHits hits = search.getHits();

        // 匹配到的总记录数
        long totalHits = hits.getTotalHits();

        // 得到匹配度高的文档
        SearchHit[] hits1 = hits.getHits();

        synchronized (hits1){
            for (SearchHit hit : hits1) {

                // 主键
                String id = hit.getId();

                // 源文档内容
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String name = (String) sourceAsMap.get("name");

                // 前面设置源文档过滤,其他字段无法获取
                String desc = (String) sourceAsMap.get("description");

                String studymodel = (String) sourceAsMap.get("studymodel");

                Double price = (Double) sourceAsMap.get("price");

                Date datestamp = dateFormat.parse( (String) sourceAsMap.get("timestamp"));

                // 取出高亮字段
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields!=null) {
                    // 取出name高亮字段
                    HighlightField name1 = highlightFields.get("name");
                    if (name1!=null) {
                        Text[] fragments = name1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Text text : fragments) {
                            stringBuffer.append(text);
                        }
                        name = stringBuffer.toString();
                    }
                }
                System.out.println("datestamp = " + datestamp);
                System.out.println("name = " + name);
                System.out.println("studymodel = " + studymodel);
                System.out.println("price = " + price);
                System.out.println("desc = " + desc);
                try {
                    Thread.sleep(50);
                    System.err.println("======================================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }


    }

}
