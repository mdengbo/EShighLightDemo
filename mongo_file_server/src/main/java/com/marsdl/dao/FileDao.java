package com.marsdl.dao;

import com.marsdl.entity.File;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class FileDao {

    @Resource
    MongoTemplate mongoTemplate;

    private Logger log = LogManager.getLogger(FileDao.class);

    public File find(String _id) {
        log.info(" " );
        Query query = new Query();
        if(StringUtils.isNotBlank(_id)) {
            query.addCriteria(Criteria.where("_id").is(new ObjectId(_id)));
        }
        return mongoTemplate.findOne(query, File.class);
    }

    public void save(File file) {
        mongoTemplate.save(file);
    }

}
