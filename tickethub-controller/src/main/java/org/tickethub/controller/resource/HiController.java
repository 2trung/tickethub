package org.tickethub.controller.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tickethub.application.service.event.EventAppService;

@RestController
public class HiController {
    public final EventAppService eventAppService;

    public HiController(EventAppService eventAppService) {
        this.eventAppService = eventAppService;
    }

    @GetMapping("/hello")
    public String hello() {
        return eventAppService.greet();
    }
}
