package org.nantipov.postcard.postcardservice.domain;

import lombok.Data;

@Data
public class IPInfoResponse {
    private String ip;
    private String hostname;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String postal;
    private String phone;
    private String org;
}
