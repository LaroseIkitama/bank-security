package com.bank.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
UserEntity findByUsername(String username);

}
