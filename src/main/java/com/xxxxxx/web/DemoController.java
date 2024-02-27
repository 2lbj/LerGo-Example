package com.xxxxxx.web;

import com.lergo.framework.annotation.RawResponse;
import com.lergo.framework.annotation.UnAuthentication;
import com.lergo.framework.entity.CommonResult;
import com.xxxxxx.common.exception.BizEnumException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xxxxxx.common.constants.BizErrorEnum.UNKNOWN_ERROR;

@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "样例", description = "返回格式")
public class DemoController {

    @RequestMapping(value = "test")
    @UnAuthentication
    public String test() {
        return "OK I'm a json";
    }

    @GetMapping(value = "testRaw")
    @UnAuthentication
    @RawResponse
    public String testRaw() {
        return "OK I'm a raw string";
    }

    @Operation(summary = "异常返回", description = "抛出异常枚举", responses = {
            @ApiResponse(responseCode = "500001", description = "未知错误")
    })
    @DeleteMapping(value = "error")
    @UnAuthentication
    public void error() {
        throw new BizEnumException(UNKNOWN_ERROR);
    }

    @GetMapping(value = "testCommon")
    @UnAuthentication
    public CommonResult<String> testCommon() {
        return CommonResult.success("OK I'm a common json");
    }

}

