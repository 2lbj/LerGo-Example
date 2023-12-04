package com.xxxxxx.web;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo/rateLimiter")
@Slf4j
@Tag(name = "限流样例", description = "限流代码实现模板")
public class DemoRateLimiterController {

    @GetMapping(value = "test")
    @RateLimiter(name = "xxxxRateLimiter", fallbackMethod = "testFallback")
    public String test() {
        return "OK";
    }

    private String testFallback(Throwable throwable) {
        return "Limited please slow down";
    }

    //TODO 超时 熔断 https://www.infoq.cn/article/l3b9wcuxqjh1drfjei87

}

