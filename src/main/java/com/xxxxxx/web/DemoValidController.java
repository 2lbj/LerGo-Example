package com.xxxxxx.web;


import com.lergo.framework.annotation.LogTracker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "校验样例", description = "Valid代码实现模板")
@Validated
public class DemoValidController {

    @GetMapping("param")
    @Operation(summary = "param")
    @LogTracker("请求-参数")
    public int param(@RequestParam @Max(10) int tx, @RequestParam @Min(10) int ty) {
        return tx + ty;
    }

    @PostMapping("body")
    @Operation(summary = "body")
    @LogTracker("请求-数据")
    public ValidDemo body(@RequestBody @Valid ValidDemo request) {
        return request;
    }

    @Data
    // message 可省略，会使用默认的提示信息
    static class ValidDemo {

        @Min(value = 1, message = "编号过小")
        @Max(value = 100, message = "编号过大")
        @NotNull(message = "编号不能为空")
        private Integer id;

        @NotBlank(message = "名称不能为空")
        private String name;

        @Size(min = 1, max = 3)
        private List<String> goodsList;

        @DecimalMin(value = "1", message = "金额必须大于等于1")
        private BigDecimal amount;
    }
}

