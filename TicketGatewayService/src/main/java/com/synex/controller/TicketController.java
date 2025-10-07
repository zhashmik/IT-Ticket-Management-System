package com.synex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.synex.entity.Ticket;
import com.synex.entity.Employee;
import com.synex.service.TicketService;
import com.synex.service.EmployeeService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final EmployeeService employeeService;

    public TicketController(TicketService ticketService, EmployeeService employeeService) {
        this.ticketService = ticketService;
        this.employeeService = employeeService;
    }

//    @PostMapping
//    public Ticket createTicket(@RequestBody Ticket ticket, Authentication auth) {
//        Employee creator = employeeService.getByEmail(auth.getName());
//        return ticketService.createTicket(ticket, creator);
//    }
    @PostMapping("/upload")
    public Ticket createTicketWithFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("priority") String priority,
            @RequestParam("category") String category,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment,
            Authentication auth) throws IOException {

        Employee creator = employeeService.getByEmail(auth.getName());
        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setCategory(category);

        if (attachment != null && !attachment.isEmpty()) {

            Path uploadPath = Paths.get("C:/ticket_uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + attachment.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            attachment.transferTo(filePath.toFile());

            ticket.setFileAttachmentPath(fileName);
             // String uploadDir = "uploads";
//            File uploadFolder = new File(uploadDir);
//            if (!uploadFolder.exists()) uploadFolder.mkdirs();
//
//            String filePath = uploadDir + System.currentTimeMillis() + "_" + attachment.getOriginalFilename();
//            attachment.transferTo(new File(filePath));
//            ticket.setFileAttachmentPath(filePath);
        }

        return ticketService.createTicket(ticket, creator);
    }

    @PostMapping("/{id}/assign")
    public Ticket assignTicket(@PathVariable Long id, @RequestParam String assigneeEmail, Authentication auth) {
        Employee assignee = employeeService.getByEmail(assigneeEmail);
        Employee actionBy = employeeService.getByEmail(auth.getName());
        return ticketService.assignTicket(id, assignee, actionBy);
    }

    @PostMapping("/{id}/approve")
    public Ticket approveTicket(@PathVariable Long id, @RequestParam boolean approved, @RequestParam String comments, Authentication auth) {
        Employee manager = employeeService.getByEmail(auth.getName());
        return ticketService.approveTicket(id, manager, approved, comments);
    }

    @PostMapping("/{id}/resolve")
    public Ticket resolveTicket(@PathVariable Long id, @RequestParam String resolutionComments, Authentication auth) {
        Employee resolver = employeeService.getByEmail(auth.getName());
        return ticketService.resolveTicket(id, resolver, resolutionComments);
    }

    @PostMapping("/{id}/close")
    public Ticket closeTicket(@PathVariable Long id, Authentication auth) {
        Employee closer = employeeService.getByEmail(auth.getName());
        return ticketService.closeTicket(id, closer);
    }

    @PostMapping("/{id}/reopen")
    public Ticket reopenTicket(@PathVariable Long id, @RequestParam String comments, Authentication auth) {
        Employee reopener = employeeService.getByEmail(auth.getName());
        return ticketService.reopenTicket(id, reopener, comments);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/my")
    public List<Ticket> getMyTickets(Authentication auth) {
        Employee user = employeeService.getByEmail(auth.getName());
        return ticketService.getTicketsByUser(user);
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }
    
    
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get("uploads").resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
