package com.bank.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bank.security.entity.RoleEntity;
import com.bank.security.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.security.exception.RequestException;
import com.bank.security.exception.EntityNotFoundException;
import com.bank.security.domain.Role;
import com.bank.security.domain.User;
import com.bank.security.mapper.RoleMapper;
import com.bank.security.mapper.UserMapper;
import com.bank.security.repository.RoleRepository;
import com.bank.security.repository.UserRepository;

import lombok.AllArgsConstructor;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

	
	UserRepository userRepository;
	
	RoleRepository roleRepository;


	UserMapper userMapper;
	
	RoleMapper roleMapper;

	MessageSource messageSource;

	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User user){
		userRepository.findById(user.getId())
		.ifPresent(entity -> {
			throw new RequestException(messageSource.getMessage("user.exists", new Object[]{user.getId()},
					Locale.getDefault()), HttpStatus.CONFLICT);
		});
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		log.info("Saving new user: {} to database",user.getUsername());
		return userMapper.toUser(userRepository.save(userMapper.fromUser(user)));}

	public Role saveRole(Role role){
		roleRepository.findById(role.getId())
				.ifPresent(entity -> {
					throw new RequestException(messageSource.getMessage("role.exists", new Object[]{role.getId()},
							Locale.getDefault()), HttpStatus.CONFLICT);
				});
		log.info("Saving new role: {} to database",role.getName());
		return roleMapper.toRole(roleRepository.save(roleMapper.fromRole(role)));
	}

	public void addRoleToUser(String username,String roleName){
		log.info("Adding role: {} to user: {}",roleName,username);

		UserEntity userEntity=userRepository.findByUsername(username);
		RoleEntity roleEntity=roleRepository.findByName(roleName);
		userEntity.getRoleEntities().add(roleEntity);
	}
	public User getUser(Long id){
		return userMapper.toUser(userRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException(messageSource.getMessage("user.notfound", new Object[]{id},
						Locale.getDefault()))));
	}

	public List<User> getUsers(){
		log.info("Fetching all users");

		return userRepository.findAll().stream()
				.map(userMapper::toUser)
				.collect(Collectors.toList());
	}

	public User findByUsername(String username){
		log.info("Fetching user: {} ",username);

		return userMapper.toUser(userRepository.findByUsername(username));
	}

	public User updateUser(User user){
		log.info("Update user: {} ",user.getUsername());

		return userRepository.findById(user.getId())
				.map(entity -> {
					return userMapper.toUser(
							userRepository.save(userMapper.fromUser(user)));
				}).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user.notfound", new Object[]{user.getId()},
						Locale.getDefault())));
	}

	public void deleteUser(Long id) {
		try {
			userRepository.deleteById(id);

			log.info("User id: {} has been deleted",id);
		} catch (Exception e) {
			throw new RequestException(messageSource.getMessage("user.errordeletion", new Object[]{id},
					Locale.getDefault()),
					HttpStatus.CONFLICT);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity= userRepository.findByUsername(username);
		if (userEntity==null){
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		}else {
			log.info("User found in the database: {}",username);
		}
		Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();

		userEntity.getRoleEntities().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

		return new org.springframework.security.core.userdetails.User(userEntity.getUsername(),userEntity.getPassword(),authorities);
	}
}
