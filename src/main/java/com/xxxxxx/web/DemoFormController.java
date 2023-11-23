package com.xxxxxx.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("demo")
@Slf4j
@Tag(name = "样例", description = "I/O表单")
public class DemoFormController {

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

}

