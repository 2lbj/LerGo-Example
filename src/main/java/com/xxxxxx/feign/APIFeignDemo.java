package com.xxxxxx.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "demo",
        configuration = APIFeignConfig.class, //内部调用避免重试防止服务响应超时后引起调用风暴
        url = "${feign.demo-url}")
public interface APIFeignDemo {
    @GetMapping("/demo/test")
    String test();
}
