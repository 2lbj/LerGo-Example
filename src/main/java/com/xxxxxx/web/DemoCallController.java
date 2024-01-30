package com.xxxxxx.web;

import com.lergo.framework.annotation.RawResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("demo/call")
@Slf4j
@Tag(name = "调用样例", description = "调用外部请求及长连接")
public class DemoCallController {

    @GetMapping("mono")
    @RawResponse
    public Mono<Object> mono() {
        return Mono.create(monoSink -> {
            log.info("创建 Mono");
            monoSink.success("hello webflux");
        }).doOnSubscribe(subscription -> { //当订阅者去订阅发布者的时候，该方法会调用
            log.info("subscription={}", subscription);
        }).doOnNext(o -> { //当订阅者收到数据时，改方法会调用
            log.info("o={}", o);
        });
    }

    @GetMapping(value = "redirect")
    public Mono<Void> redirect(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create("https://www.example.com/"));
        return response.setComplete();
    }

    @GetMapping(value = "webClient")
    public Mono<String> webClient() {
        return WebClient.create("http://localhost:8080")
                .get().uri("/demo/test")
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println);
    }

//    //每秒会给浏览器推送数据
//    @GetMapping(value = "/interval", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    private Flux<String> flux(@RequestParam int t) {
//        return Flux.fromStream(IntStream.range(1, t).mapToObj(i -> {
//            log.info("i=" + i);
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "this data is " + i;
//        }));
//    }

}

