package com.xxxxxx.web;


import com.lergo.framework.annotation.LogTracker;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("demo/valid")
@Slf4j
@Tag(name = "校验样例", description = "Valid代码实现模板")
@Validated
public class DemoValidController {

    @GetMapping("param")
    @Operation(description = "# param校验\r\n" +
            " - tx\n" +
            " - ty\n", summary = "参数校验",
            externalDocs = @ExternalDocumentation(url = "https://www.openapis.org/", description = "OpenApi"))
    @LogTracker("请求-参数")
    public int param(
            @Parameter(description = "参数X", example = "10")
            @RequestParam @Max(10) int tx,
            @Parameter(description = "参数Y", example = "10")
            @RequestParam @Min(10) int ty) {
        return tx + ty;
    }

    @PostMapping("body")
    @Operation(description = "body校验 \n# 标题\n" +
            " - 列表 **加粗** `标记`\n" +
            "```\n" +
            " this is code\n" +
            "```\n", summary = "请求体校验")
    @LogTracker("请求-数据")
    public ValidDemo body(@RequestBody @Valid ValidDemo request) {
        return request;
    }

    @Data
    @Schema(description = "请求体")
    // message 可省略，会使用默认的提示信息
    static class ValidDemo {

        @Min(value = 1, message = "编号过小")
        @Max(value = 100, message = "编号过大")
        @NotNull(message = "编号不能为空")
        @Schema(description = "编号", example = "1")
        private Integer id;

        @NotBlank(message = "名称不能为空")
        @Schema(description = "名称", example = "xxx")
        private String name;

        @Size(min = 1, max = 3)
        @Schema(description = "列表", example = "[\"apple\",\"banana\"]")
        private List<String> goodsList;

        @DecimalMin(value = "1", message = "金额必须大于等于1")
        @Schema(description = "金额", example = "1.00")
        private BigDecimal amount;
    }
}

