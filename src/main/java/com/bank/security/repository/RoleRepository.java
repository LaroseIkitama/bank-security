package com.bank.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.security.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	RoleEntity findByName(String name);
}
