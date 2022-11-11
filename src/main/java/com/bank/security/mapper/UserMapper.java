package com.bank.security.mapper;

import org.mapstruct.Mapper;

import com.bank.security.domain.User;
import com.bank.security.entity.UserEntity;

@Mapper
public interface UserMapper {

	User toUser(UserEntity userEntity);
	
	UserEntity fromUser(User user);
}
