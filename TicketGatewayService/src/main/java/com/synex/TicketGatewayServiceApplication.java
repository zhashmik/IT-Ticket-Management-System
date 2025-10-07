package com.synex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.synex.entity.Employee;
import com.synex.entity.Role;
import com.synex.repository.EmployeeRepository;
import com.synex.repository.RoleRepository;

@SpringBootApplication
public class TicketGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketGatewayServiceApplication.class, args);
	}
	
	
	 @Bean
	    CommandLineRunner loadRolesAndAdmin(RoleRepository roleRepository,
	                                        EmployeeRepository employeeRepository,
	                                        PasswordEncoder passwordEncoder) {
	        return args -> {

	            // --- Create roles ---
	            Role userRole = roleRepository.findByName("USER");
	            if (userRole == null) {
	                userRole = new Role();
	                userRole.setName("USER");
	                roleRepository.save(userRole);
	            }

	            Role managerRole = roleRepository.findByName("MANAGER");
	            if (managerRole == null) {
	                managerRole = new Role();
	                managerRole.setName("MANAGER");
	                roleRepository.save(managerRole);
	            }

	            Role adminRole = roleRepository.findByName("ADMIN");
	            if (adminRole == null) {
	                adminRole = new Role();
	                adminRole.setName("ADMIN");
	                roleRepository.save(adminRole);
	            }

	            System.out.println("Roles loaded successfully!");
	            System.out.println("---- CommandLineRunner executing ----");
	            // --- Create default ADMIN employee ---
	            String adminEmail = "admin@example.com";
	            if (employeeRepository.findByEmail(adminEmail).isEmpty()) {
	                Employee admin = new Employee();
	                admin.setName("Default Admin");
	                admin.setEmail(adminEmail);
	                admin.setPassword(new BCryptPasswordEncoder().encode("admin123")); // encrypted
	                admin.setDepartment("IT");
	                admin.setProject("All");
	                admin.setManagerId(null);

	                Set<Role> roles = new HashSet<>();
	                roles.add(adminRole);
	                admin.setRoles(roles);

	                employeeRepository.save(admin);
	                System.out.println("Default ADMIN employee created: " + adminEmail + " / password: admin123");
	            }
	        };
	       
	    }


}
