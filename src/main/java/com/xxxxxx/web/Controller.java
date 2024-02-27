package com.xxxxxx.web;

import com.lergo.framework.annotation.LogTracker;
import com.lergo.framework.annotation.UnAuthentication;
import com.lergo.framework.config.LergoConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/")
@Slf4j
@Tag(name = "健康检查", description = "监控")
public class Controller {

    @Resource
    LergoConfig lergoConfig;

    @GetMapping("ping")
    @LogTracker("Ping...")
    @Operation(summary = "服务连通性测试接口", description = "返回PONG")
    @UnAuthentication
    public String ping(ServerWebExchange exchange) {

        exchange.getResponse().getHeaders().add(
                HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_CBOR_VALUE);

        return "PONG";
    }

    @GetMapping("config")
    public ResponseEntity<LergoConfig> config() {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(lergoConfig);
    }
}

