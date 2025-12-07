package org.tickethub.application.service.event.impl;

import org.springframework.stereotype.Service;
import org.tickethub.application.service.event.EventAppService;
import org.tickethub.service.HiDomainService;

@Service
public class EventAppServiceImpl implements EventAppService {
    private final HiDomainService hiDomainService;

    public EventAppServiceImpl(HiDomainService hiDomainService) {
        this.hiDomainService = hiDomainService;
    }

    @Override
    public String sayHi(String who) {
        return hiDomainService.sayHi(who);
    }
}
