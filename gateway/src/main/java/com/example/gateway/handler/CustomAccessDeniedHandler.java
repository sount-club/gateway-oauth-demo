package com.example.gateway.handler;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author lizhichao
 * @description 无权访问自定义响应
 * @date 2021/10/15 17:04
 */
@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange , AccessDeniedException e) {

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body = "{\"error\":\"无权访问资源\",\"code\":\"403\"}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
