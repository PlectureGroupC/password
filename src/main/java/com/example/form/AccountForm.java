package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.List;

@Data
public class AccountForm {
	@NotNull
	@Size(min = 1, max = 100)
	private String username;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String mailAddress;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String confirmMail;
	
	@NotNull
	@Size(min = 12, max = 100)
	private String password;
	
	@NotNull
	@Size(min = 12, max = 100)
	private String confirmPass;
	
	@NotNull
	private List<String> imgPath;
}
