package com.xumingxing.webpack.userfunction.repository;

import com.xumingxing.webpack.userfunction.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/3110:38
 * Description: Version 1.0
 * Location: webpack
 */
@Repository
public interface DictionaryRepository extends MongoRepository<User,String> {
}