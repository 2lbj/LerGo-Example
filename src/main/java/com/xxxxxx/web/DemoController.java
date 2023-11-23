package com.xxxxxx.web;

import com.lergo.framework.annotation.RawResponse;
import com.lergo.framework.annotation.UnAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "样例", description = "返回格式")
public class DemoController {

    @GetMapping(value = "test")
    @UnAuthentication
    public String test() {
        return "OK I'm a json";
    }

    @GetMapping(value = "testRaw")
    @UnAuthentication
    @RawResponse
    public String testRaw() {
        return "OK I'm a raw string";
    }

//    @GetMapping("mono")
//    @RawResponse
//    public Mono<Object> mono() {
//        return Mono.create(monoSink -> {
//            log.info("创建 Mono");
//            monoSink.success("hello webflux");
//        }).doOnSubscribe(subscription -> { //当订阅者去订阅发布者的时候，该方法会调用
//            log.info("subscription={}", subscription);
//        }).doOnNext(o -> { //当订阅者收到数据时，改方法会调用
//            log.info("o={}", o);
//        });
//    }
//
//    @RequestMapping("webClient")
//    @RawResponse
//    public Mono<String> webClient(){
//        Mono<String> resp = WebClient.create("http://localhost:8080")
//                .get().uri("/demo/test")
//                .retrieve()
//                .bodyToMono(String.class);
//        resp.subscribe(System.out::println);
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return resp;
//    }
//
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

