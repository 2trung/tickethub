package org.tickethub.application.service.ticket.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tickethub.application.service.ticket.TicketDetailAppService;
import org.tickethub.application.service.ticket.cache.TicketDetailCacheService;
import org.tickethub.model.entity.TicketDetail;
import org.tickethub.service.TicketDetailDomainService;

@Service
@Slf4j
public class TicketDetailAppServiceImpl implements TicketDetailAppService {

    private final TicketDetailCacheService ticketDetailCacheService;

    public TicketDetailAppServiceImpl(
            TicketDetailCacheService ticketDetailCacheService
    ) {
        this.ticketDetailCacheService = ticketDetailCacheService;
    }

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement Application : {}", ticketId);
        return ticketDetailCacheService.getTicketDefaultCacheVip(ticketId, System.currentTimeMillis());
    }
}
