package com.example.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ResetPassForm {
	@NotNull
	private String password;

	@NotNull
	private String confirmPass;

	@NotNull
	private String token;
}