package com.xxxxxx.web;


import com.lergo.framework.annotation.LogTracker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "校验样例", description = "Valid代码实现模板")
@Validated
public class DemoValidController {

    @PostMapping("body")
    @Operation(summary = "body")
    @LogTracker("校验参数")
    public ValidDemo body(
            @RequestParam @Max(10) int tx,
            @RequestParam @Min(10) int ty,
            @RequestBody ValidDemo request) {
        return request;
    }

    @PostMapping(value = "form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "form")
    public String form(
            @RequestParam int x,
            @RequestPart("s") String s,
            @RequestPart("file") FilePart filePart) throws IOException {

        Path tempFile = Files.createTempFile(s, filePart.filename());
        filePart.transferTo(tempFile).subscribe();

//        DataBufferUtils.write(filePart.content(),
//                        AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE),
//                        0).doOnComplete(() -> {
//            System.out.println("finish");
//        }).subscribe();

        return "=>" + tempFile;
    }

    @Data
    static class ValidDemo {

        @Min(value = 1, message = "订单编号过小")
        @Max(value = 100, message = "订单编号过大")
        @NotNull(message = "订单编号不能为空")
        private Integer orderId;

        @NotBlank(message = "订单名称不能为空")
        private String orderName;

        @Size(min = 1, max = 10)
        private List<String> goodsList;

        @DecimalMin(value = "1", message = "订单金额必须大于等于1")
        private BigDecimal amount;
    }
}

