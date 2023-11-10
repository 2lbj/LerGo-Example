package com.idriven.common.filter;

import com.idriven.common.annotation.ApiToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.annotation.Resource;

@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Order(99)//优先级，数字越小，优先级越高
public class TokenFilter implements WebFilter {

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Value("${token.filter-flag:false}")
    private boolean flag;

    @Value("${token.header-name:token}")
    private String tokenHeaderName;

    @NotNull
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        ServerHttpResponse res = exchange.getResponse();
        ServerHttpRequest req = exchange.getRequest();

        // 获取请求对应的HandlerMethod
        Mono<HandlerMethod> handlerMethodMono = requestMappingHandlerMapping
                .getHandler(exchange).cast(HandlerMethod.class);

        if(flag) {
            log.debug("Debug --> ");
            return chain.filter(exchange);
        }

        return handlerMethodMono.zipWhen(handlerMethod -> {
            // 判断Method是否含有对应注解
            if (handlerMethod.hasMethodAnnotation(ApiToken.class)) {

                MultiValueMap<String, String> params = req.getQueryParams();
                HttpHeaders headers = req.getHeaders();
                // 从header获取token
                String token = headers.getFirst(tokenHeaderName);
                // 从参数中获取token
                if(StringUtils.isNotBlank(params.getFirst(tokenHeaderName))) {
                    token = params.getFirst(tokenHeaderName);
                }
                // TODO: 校验Token
                log.debug(token);

                boolean valid = "OK".equals(token);

                // 校验通过，过滤器正常放行
                if (valid) return chain.filter(exchange);

            }

            // TODO: 校验不通过，返回错误信息
            res.setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            byte[] bytes = "NON_AUTHORITATIVE_INFORMATION".getBytes();
            return res.writeWith(Mono.just(res.bufferFactory().wrap(bytes)));

        }).map(Tuple2::getT2);
    }

}