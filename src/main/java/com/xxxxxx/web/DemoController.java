package com.xxxxxx.web;

import com.lergo.framework.annotation.RawResponse;
import com.lergo.framework.annotation.UnAuthentication;
import com.lergo.framework.entity.CommonResult;
import com.xxxxxx.common.exception.XxxBizException;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "样例", description = "返回格式")
public class DemoController {

    @RequestMapping(value = "test")
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

    @DeleteMapping(value = "error")
    @UnAuthentication
    public void error() {
        throw new XxxBizException("I'm a error");
    }

    @GetMapping(value = "testCommon")
    @UnAuthentication
    public CommonResult<String> testCommon() {
        return CommonResult.success("OK I'm a common json");
    }


    @GetMapping("testEvents")
    @Operation(summary = "SSE长连接测试接口", description = "每秒推送测试数据 中断后5秒重连",
            externalDocs = @ExternalDocumentation(url = "/html/sse.html", description = "SSE Demo"))
    public Flux<ServerSentEvent<String>> getEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("sse-event")
                        .data("ID - " + sequence)
                        .retry(Duration.ofSeconds(5))
                        .build());
    }
}

