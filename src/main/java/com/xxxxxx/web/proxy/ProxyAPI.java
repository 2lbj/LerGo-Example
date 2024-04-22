package com.xxxxxx.web.proxy;

import com.lergo.framework.annotation.RawResponse;
import com.lergo.framework.exception.BizException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.lergo.framework.entity.CommonResult.gson;

@RestController
@Hidden
@Slf4j
public class ProxyAPI {

    //@Value("${domain}")
    String domain;
    @Value("${atomic-uri.xxx}")
    String atomicURI_XXX;


    WebClient webClient = WebClient.builder()
            .exchangeStrategies(
                    ExchangeStrategies.builder()
                            .codecs(c -> c.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10M json解析
                            .build())
            .filter(logRequest())
            .filter(logResponse())
            .build();
    RestTemplate restTemplate = new RestTemplate();

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("ProxyAPI==> Request: {} {} >>> ({}) [{}]",
                    clientRequest.method(),
                    clientRequest.url(),
                    gson.toJson(clientRequest.headers()),
                    clientRequest.body());
            return next.exchange(clientRequest);
        };
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("ProxyAPI==> Response: {} {} <<< ({}) {} ",
                    clientResponse.request().getMethod(),
                    clientResponse.request().getURI(),
                    gson.toJson(clientResponse.headers()),
                    clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }

    @RequestMapping(value = "/mgnt-xxx-atomic/api/**")
    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "doProxyLimitFallback")
    @RawResponse
    public Mono<byte[]> eosProxy(ServerWebExchange exchange) {
        return doProxy(exchange, atomicURI_XXX + exchange.getRequest().getPath().value()
                .replaceAll("^/mgnt-eos-atomic/", "/"));
    }

    /**
     * 限流回调
     *
     * @return Mono<String>
     */
    private Mono<byte[]> doProxyLimitFallback(Throwable throwable) {
        log.warn("API Limited {}", throwable.getMessage());
        throw new BizException(429, "API Limited ... please slow down");
    }

    private Mono<byte[]> doProxy(ServerWebExchange exchange, String uri) {
        ServerHttpRequest request = exchange.getRequest();

//        StringBuilder jsonBody = new StringBuilder();
////        exchange.getRequest().getBody().subscribe(dataBuffer -> {
////            byte[] bytes = new byte[dataBuffer.readableByteCount()];
////            dataBuffer.read(bytes);
////            jsonBody.append(new String(bytes));
////        });
////        if (jsonBody.isEmpty()) {
////        }
//        exchange.getFormData().subscribe(map ->
//                jsonBody.append(gson.toJson(map))
//        );
//
//        log.info("jsonBody: {} ", jsonBody);
//        HttpEntity<byte[]> rp = restTemplate.exchange(
//                uri + "?" + request.getQueryParams().toSingleValueMap().entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).reduce((a, b) -> a + "&" + b).orElse(""),
//                request.getMethod(),
//                new HttpEntity<>(jsonBody, new HttpHeaders() {{
//                    setContentType(MediaType.APPLICATION_JSON);
//                }}),
//                byte[].class);
//        exchange.getResponse().getHeaders().putAll(rp.getHeaders());
//        return Mono.just(Objects.requireNonNull(rp.getBody()));

        return webClient.method(request.getMethod())
                .uri(uri, uriBuilder -> {
                    //uriBuilder.scheme("http");
                    //uriBuilder.host("svc-mgnt-vcc-atomic");
                    //uriBuilder.path(request.getPath().value().replaceAll("^/mgnt-vcc-atomic/", "/"));
                    uriBuilder.queryParams(request.getQueryParams());
                    return uriBuilder.build();
                })
                .headers(httpHeaders -> {
                    httpHeaders.addAll(request.getHeaders());
                    //K8S Istio 修正DNS?
                    //httpHeaders.setHost(InetSocketAddress.createUnresolved(domain, 80));
                })
                .body(BodyInserters.fromDataBuffers(request.getBody()))
                .exchangeToMono(clientResponse -> {
                    exchange.getResponse().getHeaders().putAll(clientResponse.headers().asHttpHeaders());
                    return clientResponse.bodyToMono(byte[].class);
                }).defaultIfEmpty("".getBytes());
    }

}
