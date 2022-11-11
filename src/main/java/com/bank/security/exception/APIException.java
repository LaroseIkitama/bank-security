package com.bank.security.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class APIException {
	String message;
	HttpStatus httpStatus;
	LocalDateTime localDateTime;
}
