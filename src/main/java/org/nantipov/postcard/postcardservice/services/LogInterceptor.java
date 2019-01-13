package org.nantipov.postcard.postcardservice.services;

import org.nantipov.postcard.postcardservice.controller.web.PostcardController;
import org.nantipov.postcard.postcardservice.domain.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Value("${qr-postcard.ipinfo-header}")
    private String ipinfoHeader;

    private final LogService logService;

    @Autowired
    public LogInterceptor(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        String messageCode = "";
        if (modelAndView != null && modelAndView.getModel() != null) {
            messageCode = (String) modelAndView.getModel().get(PostcardController.MODEL_ATTR_MESSAGE_CODE);
        }
        logService.retrieveIPInfoAndLog(toLogEntity(request, messageCode));
    }

    private LogEntity toLogEntity(HttpServletRequest request, String messageCode) {
        LogEntity logEntity = new LogEntity();
        String ip = request.getHeader(ipinfoHeader);
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        logEntity.setIpAddress(ip);
        logEntity.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        logEntity.setMessageCode(messageCode);
        logEntity.setRequestPath(request.getRequestURI());
        return logEntity;
    }
}
