package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ResetPassForm {
	@NotNull
	@Size(min=12)
	private String password;

	@NotNull
	@Size(min=12)
	private String confirmPass;

	@NotNull
	private String token;
}