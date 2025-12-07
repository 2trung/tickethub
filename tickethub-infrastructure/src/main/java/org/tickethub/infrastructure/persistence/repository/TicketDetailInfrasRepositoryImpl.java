package org.tickethub.infrastructure.persistence.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tickethub.infrastructure.persistence.mapper.TicketDetailJPAMapper;
import org.tickethub.model.entity.TicketDetail;
import org.tickethub.repository.TicketDetailRepository;

import java.util.Optional;

@Service
@Slf4j
public class TicketDetailInfrasRepositoryImpl implements TicketDetailRepository {
    private final TicketDetailJPAMapper ticketDetailJPAMapper;

    public TicketDetailInfrasRepositoryImpl(TicketDetailJPAMapper ticketDetailJPAMapper) {
        this.ticketDetailJPAMapper = ticketDetailJPAMapper;
    }

    @Override
    public Optional<TicketDetail> findById(Long id) {
        log.info("Implement Infrastructure : {}", id);
        return ticketDetailJPAMapper.findById(id);
    }
}
