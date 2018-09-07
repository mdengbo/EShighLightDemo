package com.marsdl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.marsdl.dao"},mongoTemplateRef = "mongoDBTemplate_service")
public class MongoTemplate_service {
    @Autowired
    private MongoClient_service mongoClient;

    @Bean(name = "mongoDBTemplate_service")
    public MongoTemplate listTemplate() throws Exception {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient.createClient(), mongoClient.getDbname());

        MongoMappingContext context = new MongoMappingContext();
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(mongoDbFactory, converter);
    }

}
