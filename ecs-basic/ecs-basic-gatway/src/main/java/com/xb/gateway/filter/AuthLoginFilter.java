package com.xb.gateway.filter;

import com.xb.common.base.ret.ApiResult;
import com.xb.common.jwt.JWTUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.ServerRequest;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @description:网关统一token登录鉴权
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Component //加入到容器
public class AuthLoginFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //uri过滤，如果是注册和swagger，不需要校验解析token
        if(path.contains("member/api/v1") || path.contains("v2/api-docs")){
            return chain.filter(exchange);
        }
        //获取token
        String token = request.getHeaders().getFirst("TOKEN");
        ServerHttpResponse response = exchange.getResponse();
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //解析token
        ApiResult rt = JWTUtils.verifyToken(token);
        //是否解析成功
        if(rt.getCode() != ApiResult.SUCCESS_CODE){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    //return 的值越小，过滤器越靠前
    @Override
    public int getOrder() {
        return 0;
    }
}
