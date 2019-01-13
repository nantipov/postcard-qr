package org.nantipov.postcard.postcardservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nantipov.postcard.postcardservice.domain.LogEntity;
import org.nantipov.postcard.postcardservice.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;
    private final IPInfoService ipInfoService;

    @Autowired
    public LogService(LogRepository logRepository, ObjectMapper objectMapper,
                      IPInfoService ipInfoService) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
        this.ipInfoService = ipInfoService;
    }

    @Async
    public void retrieveIPInfoAndLog(LogEntity logEntity) {
        if (logEntity.getIpAddress() != null && !logEntity.getIpAddress().isEmpty()) {
            try {
                logEntity.setIpInfo(
                        objectMapper.writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(ipInfoService.getInfo(logEntity.getIpAddress()))
                );
            } catch (Exception e) {
                log.error("Could not get ipinfo", e);
            }
        }
        logRepository.save(logEntity);
    }

    public Collection<LogEntity> getLog() {
        return logRepository.findAllByOrderByCreatedAtDesc();
    }

}
