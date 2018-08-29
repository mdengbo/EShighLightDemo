package com.marsdl.dao.es;

import com.marsdl.entity.es.FilmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-13
 */
public interface FilmDao extends ElasticsearchRepository<FilmEntity, Long>{


    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}},\"highlight\": {\n" +
            "\"fields\" : {\n" +
            "\"name\" : {}\n" +
            "}\n" +
            "}")
    List<Object> findByAuthorsNameUsingCustomQuery(String name);

}
