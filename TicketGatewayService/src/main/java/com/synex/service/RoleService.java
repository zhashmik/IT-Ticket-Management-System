package com.synex.service;

import com.synex.entity.Role;
import com.synex.repository.RoleRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if(role != null) return role; // already exists
        role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
    
}