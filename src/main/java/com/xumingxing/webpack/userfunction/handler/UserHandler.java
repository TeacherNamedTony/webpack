package com.xumingxing.webpack.userfunction.handler;

import com.xumingxing.webpack.userfunction.common.PageEntity;
import com.xumingxing.webpack.userfunction.common.ResponseEntity;
import com.xumingxing.webpack.userfunction.enumeration.ResponseStatus;
import com.xumingxing.webpack.userfunction.pojo.User;
import com.xumingxing.webpack.userfunction.repository.UserReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2914:41
 * Description: Version 1.0
 * Location: webpack
 */
@Service
public class UserHandler {
    @Autowired
    private UserReactiveRepository userReactiveRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * GET请求方式
     * 查询单个
     * 路径取参数 pathVariable("id")
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getOne(ServerRequest serverRequest) {
        return ServerResponse
                //状态200
                .status(HttpStatus.OK)
                //contentType类型指定
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //读取内容
                .body(Mono.just(serverRequest.pathVariable("id"))
                                //根据id findbyid查找
                                .flatMap(id -> userReactiveRepository.findById(id))
                                .map(user -> ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS, user))
                        , String.class);
    }

    /**
     * GET请求方式
     * 查询所有
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {

        return ServerResponse
                //状态200
                .status(HttpStatus.OK)
                //contentType类型指定
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //读取内容
                .body(reactiveMongoTemplate.findAll(User.class)
                        //接受查找的所有数据
                        .reduce(new ArrayList<User>(), (users, user) -> {
                            users.add(user);
                            return users;
                        })
                        .map(users -> ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS, users)), String.class);
    }

    /**
     * POST请求方式
     * 添加信息 JSON格式
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> addJson(ServerRequest serverRequest) {
        return ServerResponse
                //状态200
                .status(HttpStatus.OK)
                //contentType类型指定
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //读取内容
                .body(serverRequest.bodyToMono(User.class).flatMap(user -> {
                    //设置id
                    user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    //设置操作时间
                    user.setOperateTime(LocalDateTime.now());
                    //插入消费
                    return userReactiveRepository.insert(user);
                }).then(Mono.just(ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))), String.class);
    }

    /**
     * POST请求方式
     * 添加信息 FORM格式
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> addForm(ServerRequest serverRequest){

        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(serverRequest.exchange().getFormData().flatMap(stringStringMultiValueMap -> {
                    User user = new User();
                    user.setType(Integer.valueOf(Objects.requireNonNull(stringStringMultiValueMap.getFirst("type"))));
                    user.setContent(stringStringMultiValueMap.getFirst("content"));
                    user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    user.setOperateTime(LocalDateTime.now());
                    return userReactiveRepository.insert(user);
                }).then(Mono.just(ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))), String.class);
    }

    /**
     * PUT请求方式
     * 修改信息 JSON格式
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> updateJson(ServerRequest serverRequest) {

         return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(serverRequest.bodyToMono(User.class)
                        .flatMap(user -> {
                            user.setOperateTime(LocalDateTime.now());
                            return userReactiveRepository.save(user);
                        }).then(Mono.just(ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))),String.class);
    }

    /**
     * POST请求方式
     * 修改信息 FROM格式
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> updateForm(ServerRequest serverRequest) {

        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(serverRequest.exchange().getFormData().flatMap(stringStringMultiValueMap -> {
                    User user = new User();
                    user.setId(stringStringMultiValueMap.getFirst("id"));
                    user.setType(Integer.valueOf(Objects.requireNonNull(stringStringMultiValueMap.getFirst("type"))));
                    user.setContent(stringStringMultiValueMap.getFirst("content"));
                    user.setOperateTime(LocalDateTime.now());
                    return userReactiveRepository.save(user);
                }).then(Mono.just(ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))), String.class);
    }

    /**
     * DELETE请求方式
     * 逻辑删除
     * 0正常 1删除
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> logicalDelete(ServerRequest serverRequest) {

        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(serverRequest.pathVariable("id"))
                                .flatMap(id -> userReactiveRepository.findById(id))
                                .flatMap(user -> {
                                    user.setDelete(1);
                                    return userReactiveRepository.save(user);
                                })
                                .map(user -> ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))
                        ,String.class);
    }
    /**
     * GET请求方式
     * 逻辑删除
     * MultiValueMap<String, String> stringStringMultiValueMap = serverRequest.queryParams();
     * Optional<String> value = serverRequest.queryParam("key")
     * 基于 skip 和 limit 分页
     * 全表扫描，数据量大时性能低
     * 待优化
     * 当前为同步执行
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> selectBlock(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("1"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("5"));
        Query query = new Query();
        serverRequest.queryParam("type").ifPresent(s -> query.addCriteria(Criteria.where("type").is(Integer.parseInt(s))));
        serverRequest.queryParam("content").ifPresent(s -> query.addCriteria(Criteria.where("content").regex(s)));
        long count = mongoTemplate.count(query, User.class);
        query.skip((page - 1) * size).limit(size);
        List<User> examples = mongoTemplate.find(query, User.class);
        PageEntity<Object> pageEntity = PageEntity.generateData(page,size, count,examples);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS,pageEntity)),String.class);

    }

















}
