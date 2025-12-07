package org.tickethub.controller.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tickethub.application.service.ticket.TicketDetailAppService;
import org.tickethub.controller.model.enums.ResultUtil;
import org.tickethub.controller.model.vo.ResultMessage;
import org.tickethub.model.entity.TicketDetail;

@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketDetailController {

    private final TicketDetailAppService ticketDetailAppService;

    public TicketDetailController(TicketDetailAppService ticketDetailAppService) {
        this.ticketDetailAppService = ticketDetailAppService;
    }

    @GetMapping("/{ticketId}/detail/{detailId}")
    public ResultMessage<TicketDetail> getTicketDetail(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("detailId") Long detailId
    ) {
        log.info("MEMBER TIPS GO");
        log.info(" ticketId:{}, detailId:{}", ticketId, detailId);
        return ResultUtil.data(ticketDetailAppService.getTicketDetailById(detailId));
    }
}
