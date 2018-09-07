package com.marsdl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mongodb.service")
public class MongoClient_service extends MongoClientFactory {

    protected String dbname = "";

    public String getDbname() {
        return this.dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

}
