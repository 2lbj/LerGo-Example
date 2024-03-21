package com.xxxxxx.filter;

import com.lergo.framework.filter.BaseFilter;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpMethod.*;

@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Order(101)
public class CORSFilter extends BaseFilter implements WebFilter {
    @NotNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setAccessControlAllowHeaders(Collections.singletonList("*"));
        headers.setAccessControlAllowMethods(Arrays.asList(GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE));
        return chain.filter(exchange);
    }

}