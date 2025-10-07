package com.synex.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.entity.Employee;
import com.synex.entity.Ticket;
import com.synex.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TicketService  {

    private final TicketRepository ticketRepository;
    private final TicketHistoryService ticketHistoryService;


    public TicketService(TicketRepository ticketRepository, TicketHistoryService ticketHistoryService) {
        this.ticketRepository = ticketRepository;
        this.ticketHistoryService = ticketHistoryService;
    }
    
    public Ticket createTicket(Ticket ticket, Employee creator) {
        ticket.setCreatedBy(creator);
        ticket.setStatus("CREATED");
        ticket.setCreationDate(new Date());
        Ticket saved = ticketRepository.save(ticket);

        ticketHistoryService.logAction(saved, "CREATED", creator, "Ticket created");
        return saved;
    }


    public Ticket assignTicket(Long ticketId, Employee assignee, Employee actionBy) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setAssignee(assignee);
        ticket.setStatus("ASSIGNED");
        ticketRepository.saveAndFlush(ticket);
//        Ticket updated = ticketRepository.save(ticket);

//        ticketHistoryService.logAction(updated, "ASSIGNED", actionBy, "Ticket assigned to " + assignee.getEmail());
//        return updated;
        ticketHistoryService.logAction(ticket, "ASSIGNED", actionBy, "Ticket assigned to " + assignee.getEmail());
        return ticket;

    }


    public Ticket approveTicket(Long ticketId, Employee manager, boolean approved, String comments) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus(approved ? "APPROVED" : "REJECTED");
        Ticket updated = ticketRepository.save(ticket);

        ticketHistoryService.logAction(updated, approved ? "APPROVED" : "REJECTED", manager, comments);
        return updated;
    }


    public Ticket resolveTicket(Long ticketId, Employee resolver, String resolutionComments) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus("RESOLVED");
        Ticket updated = ticketRepository.save(ticket);

        ticketHistoryService.logAction(updated, "RESOLVED", resolver, resolutionComments);
        return updated;
    }


    public Ticket closeTicket(Long ticketId, Employee closer) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus("CLOSED");
        Ticket updated = ticketRepository.save(ticket);

        ticketHistoryService.logAction(updated, "CLOSED", closer, "Ticket closed");
        return updated;
    }


    public Ticket reopenTicket(Long ticketId, Employee reopener, String comments) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus("REOPENED");
        Ticket updated = ticketRepository.save(ticket);

        ticketHistoryService.logAction(updated, "REOPENED", reopener, comments);
        return updated;
    }


    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id).orElseThrow();
    }


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    public List<Ticket> getTicketsByUser(Employee user) {
        return ticketRepository.findByCreatedBy(user);
    }
}