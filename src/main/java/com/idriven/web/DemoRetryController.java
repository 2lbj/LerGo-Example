package com.idriven.web;


import com.idriven.common.utils.FeishuNotifications;
import com.lergo.framework.utils.RetryExtracted;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.idriven.common.utils.FeishuNotifications.COLOUR.ORANGE;


@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "样例", description = "代码实现模板")
public class DemoRetryController {

    @Resource
    FeishuNotifications feishuNotifications;

    @GetMapping("retry")
    public String retry() {
        feishuNotifications.sendMsg("test_default", "hello world");
        feishuNotifications.sendMsg("test_warring", "Error!", ORANGE,
                new String[]{"yunchao_zhang@human-horizons.com"}, true);
        // 定义重试器
        log.info("-------- <<< {}", RetryExtracted.booleanCallable(() ->
                feishuNotifications.sendMsg("test_retry", "retry")));

        return "ok";
    }

}

