package com.synex.security;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.synex.entity.Employee;
import com.synex.repository.EmployeeRepository;

@Service 
public class CustomUserDetailsService  implements UserDetailsService{
	 private final EmployeeRepository employeeRepository;

	    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
	        this.employeeRepository = employeeRepository;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        Employee employee = employeeRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//	        return new CustomUserDetails(employee);
	        return new org.springframework.security.core.userdetails.User(
	                employee.getEmail(),
	                employee.getPassword(),
	                employee.getRoles().stream()
	                .map(role -> {
                        String name = role.getName();
                        if (!name.startsWith("ROLE_")) {
                            name = "ROLE_" + name;
                        }
                        return new SimpleGrantedAuthority(name);
                        })
	                    .collect(Collectors.toList())
	            );
	        
	    }
	

}
