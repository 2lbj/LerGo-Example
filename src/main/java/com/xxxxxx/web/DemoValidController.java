package com.xxxxxx.web;


import com.lergo.framework.annotation.LogTracker;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "校验样例", description = "Valid代码实现模板")
//@Validated
public class DemoValidController {

    private final Path basePath = Paths.get("./aaa");

    @GetMapping("validTest-Param")
    @LogTracker("校验参数")
    public String testParam(
            @RequestParam @Valid @Max(10) int tx,
            @RequestParam @Valid @Min(10) int ty) {
        return "Validate-OK";
    }

    @PostMapping(value = "validTest-Form")
    @LogTracker("校验参数")
    public Mono<Void> testForm(
            @RequestPart(value = "n", required = false) @Valid @Min(10) int n,
            @RequestPart(value = "filePartMono", required = false) @Valid @NotEmpty @Parameter(description = "文件") Mono<FilePart> filePartMono) {
        return filePartMono
                .doOnNext(fp -> System.out.println(fp.filename()))
                .flatMap(fp -> fp.transferTo(basePath.resolve(fp.filename())))
                .then();
    }

    @PostMapping("validTest-Body")
    @LogTracker("校验参数")
    public String testBody(
            @RequestBody @Valid ValidDemo validDemo) {
        return "Validate-OK";
    }

}

