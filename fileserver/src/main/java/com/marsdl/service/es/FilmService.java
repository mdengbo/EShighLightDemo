package com.marsdl.service.es;

import com.marsdl.config.es.EsConfig;
import com.marsdl.dao.es.FilmDao;
import com.marsdl.entity.es.FilmEntity;
import org.dom4j.Entity;
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
    private FilmDao filmDao;

    @Autowired
    EsConfig esConfig;

    public void save(FilmEntity filmEntity) {
        filmDao.save(filmEntity);
    }

    /**
     * 拼接搜索条件
     * @return
     */
    public List<FilmEntity> search(String name, String director) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("name",  name))
                .build();
       List<FilmEntity> list = filmDao.search(searchQuery).getContent();
        return list;
    }

    /**
     * <p>高亮查询内容, query的值查询两个字段name, director。当然了你可以配置查询更多个字段或者你可以改成你所需查询的字段</p>
     * @param query
     * @return List<FilmEntity> list
     */
    public List<FilmEntity> search(String query) {
        Client client = esConfig.esTemplate();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //高亮显示规则
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        //指定高亮字段
        highlightBuilder.field("name");
        highlightBuilder.field("director");
        //添加查询的字段内容
        String [] fileds = {"name", "director"};
        QueryBuilder matchQuery = QueryBuilders.multiMatchQuery(query, fileds);
        //搜索数据
        SearchResponse response = client.prepareSearch("chenrui")
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .execute().actionGet();

        SearchHits searchHits = response.getHits();
        System.out.println("记录数-->"+searchHits.getTotalHits());

        //List<String> list = new ArrayList<>();
        List<FilmEntity> list = new ArrayList<>();

        for(SearchHit hit : searchHits) {
            FilmEntity entity = new FilmEntity();
            Map<String, Object> entityMap = hit.getSourceAsMap();
            //高亮字段
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("name"))) {
                Text[] text = hit.getHighlightFields().get("name").getFragments();
                entity.setName(text[0].toString());
                entity.setDirector(String.valueOf(entityMap.get("director")));
            }
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("director"))) {
                Text[] text = hit.getHighlightFields().get("director").getFragments();
                entity.setDirector(text[0].toString());
                entity.setName(String.valueOf(entityMap.get("name")));
            }

            //map to object
            if(!CollectionUtils.isEmpty(entityMap)) {
                if(!StringUtils.isEmpty(entityMap.get("id"))) {
                    entity.setId(Long.valueOf(String.valueOf(entityMap.get("id"))));
                }
                if(!StringUtils.isEmpty(entityMap.get("language"))) {
                    entity.setLanguage(String.valueOf(entityMap.get("language")));
                }
            }
            list.add(entity);
        }
        return list;
    }


}
