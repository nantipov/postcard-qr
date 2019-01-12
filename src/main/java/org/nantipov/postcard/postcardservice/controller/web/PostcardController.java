package org.nantipov.postcard.postcardservice.controller.web;

import org.nantipov.postcard.postcardservice.services.AssetService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.file.Path;

@Controller
@RequestMapping("/postcard")
public class PostcardController {

    private static final String TEMPLATE_POSTCARD = "postcard";
    private static final String TEMPLATE_NEW_POSTCARD = "new-postcard";
    private static final String TEMPLATE_POSTCARD_QR = "postcard-qr";
    public static final String MODEL_ATTR_MESSAGE_CODE = "messageCode";

    private final AssetService assetsService;

    public PostcardController(AssetService assetsService) {
        this.assetsService = assetsService;
    }

    @GetMapping("/{messageCode}/view")
    public String showMessage(@PathVariable String messageCode, Model model) {
        model.addAttribute(MODEL_ATTR_MESSAGE_CODE, messageCode);
        return TEMPLATE_POSTCARD;
    }

    @GetMapping("/{messageCode}/download")
    public HttpEntity<FileSystemResource> download(@PathVariable String messageCode) {
        Path messageFile = assetsService.getMessageFile(messageCode)
                                        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                                  .filename(messageFile.getFileName().toString())
                                  .build()
        );
        return new HttpEntity<>(new FileSystemResource(messageFile), headers);
    }

    @GetMapping("/new")
    public String newMessage() {
        return TEMPLATE_NEW_POSTCARD;
    }

    @PostMapping("/create")
    public RedirectView createNewMessage(@RequestParam("messageFile") MultipartFile messageFile) {
        String messageCode = assetsService.createNewMessage(messageFile);
        return new RedirectView("/postcard/" + messageCode + "/qr", true);
    }

    @GetMapping("/{messageCode}/qr")
    public String showMessageQRCode(@PathVariable String messageCode, Model model) {
        model.addAttribute("messageCode", messageCode);
        return TEMPLATE_POSTCARD_QR;
    }

}
