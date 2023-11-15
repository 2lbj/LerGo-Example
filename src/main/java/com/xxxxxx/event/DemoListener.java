package com.xxxxxx.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DemoListener {
    @EventListener(value = {DemoEvent.class})
    public void processApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("@bean-listener 收到了 publisher 发布的消息: " + msg);
    }

    @EventListener(value = {DemoEvent.class}, condition = "#event.msg == 'AKB48'")
    public void akb48Event(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("@akb48-listener 收到了 publisher 发布的消息: " + msg);
    }

    @EventListener
    @Async
    public void asyncProcessApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("@async-listener 收到了 publisher 发布的异步消息: " + msg);
    }
}
