package org.nantipov.postcard.postcardservice.services;

import org.nantipov.postcard.postcardservice.controller.web.PostcardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

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
        logService.log(request, messageCode);
    }
}
