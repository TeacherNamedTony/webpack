package com.xumingxing.webpack.userfunction.handler;

import com.xumingxing.webpack.userfunction.common.ResponseEntity;
import com.xumingxing.webpack.userfunction.enumeration.ResponseStatus;
import com.xumingxing.webpack.userfunction.entity.Dictionary;
import com.xumingxing.webpack.userfunction.repository.DictionaryReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/3110:49
 * Description: Version 1.0
 * Location: webpack
 */
@Service

public class DictionaryHandler {

    @Autowired
    private DictionaryReactiveRepository dictionaryReactiveRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * GET请求方式
     * 查询单个
     * 路径取参数 pathVariable("id")
     *
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
                                .flatMap(id -> dictionaryReactiveRepository.findById(id))
                                .map(dictionary -> ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS, dictionary))
                        , String.class);
    }

    /**
     * GET请求方式
     * 查询所有
     *
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
                .body(reactiveMongoTemplate.findAll(Dictionary.class)
                        //接受查找的所有数据
                        .reduce(new ArrayList<Dictionary>(), (dictionaries, dictionary) -> {
                            dictionaries.add(dictionary);
                            return dictionaries;
                        })
                        .map(dictionaries -> ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS, dictionaries)), String.class);
    }

    /**
     * POST请求方式
     * 添加信息 JSON格式
     *
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
                .body(serverRequest.bodyToMono(Dictionary.class).flatMap(dictionary -> {
                    //设置id
                    dictionary.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    //设置操作时间
                    dictionary.setCreateTime(LocalDateTime.now());
                    //插入消费
                    return dictionaryReactiveRepository.insert(dictionary);
                }).then(Mono.just(ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))), String.class);
    }

    /**
     * PUT请求方式
     * 修改信息 JSON格式
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> updateJson(ServerRequest serverRequest) {

        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(serverRequest.bodyToMono(Dictionary.class)
                        .flatMap(dictionary -> {
                            dictionary.setReviseTime(LocalDateTime.now());
                            return dictionaryReactiveRepository.save(dictionary);
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
                                .flatMap(id -> dictionaryReactiveRepository.findById(id))
                                .flatMap(dictionary -> {
                                    dictionary.setDataStatus(1);
                                    return dictionaryReactiveRepository.save(dictionary);
                                })
                                .map(dictionary -> ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))
                        , String.class);
    }


}
