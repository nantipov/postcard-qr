package org.nantipov.postcard.postcardservice.services;

import org.nantipov.postcard.postcardservice.domain.IPInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IPInfoService {
    @Value("${qr-postcard.ipinfo-url}")
    private String ipInfoUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public IPInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IPInfoResponse getInfo(String ipAddress) {
        return restTemplate.getForObject(ipInfoUrl, IPInfoResponse.class, ipAddress);
    }
}
