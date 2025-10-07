package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.entity.Employee;
import com.synex.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	List<Ticket> findByCreatedBy(Employee employee);
}
