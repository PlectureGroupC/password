package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ForgotPassForm {
	@NotNull
	@Size(min = 1, max = 100)
	private String mailAddress;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String confirmMail;
	
	@NotNull
	private String[] imgs;
}
