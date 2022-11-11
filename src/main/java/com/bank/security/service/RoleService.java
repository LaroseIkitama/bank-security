package com.bank.security.service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.security.domain.Role;
import com.bank.security.exception.EntityNotFoundException;
import com.bank.security.exception.RequestException;
import com.bank.security.mapper.RoleMapper;
import com.bank.security.repository.RoleRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class RoleService {


	RoleRepository roleRepository; 
	RoleMapper roleMapper;
	MessageSource messageSource;

	public Role saveRole(Role role) {
		roleRepository.findById(role.getId())
		.ifPresent(entity -> {
			throw new RequestException(messageSource.getMessage("role.exists", new Object[]{role.getId()},
					Locale.getDefault()), HttpStatus.CONFLICT);
		});
		return roleMapper.toRole(roleRepository.save(roleMapper.fromRole(role)));
	}

	public Role updateRole(Role role){
		return roleRepository.findById(role.getId())
				.map(entity -> {
					return roleMapper.toRole(
							roleRepository.save(roleMapper.fromRole(role)));
				}).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{role.getId()},
						Locale.getDefault())));
	}

	public void deleteRole(Long id) {
		try {
			roleRepository.deleteById(id);
		} catch (Exception e) {
			throw new RequestException(messageSource.getMessage("role.errordeletion", new Object[]{id},
					Locale.getDefault()),
					HttpStatus.CONFLICT);
		}
	}

	public Role getRole(Long id) {
		return roleMapper.toRole(roleRepository.findById(id).orElseThrow(() ->
		new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
				Locale.getDefault()))));
	}

	public List<Role> getRoles() {
		return roleRepository.findAll().stream()
				.map(roleMapper::toRole)
				.collect(Collectors.toList());
	}


}
