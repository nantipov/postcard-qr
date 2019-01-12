package org.nantipov.postcard.postcardservice.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.nantipov.postcard.postcardservice.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Slf4j
@Controller
public class QRCodeController {

    private final AssetService assetService;

    @Autowired
    public QRCodeController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/qr/{messageCode}")
    public ResponseEntity<StreamingResponseBody> showQRCodeImage(@PathVariable("messageCode") String messageCode) {
        return ResponseEntity.ok()
                             .contentType(MediaType.IMAGE_PNG)
                             .body(responseStream -> assetService.writeMessageQRCode(messageCode, responseStream));
    }

}
