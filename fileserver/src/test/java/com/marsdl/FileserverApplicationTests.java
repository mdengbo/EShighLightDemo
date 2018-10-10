package com.marsdl;

import com.marsdl.config.es.EsConfig;
import com.marsdl.dao.es.FilmDao;
import com.marsdl.entity.es.FilmEntity;
import com.marsdl.service.es.FilmService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileserverApplication.class)
public class FileserverApplicationTests {

	@Autowired
	FilmDao filmDao;

	@Autowired
	FilmService filmService;

	@Autowired
	EsConfig config;

	@Test
	public void testFilmService() {
		FilmEntity filmEntity = new FilmEntity();
		filmEntity.setName("test, you are great bang，好好学习，天天上上");
		filmEntity.setDirector("你才");
		filmService.save(filmEntity);
	}

	@Test
	public void testSearch() {
		String name = "少女";
		/*List<FilmEntity> film = filmService.search(name);
		for (FilmEntity entity : film) {
			System.out.println(entity.getName());
		}*/
	}

	@Test
	public void testSearchDirect() {
		String name = "坠落";
		String director = "李佳隆";

	}


	@Test
	public void testFilm() {
		// 设置集群名称
		Settings settings = Settings.builder()
				.put("cluster.name", "chenrui").build();
		// 创建client
		TransportClient client = null;
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(
							InetAddress.getByName("127.0.0.1"), 9300));
		} catch (Exception e) {
			e.printStackTrace();
		}

		QueryBuilder matchQuery = QueryBuilders.matchQuery("name", "少女");
		HighlightBuilder hiBuilder=new HighlightBuilder();
		hiBuilder.preTags("<h2>");
		hiBuilder.postTags("</h2>");
		hiBuilder.field("name");
		// 搜索数据
		SearchResponse response = client.prepareSearch("chenrui")
				.setQuery(matchQuery)
				.highlighter(hiBuilder)
				.execute().actionGet();
		//获取查询结果集
		SearchHits searchHits = response.getHits();
		System.out.println("共搜到:"+searchHits.getTotalHits()+"条结果!");
		//遍历结果
		/*for(SearchHit hit:searchHits){

			System.out.println("String方式打印文档搜索内容:");
			System.out.println(hit.getSourceAsString());
			System.out.println("Map方式打印高亮内容");
			System.out.println(hit.getHighlightFields());

			System.out.println("遍历高亮集合，打印高亮片段:");
			Text[] text = hit.getHighlightFields().get("name").getFragments();
			for (Text str : text) {
				System.out.println(str.string());
			}

		}*/
	}

	@Test
	public void testConfig() {
		Client client = config.esTemplate();
		QueryBuilder matchQuery = QueryBuilders.matchQuery("name", "少女");

		HighlightBuilder hiBuilder=new HighlightBuilder();
		hiBuilder.preTags("<em>");
		hiBuilder.postTags("</em>");
		hiBuilder.field("name");
		// 搜索数据
		SearchResponse response = client.prepareSearch("chenrui")
				.setQuery(matchQuery)
				.highlighter(hiBuilder)
				.execute().actionGet();
		//获取查询结果集
		SearchHits searchHits = response.getHits();

		System.out.println(" 所有记录数："+searchHits.getTotalHits());
		//遍历结果
		for(SearchHit hit:searchHits){

			System.out.print("遍历高亮集合--->");
			Text[] text = hit.getHighlightFields().get("name").getFragments();
			for (Text str : text) {
				System.out.println(str.string());
			}

		}

		Client client1 = config.esTemplate();
		QueryBuilder matchQuery1 = QueryBuilders.matchQuery("name", "体面");
		SearchResponse response1 = client1.prepareSearch("chenrui")
				.setQuery(matchQuery1)
				.highlighter(hiBuilder)
				.execute().actionGet();
		SearchHits searchHits1 = response1.getHits();
		System.out.println("第二边遍历结果");
		for(SearchHit hit:searchHits1){
			Text[] text = hit.getHighlightFields().get("name").getFragments();
				for (Text str : text) {
				System.out.println(str.string());
			}

		}

	}

	@Test
	public void testSearchEs() {
		/*List<String> param = filmService.search("少女");
		for(String str : param) {
			System.out.println(str);
		}*/


	}

}
