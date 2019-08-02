package com.xumingxing.webpack.userfunction.webRouter;

import com.xumingxing.webpack.userfunction.handler.DictionaryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/3110:40
 * Description: Version 1.0
 * Location: webpack
 */
@Component
public class DictionaryRouter {
    @Autowired
    private DictionaryHandler dictionaryHandler;

    @Bean
    public RouterFunction<ServerResponse> mongoRouterFunction() {
        return
                /**
                 * GET请求
                 * 根据id获取user信息
                 * 返回查询信息
                 *
                 * 徐明星 2019
                 * xumingxing 2019
                 *
                 */
                route(GET("/dictionary/getone/{id}"), dictionaryHandler::getOne)

                        /**
                         * GET请求
                         * 获取所有user信息
                         * 返回查询信息
                         */
                        .andRoute(GET("/dictionary/getall"), dictionaryHandler::getAll)
                        /**
                         * GET请求
                         * 根据id查询两个user
                         * 返回sort
                         */
                            .andRoute(GET("/dictionary/setexchange"), dictionaryHandler::setExchange)
                        /**
                         * POST请求
                         * 以json添加用户信息
                         * JSON格式
                         * 返回状态信息
                         */
                        .andRoute(POST("/dictionary/addjson").and(accept(APPLICATION_JSON_UTF8)).and(contentType(APPLICATION_JSON_UTF8)), dictionaryHandler::addJson)
                        /**
                         * PUT请求
                         * 以json修改用户信息
                         * 返回状态信息
                         */
                        .andRoute(PUT("/dictionary/updatejson").and(accept(APPLICATION_JSON_UTF8)), dictionaryHandler::updateJson)
                        /**
                         * DELETE请求
                         * 逻辑删除
                         * 返回状态信息
                         */
                        .andRoute(DELETE("/dictionary/ldelete/{id}").and(accept(APPLICATION_JSON_UTF8)), dictionaryHandler::logicalDelete)
                        /**
                         * GET请求
                         * t同步阻塞分页查询
                         * 返回查询信息
                         */
                        .andRoute(GET("/dictionary/querysyn").and(accept(APPLICATION_JSON_UTF8)), dictionaryHandler::querySynchronization)
                        /**
                         * GET请求
                         * 异步分页查询
                         * 返回查询信息
                         */
                        .andRoute(GET("/dictionary/queryasyn").and(accept(APPLICATION_JSON_UTF8)), dictionaryHandler::queryAsynchronous)
                ;
    }
}
