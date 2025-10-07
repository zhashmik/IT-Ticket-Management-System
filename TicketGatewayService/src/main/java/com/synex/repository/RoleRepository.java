package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);

}
