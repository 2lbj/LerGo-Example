package com.xxxxxx.web;

import com.lergo.framework.annotation.LogTracker;
import com.lergo.framework.annotation.UnAuthentication;
import com.xxxxxx.event.DemoPublisher;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("demo/event")
@Slf4j
@Tag(name = "事件样例", description = "Event代码实现模板")
public class DemoEventController {

    @Resource
    DemoPublisher bean;

    @GetMapping("/publish")
    @UnAuthentication
    @LogTracker("发布者发送消息")
    public void publish() {
        bean.sendMsg("AKB48");
    }

}
