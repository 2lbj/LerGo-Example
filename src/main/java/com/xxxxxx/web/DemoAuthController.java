package com.xxxxxx.web;

import cn.hutool.core.date.DateUtil;
import com.lergo.framework.annotation.UnAuthentication;
import com.lergo.framework.utils.JwtTool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("demo/suth")
@Slf4j
@Tag(name = "鉴权样例", description = "JWT载荷解析/Redis缓存鉴权")
public class DemoAuthController {

    @GetMapping("authentication")
    @Operation(summary = "服务鉴权测试接口")
    public Map<String, String> authentication(@RequestHeader(value = "Authorization", required = false)
                                              String authorization) {

        String testAppKey = "testAppkey";
        String testAppSecret = "*************************";

        JwtTool.JwtVerifyResult jwtVerifyResult = JwtTool.claimsToken(
                authorization.replace("Bearer ", ""),
                testAppKey,
                testAppSecret);
        return jwtVerifyResult.getPayload();
    }

    @GetMapping("login")
    @Operation(summary = "服务鉴权测试接口")
    @UnAuthentication
    public String login() {
        Map<String, String> payload = new HashMap<>();
        payload.put("uuid", "0000000000");
        payload.put("ts", DateUtil.current() + "");

        String testAppKey = "testAppkey";
        String testAppSecret = "*************************";

        return JwtTool.createToken(payload, testAppKey, testAppSecret, Collections.singletonList("role-admin"));
    }

}

