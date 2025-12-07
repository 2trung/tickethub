package org.tickethub.service.impl;

import org.tickethub.model.entity.TicketDetail;
import org.tickethub.repository.TicketDetailRepository;
import org.tickethub.service.TicketDetailDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailDomainServiceImpl implements TicketDetailDomainService {

    private final TicketDetailRepository ticketDetailRepository;

    public TicketDetailDomainServiceImpl(TicketDetailRepository ticketDetailRepository) {
        this.ticketDetailRepository = ticketDetailRepository;
    }

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement Domain : {}", ticketId);
        return ticketDetailRepository.findById(ticketId).orElse(null);
    }
}
