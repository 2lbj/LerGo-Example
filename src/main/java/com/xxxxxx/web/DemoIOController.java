package com.xxxxxx.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("demo/io")
@Slf4j
@Tag(name = "文件样例", description = "I/O表单")
public class DemoIOController {

    @PostMapping(value = "form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "form")
    public String form(
            @RequestParam int x,
            @RequestPart("s") String s,
            @RequestPart("file") FilePart filePart) throws IOException {

        System.out.println(filePart.filename());
        Path tempFile = Files.createTempFile("test", filePart.filename());

//        //NOTE 方法一
//        AsynchronousFileChannel channel =
//                AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
//        DataBufferUtils.write(filePart.content(), channel, 0)
//                .doOnComplete(() -> {
//                    System.out.println("finish");
//                })
//                .subscribe();

        //NOTE 方法二
        filePart.transferTo(tempFile).subscribe();

        //逐行读取文件内容
        Files.lines(tempFile).forEach(System.out::println);

        return "=>" + tempFile;
    }

    @GetMapping("/download")
    public Mono<Void> downloadByWriteWith(ServerHttpResponse response) throws IOException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parallel.png");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);

        ClassPathResource resource = new ClassPathResource("parallel.png");
        File file = resource.getFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}

