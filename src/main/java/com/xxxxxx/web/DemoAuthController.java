package com.xxxxxx.web;

import cn.hutool.core.date.DateUtil;
import com.lergo.framework.annotation.UnAuthentication;
import com.lergo.framework.config.LergoFilterConfig;
import com.lergo.framework.config.LergoJWTConfig;
import com.lergo.framework.utils.JwtTool;
import de.huxhorn.sulky.ulid.ULID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("demo/auth")
@Slf4j
@Tag(name = "鉴权样例", description = "JWT载荷解析/Redis缓存鉴权")
public class DemoAuthController {
    @Resource
    LergoFilterConfig lergoFilterConfig;
    @Resource
    LergoJWTConfig lergoJWTConfig;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("jwt-login")
    @Operation(summary = "服务鉴权测试接口")
    @UnAuthentication
    public String jwtLogin() {

        Map<String, String> payload = new HashMap<>();
        payload.put("lid", "1234567890");//new ULID().nextValue().toString());
        payload.put("lts", DateUtil.current() + "");

        //2.将用户角色信息添加到JWT负载中
        payload.put("roles", String.join(",", Collections.singletonList("role-admin")));  // 将用户角色信息添加到JWT负载中

        return JwtTool.createToken(
                lergoJWTConfig.getAppKey(),
                lergoJWTConfig.getAppSecret(),
                (Optional.ofNullable(lergoFilterConfig.getAuthExpireSeconds())
                        .orElse(120L)).intValue(),
                payload);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("jwt-authentication")
    @Operation(summary = "服务鉴权测试接口")
    public Map<String, String> jwtAuthentication(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        JwtTool.JwtVerifyResult jwtVerifyResult = JwtTool.claimsToken(
                authorization.replace("Bearer ", ""),
                lergoJWTConfig.getAppKey(),
                lergoJWTConfig.getAppSecret(),
                lergoJWTConfig.getLeewaySeconds());

        return jwtVerifyResult.getPayload();
    }

    @GetMapping(value = "redis-login")
    @UnAuthentication
    public String redisLogin() {

        String token = new ULID().nextValue().toString();
        stringRedisTemplate.opsForValue().set(token, "User-Info: sys-admin",
                (Optional.ofNullable(lergoFilterConfig.getAuthExpireSeconds())
                        .orElse(120L)), TimeUnit.SECONDS);

        return token;
    }

    @GetMapping(value = "redis-authentication")
    public String redisAuthentication(@RequestHeader HttpHeaders headers) {

        return stringRedisTemplate.opsForValue().get(
                Objects.requireNonNull(headers.getFirst(
                        Optional.ofNullable(lergoFilterConfig.getAuthHeaderName())
                                .orElse("Authorization"))));
    }

}

