package org.tickethub.infrastructure.persistence.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tickethub.model.entity.TicketDetail;

import java.util.Optional;

public interface TicketDetailJPAMapper extends JpaRepository<TicketDetail, Long> {

    Optional<TicketDetail> findById(Long id);
}