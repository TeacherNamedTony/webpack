package com.xumingxing.webpack.userfunction.handler;

import com.mongodb.BasicDBObject;
import com.xumingxing.webpack.userfunction.common.PageEntity;
import com.xumingxing.webpack.userfunction.common.ResponseEntity;
import com.xumingxing.webpack.userfunction.enumeration.ResponseStatus;
import com.xumingxing.webpack.userfunction.entity.Dictionary;
import com.xumingxing.webpack.userfunction.repository.DictionaryReactiveRepository;
import com.xumingxing.webpack.userfunction.validation.validate.DictionaryValidate;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

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
     * GET请求方式
     * 查询两个id
     * 路径取参数 pathVariable("id")
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> setExchange(ServerRequest serverRequest) {

        MultiValueMap<String, String> id = serverRequest.queryParams();
        String id1 = id.getFirst("id1");
        String id1_sort = id.getFirst("id1_sort");
        String id2 = id.getFirst("id2");
        String id2_sort = id.getFirst("id2_sort");
        Query query=new Query(Criteria.where("id").is(id1));
        Update update = Update.update("sort",id2_sort);
        mongoTemplate.updateFirst(query, update, Dictionary.class,"dictionary");
        Query query1=new Query(Criteria.where("id").is(id2));
        Update update1 = Update.update("sort",id1_sort);
        mongoTemplate.updateFirst(query1, update1, Dictionary.class,"dictionary");
        return ServerResponse
                //状态200
                .status(HttpStatus.OK)
                //contentType类型指定
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //读取内容
                .body(Mono.just(id)
                        .flatMap(id3 -> dictionaryReactiveRepository.findById(id1))
                        .flatMap(dictionary1 -> {
                            return dictionaryReactiveRepository.save(dictionary1);
                        })
                        .map(dictionary -> ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA)), String.class);
    }

    /**
     * POST请求方式
     * 添加信息 JSON格式
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> addJson(ServerRequest serverRequest) {
        Aggregation aggregation5 = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("classify").is("select")),
                Aggregation.unwind("sort"),
                Aggregation.group("$classify").max("$sort").as("max_sort"));
        AggregationResults<Document> outputTypeCount5 =
                mongoTemplate.aggregate(aggregation5, "dictionary", Document.class);
        Document document = outputTypeCount5.getMappedResults().get(0);
        Integer max_sort = document.getInteger("max_sort");
        System.out.println("当前最大sort为"+max_sort);
        return ServerResponse
                //状态200
                .status(HttpStatus.OK)
                //contentType类型指定
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //读取内容

                .body(serverRequest.bodyToMono(Dictionary.class)
                        .doOnNext(DictionaryValidate::validate)
                        .flatMap(dictionary -> {
                    // 校验器
                    //设置id
                    dictionary.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    //设置操作时间
                    dictionary.setCreateTime(LocalDateTime.now());
                    //设置修改时间
                    dictionary.setReviseTime(LocalDateTime.now());
                    //设置查询后的排序为最大
                    dictionary.setSort(max_sort + 1);
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
                        }).then(Mono.just(ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA))),String.class);
    }

    /**
     * DELETE请求方式
     * 逻辑删除
     * 0正常 1删除
     *
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
                        .map(dictionary -> ResponseEntity.responseToJSONStringNoneData(ResponseStatus.SUCCESS_NONE_DATA)), String.class);
    }

    /**
     * GET请求方式
     * 分页查询
     * MultiValueMap<String, String> stringStringMultiValueMap = serverRequest.queryParams();
     * Optional<String> value = serverRequest.queryParam("key")
     * 基于 skip 和 limit 分页
     * 全表扫描，数据量大时性能低
     * 待优化
     * 当前为同步执行
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> querySynchronization(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("1"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("5"));
        Query query = new Query();
        serverRequest.queryParam("operate").ifPresent(s -> query.addCriteria(Criteria.where("operate").is(Integer.parseInt(s))));
        serverRequest.queryParam("name").ifPresent(s -> query.addCriteria(Criteria.where("name").regex(s)));
        long count = mongoTemplate.count(query, Dictionary.class);
        query.skip((page - 1) * size).limit(size);
        List<Dictionary> examples = mongoTemplate.find(query, Dictionary.class);
        PageEntity<Object> pageEntity = PageEntity.generateData(page, size, count, examples);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS, pageEntity)), String.class);

    }

    /**
     * queryAsynchronous
     * 分页查询
     *
     * @param classify 分类
     * @param page     页码
     * @return {@see ResponseEntity } {@see PageEntity}
     * @author
     * @see PageEntity
     */
    public Mono<ServerResponse> queryAsynchronous(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("1"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("5"));
        Query countQuery = new Query();
        Query dataQuery = new Query().skip((page - 1) * size).limit(size);
        serverRequest.queryParam("operate").ifPresent(s -> {
            countQuery.addCriteria(Criteria.where("operate").is(Integer.parseInt(s)));
            dataQuery.addCriteria(Criteria.where("operate").is(Integer.parseInt(s)));
        });
        serverRequest.queryParam("name").ifPresent(s -> {
            countQuery.addCriteria(Criteria.where("name").regex(s));
            dataQuery.addCriteria(Criteria.where("name").regex(s));
        });
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(PageEntity.generateData())
                        .flatMap(pageEntity -> {
                            pageEntity.setPage(page);
                            pageEntity.setSize(size);
                            Mono.just(dataQuery)
                                    .map(query -> mongoTemplate.find(query, Dictionary.class))
                                    .subscribe(pageEntity::setData);
                            /*Mono.just(countQuery)
                                    .map(query -> mongoTemplate.count(query, Example.class))
                                    .handle((aLong, synchronousSink) -> pageEntity.setTotal(aLong))
                                    .subscribe();*/
                            Mono.just(countQuery)
                                    .map(query -> mongoTemplate.count(query, Dictionary.class))
                                    .subscribe(pageEntity::setTotal);
                            return Mono.just(ResponseEntity.responseToJSONStringWithData(ResponseStatus.SUCCESS, pageEntity));
                        }), String.class);
    }


}
