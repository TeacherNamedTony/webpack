package com.xumingxing.webpack.userfunction.repository;

import com.xumingxing.webpack.userfunction.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2914:49
 * Description: Version 1.0
 * Location: webpack
 */
@Repository
public interface UserReactiveRepository extends ReactiveMongoRepository<User,String> {
}
