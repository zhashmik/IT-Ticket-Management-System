package com.synex.service;

import com.synex.entity.Employee;
import com.synex.entity.Role;
import com.synex.repository.EmployeeRepository;
import com.synex.repository.RoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee registerEmployee(Employee employee) {

        if(employeeRepository.findByEmail(employee.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        // Encrypt password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Ensure roles exist and assign
        Set<Role> roleSet = new HashSet<>();
        if(employee.getRoles() != null){
            for(Role r : employee.getRoles()){
                Role role = roleRepository.findByName(r.getName());
                if(role == null){
                    role = new Role();
                    role.setName(r.getName());
                    roleRepository.save(role);
                }
                roleSet.add(role);
            }
        }
        employee.setRoles(roleSet);

        return employeeRepository.save(employee);
    }
    
    public Employee registerEmployeeWithRoles(Employee employee, List<String> roleNames) {

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        Set<Role> roleSet = new HashSet<>();
        for(String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName);
            if(role == null){
                role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
            roleSet.add(role);
        }

        employee.setRoles(roleSet);
        return employeeRepository.save(employee);
    }
    
    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
    }

}