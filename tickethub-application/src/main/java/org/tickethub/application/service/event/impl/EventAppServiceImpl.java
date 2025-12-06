package org.tickethub.application.service.event.impl;

import org.springframework.stereotype.Service;
import org.tickethub.application.service.event.EventAppService;

@Service
public class EventAppServiceImpl implements EventAppService {
    @Override
    public String greet() {
        return "Hello from application!";
    }
}
