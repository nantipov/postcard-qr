package org.nantipov.postcard.postcardservice.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.nantipov.postcard.postcardservice.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class QRCodeController {

    private final AssetService assetService;

    @Autowired
    public QRCodeController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/qr/{messageCode}")
    public void showQRCodeImage(@PathVariable("messageCode") String messageCode, HttpServletResponse response) {
        response.setContentType(MediaType.IMAGE_PNG.toString());
        try {
            assetService.writeMessageQRCode(messageCode, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not transfer data", e);
            throw new IllegalStateException(); //TODO: proper streams handling
        }
    }

}
