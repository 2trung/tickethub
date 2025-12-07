package org.tickethub.service;


import org.tickethub.model.entity.TicketDetail;

public interface TicketDetailDomainService {

    TicketDetail getTicketDetailById(Long ticketId);
}
