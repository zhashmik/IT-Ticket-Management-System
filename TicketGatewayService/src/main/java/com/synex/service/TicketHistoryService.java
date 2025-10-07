package com.synex.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.entity.Employee;
import com.synex.entity.Ticket;
import com.synex.entity.TicketHistory;
import com.synex.repository.TicketHistoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TicketHistoryService {

    private final TicketHistoryRepository historyRepository;
    public TicketHistoryService(TicketHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }



    public void logAction(Ticket ticket, String action, Employee actionBy, String comments) {
        TicketHistory history = new TicketHistory();
        history.setTicket(ticket);
        history.setAction(action);
        history.setActionBy(actionBy);
        history.setActionDate(new Date());
        history.setComments(comments);

        historyRepository.save(history);
    }


    public List<TicketHistory> getHistoryByTicket(Ticket ticket) {
        return historyRepository.findByTicket(ticket);
    }
}