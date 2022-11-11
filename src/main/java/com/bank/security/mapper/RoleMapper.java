package com.bank.security.mapper;

import org.mapstruct.Mapper;

import com.bank.security.domain.Role;
import com.bank.security.entity.RoleEntity;

@Mapper
public interface RoleMapper {

	Role toRole(RoleEntity roleEntity);
	
	RoleEntity fromRole(Role role);
}
