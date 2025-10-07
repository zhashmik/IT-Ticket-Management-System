# IT-Ticket-Management-System
It is an IT ticket management application designed to streamline the lifecycle of IT support tickets. The system supports role-based access, ticket creation, approval/rejection, resolution, history tracking, notifications, and file attachments.  

**Tech Stack:**  
- **Backend:** Java Spring Boot, Spring Security, Spring Data JPA, JavaMailSender  
- **Frontend:** HTML/CSS, jQuery, AJAX  
- **Database:** MySQL / Oracle  
- **Tools:** Postman, Git/GitHub, iText PDF, Cron Scheduler  

---


**Current Status: In Progress**  
- Ticket resolution workflow by support team is in progress  
- Notification service setup is ongoing  
- Frontend polishing is underway  

---

## Key Features  
- **User Authentication & Role Management** (USER, MANAGER, ADMIN)  
- **Ticket Lifecycle:** Create, approve/reject, reopen, close  
- **Ticket History Tracking**  
- **Email Notifications:** Automated emails for ticket creation and status changes  
- **Scheduled Tasks:** CRON jobs for pending ticket reminders  
- **PDF Generation:** Dynamic PDFs for resolved tickets  

---

## Domain Entities
| Entity | Key Attributes | Main Use Case |
|--------|----------------|---------------|
| **Employee** | id, name, email, roles, department, project, managerId | Login, ticket creation, assignment, resolution |
| **Role** | id, name | Role-based access and dashboards |
| **Ticket** | id, title, description, createdBy, assignee, priority, status, creationDate, category, fileAttachmentPath | Full ticket lifecycle |
| **TicketHistory** | id, ticket, action, actionBy, actionDate, comments | Logging ticket actions and tracking workflow |

---


## Ticket Lifecycle Process Flow
1. **Login** → Authenticated via Spring Security → Role-based dashboard  
2. **Raise Ticket** → Submit via UI → Ticket saved → Email sent to manager  
3. **Manager Action** → Approve / Reject → Status updated → Email sent  
4. **Ticket Resolution** → IT/Admin resolves ticket → PDF generated → Email sent  
5. **Closure / Reopen** → User closes or reopens ticket → History updated  
6. **Automation** → CRON jobs handle pending ticket reminders and auto-closure  

---

## Future Work 
- Complete ticket resolution workflow  
- Fully functional notification service  
- Frontend UI polishing and responsive design  
- Real-time notifications   

---


