package com.xumingxing.webpack.userfunction.webRouter;

import com.xumingxing.webpack.userfunction.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2914:40
 * Description: Version 1.0
 * Location: webpack
 */
@Component
public class UserRouter {
    @Autowired
    private UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> mongoRouterFounction() {
        return
                /**
                 * GET请求
                 * 根据id获取user信息
                 * 返回查询信息
                 */
                route(GET("/user/getone/{id}"), userHandler::getOne)

                        /**
                         * GET请求
                         * 获取所有user信息
                         * 返回查询信息
                         */
                        .andRoute(GET("/user/getall"), userHandler::getAll)

                        /**
                         * POST请求
                         * 以json添加用户信息
                         * JSON格式
                         * 返回状态信息
                         */
                        .andRoute(POST("/user/addjson").and(accept(APPLICATION_JSON_UTF8)).and(contentType(APPLICATION_JSON_UTF8)), userHandler::addJson)

                        /**
                         * POST请求
                         * 以form添加用户信息
                         * 返回状态信息
                         */
                        .andRoute(POST("/user/addform").and(accept(APPLICATION_FORM_URLENCODED)), userHandler::addForm)


                        /**
                         * PUT请求
                         * 以json修改用户信息
                         * 返回状态信息
                         */
                        .andRoute(PUT("/user/updatejson").and(accept(APPLICATION_JSON_UTF8)), userHandler::updateJson)

                        /**
                         * POST请求
                         * 以form修改用户信息
                         * 返回状态信息
                         */
                        .andRoute(POST("/user/updateform").and(accept(APPLICATION_FORM_URLENCODED)), userHandler::updateForm)

                        /**
                         * DELETE请求
                         * 逻辑删除
                         * 返回状态信息
                         */
                        .andRoute(DELETE("/user/ldelete/{id}").and(accept(APPLICATION_JSON_UTF8)), userHandler::logicalDelete)

                        /**
                         * GET请求
                         * 分页查询
                         * 返回查询信息
                         */
                        .andRoute(GET("/user/selectb").and(accept(APPLICATION_JSON_UTF8)), userHandler::selectBlock)
                ;
    }


}
