package com.marsdl.service.es;

import com.marsdl.config.es.EsConfig;
import com.marsdl.entity.es.User;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-13
 */
@Service
public class FilmService {

    @Autowired
    EsConfig esConfig;

    /**
     * <p>高亮查询内容, query的值查询两个字段name, director。当然了你可以配置查询更多个字段或者你可以改成你所需查询的字段</p>
     * @param query
     * @return List<FilmEntity> list
     */
    public List<User> search(String query) {
        Client client = esConfig.esTemplate();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //高亮显示规则
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        //指定高亮字段
        highlightBuilder.field("username");
        highlightBuilder.field("jiguan");
        //添加查询的字段内容
        String [] fileds = {"username", "biyezhongxue"};
        QueryBuilder matchQuery = QueryBuilders.multiMatchQuery(query, fileds);
        //搜索数据
        SearchResponse response = client.prepareSearch("my-application")
                .setQuery(matchQuery)
                //设置索引
                .setIndices("user2_index")
                //设置类型
                .setTypes("user2_type")
                .highlighter(highlightBuilder)
                .execute().actionGet();

        SearchHits searchHits = response.getHits();
        System.out.println("记录数-->"+searchHits.getTotalHits());

        //List<String> list = new ArrayList<>();
        List<User> list = new ArrayList<>();

        for(SearchHit hit : searchHits) {
            User entity = new User();
            Map<String, Object> entityMap = hit.getSourceAsMap();
            //高亮字段
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("jiguan"))) {
                Text[] text = hit.getHighlightFields().get("jiguan").getFragments();
                entity.setUsername(text[0].toString());
                entity.setJiguan(String.valueOf(entityMap.get("jiguan")));
            }
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("username"))) {
                Text[] text = hit.getHighlightFields().get("username").getFragments();
                entity.setBranch(text[0].toString());
                entity.setUsername(String.valueOf(entityMap.get("username")));
            }

            //map to object
            if(!CollectionUtils.isEmpty(entityMap)) {
                if(!StringUtils.isEmpty(entityMap.get("userid"))) {
                    entity.setUserid(Integer.valueOf(String.valueOf(entityMap.get("userid"))));
                }
                if(!StringUtils.isEmpty(entityMap.get("biyezhongxue"))) {
                    entity.setBiyezhongxue(String.valueOf(entityMap.get("biyezhongxue")));
                }
            }
            list.add(entity);
        }
        return list;
    }


}
