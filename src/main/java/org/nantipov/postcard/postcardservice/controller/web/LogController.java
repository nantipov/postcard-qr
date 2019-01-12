package org.nantipov.postcard.postcardservice.controller.web;

import org.nantipov.postcard.postcardservice.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController {

    private static final String TEMPLATE_LOG = "log";

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public String viewLog(Model model) {
        model.addAttribute("logEntries", logService.getLog());
        return TEMPLATE_LOG;
    }

}
