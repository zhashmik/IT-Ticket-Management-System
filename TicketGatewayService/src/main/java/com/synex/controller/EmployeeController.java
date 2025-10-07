package com.synex.controller;

import com.synex.entity.Employee;
import com.synex.entity.Role;
import com.synex.service.EmployeeService;
import com.synex.service.RoleService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleService roleService;

    public  EmployeeController(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    // Show registration form
    @GetMapping("/register-employee")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "register-employee"; // JSP/Thymeleaf template
    }

    // Handle form submission
    @PostMapping("/register-employee")
    public String registerEmployee(@ModelAttribute Employee employee, @RequestParam List<String> selectedRoles, Model model) {

        // Assign roles
        employeeService.registerEmployeeWithRoles(employee, selectedRoles);

        model.addAttribute("message", "Employee registered successfully");
        System.out.println("Employee registered successfully");
        return "register-employee"; // redirect back to form with success message
    }
    
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication auth) {
        model.addAttribute("userEmail", auth.getName()); // passes logged-in admin email
        return "admin-dashboard"; // your admin dashboard HTML
    }
}