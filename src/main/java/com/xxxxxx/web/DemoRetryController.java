package com.xxxxxx.web;


import com.lergo.framework.utils.RetryExtracted;
import com.xxxxxx.common.utils.FeishuNotifications;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.xxxxxx.common.utils.FeishuNotifications.COLOUR.ORANGE;


@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "重试样例", description = "Retry代码实现模板")
public class DemoRetryController {

    @Resource
    FeishuNotifications feishuNotifications;

    @GetMapping("retry")
    public String retry() {
        feishuNotifications.sendMsg("test_default", "hello world");
        feishuNotifications.sendMsg("test_warring", "Error!", ORANGE,
                new String[]{"yunchao_zhang@human-horizons.com"}, true);
        // 定义重试器 TODO 抽象配置参数
        log.info("-------- <<< {}", RetryExtracted.booleanCallable(() ->
                feishuNotifications.sendMsg("test_retry", "retry")));

        return "ok";
    }

}

