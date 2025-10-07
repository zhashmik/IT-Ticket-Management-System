package com.synex.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synex.entity.Ticket;
import com.synex.entity.TicketHistory;
import com.synex.service.TicketHistoryService;
import com.synex.service.TicketService;

@RestController
@RequestMapping("/ticket-history")
public class TicketHistoryController {

    private final TicketHistoryService historyService;
    private final TicketService ticketService;

    public TicketHistoryController(TicketHistoryService historyService, TicketService ticketService) {
        this.historyService = historyService;
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public List<TicketHistory> getTicketHistory(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        return historyService.getHistoryByTicket(ticket);
    }
}