package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	@NotNull
	@Size(min = 1, max = 120)
	private String username;
	
	@NotNull
	@Size(min = 12)
	private String password;
}
