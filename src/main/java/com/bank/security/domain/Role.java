package com.bank.security.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
	private Long id;

	@NotNull(message="The name must not be null")
	private String name;
}
