package com.xxxxxx.web;

import com.lergo.framework.utils.QRCodeTool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo/qr")
@Slf4j
@Tag(name = "二维码样例", description = "返回二维码/条形码")
public class DemoQRCodeController {

    @GetMapping("qrcode")
    @Operation(summary = "生成二维码", description = "根据给定字符 生成二维码")
    public ResponseEntity<String> qrcode(String data) {

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(
                "<img src=\"" + QRCodeTool.generateQRCodeBase64(data) + "\" />");

    }

    @GetMapping("barcode")
    @Operation(summary = "生成条形码", description = "根据给定数字 生成条形码")
    public ResponseEntity<String> barcode(Long data) {

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(
                "<img src=\"" + QRCodeTool.generateBarcodeBase64(data.toString()) + "\" />");

    }

}

