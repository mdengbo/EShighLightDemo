package com.marsdl.service.es;

import com.marsdl.config.es.EsConfig;
import com.marsdl.dao.es.FilmDao;
import com.marsdl.entity.es.FilmEntity;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    public List<FilmEntity> search(String name) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("name",  name))
                .build();
       List<FilmEntity> list = filmDao.search(searchQuery).getContent();
        return list;
    }

    /**
     * 拼接搜索条件
     * @return
     */
    public List<String> search(String query, String director) {

        Client client = esConfig.esTemplate();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("name");
        highlightBuilder.field("director");

        String [] fileds = {"name", "director"};
        QueryBuilder matchQuery = QueryBuilders.multiMatchQuery(query, fileds);
        //搜索数据
        SearchResponse response = client.prepareSearch("chenrui")
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .execute().actionGet();

        SearchHits searchHits = response.getHits();
        System.out.println("记录数-->"+searchHits.getTotalHits());

        List<String> list = new ArrayList<>();

        for(SearchHit hit : searchHits) {
            Text[] text = null;
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("name"))) {
                text = hit.getHighlightFields().get("name").getFragments();
                for(Text str : text) {
                    System.out.println("name--->"+str.toString());
                }
            }
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("director"))) {
                text = hit.getHighlightFields().get("director").getFragments();

                for(Text str : text) {
                    System.out.println("director--->"+str.toString());
                }

            }
            if(!StringUtils.isEmpty(text)) {
                for(Text str : text) {
                    list.add(str.toString());
                }
            }
        }
        return list;
    }


}
