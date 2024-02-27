package com.xxxxxx.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "demo",
        configuration = APIFeignConfig.class, //内部调用避免重试防止服务响应超时后引起调用风暴
        url = "${feign.demo-url}")
public interface APIFeignDemo {

    /**
     * 测试接口
     *
     * @param id
     * @param header
     * @param queryMap
     * @return String
     * @see <a href="http://localhost:8080/webjars/swagger-ui/index.html#/%E6%A0%B7%E4%BE%8B/test_1">测试连接</a>
     * @deprecated 该接口已经被废弃，使用新接口<a href="http://localhost:8080/webjars/swagger-ui/index.html#/%E6%A0%B7%E4%BE%8B/test_1">xxxx</a>
     */
    @GetMapping("/demo/test")
    String test();
}
