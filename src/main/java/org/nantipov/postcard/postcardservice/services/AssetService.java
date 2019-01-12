package org.nantipov.postcard.postcardservice.services;

import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.scheme.Url;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Optional;

@Slf4j
@Service
public class AssetService {

    private static final char[] MESSAGE_CODE_ALPHABET = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final int MESSAGE_CODE_LENGTH = 7;


    @Value("${qr-postcard.assets-dir}")
    private String assetsDirectory;

    @Value("${qr-postcard.base-url}")
    private String baseUrl;

    private final SecureRandom secureRandom = new SecureRandom();

    public Optional<Path> getMessageFile(String messageCode) {
        try {
            return Files
                    .find(
                            Paths.get(assetsDirectory, messageCode),
                            1,
                            (path, attrs) -> (path.toString().endsWith(".gpg") || path.toString().endsWith(".pgp")) &&
                                             attrs.isRegularFile()
                    )
                    .findAny();
        } catch (IOException e) {
            log.error("Could not travers the asset directory", e);
            return Optional.empty();
        }
    }

    public String createNewMessage(MultipartFile messageFile) {
        try {
            String messageCode = generateMessageCode();
            Path targetDirectoryPath = Paths.get(assetsDirectory, messageCode);
            Files.createDirectories(targetDirectoryPath);
            Path targetFile = targetDirectoryPath.resolve(messageFile.getOriginalFilename());
            messageFile.transferTo(targetFile);
            return messageCode;
        } catch (IOException e) {
            throw new IllegalStateException("Could not create a new message", e);
        }
    }

    public void writeMessageQRCode(String messageCode, OutputStream outputStream) {
        String textToEncode = String.format(baseUrl, messageCode);
        Url schemaUrl = new Url();
        schemaUrl.setUrl(textToEncode);
        QRCode.from(schemaUrl)
              .to(ImageType.PNG)
              .withSize(250, 250)
              .writeTo(outputStream);
    }

    private String generateMessageCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int f = 0; f < MESSAGE_CODE_LENGTH; f++) {
            stringBuilder.append(MESSAGE_CODE_ALPHABET[secureRandom.nextInt(MESSAGE_CODE_ALPHABET.length)]);
        }
        return stringBuilder.toString();
    }

}
