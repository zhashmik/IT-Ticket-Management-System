package com.synex.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonBackReference
    private Ticket ticket;

    private String action; // CREATED, APPROVED, REJECTED, ASSIGNED, RESOLVED, CLOSED, REOPENED

    @ManyToOne
    private Employee actionBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    private String comments;

	public TicketHistory(Long id, Ticket ticket, String action, Employee actionBy, Date actionDate, String comments) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.action = action;
		this.actionBy = actionBy;
		this.actionDate = actionDate;
		this.comments = comments;
	}

	public TicketHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Employee getActionBy() {
		return actionBy;
	}

	public void setActionBy(Employee actionBy) {
		this.actionBy = actionBy;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
