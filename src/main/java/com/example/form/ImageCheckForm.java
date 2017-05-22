package com.example.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ImageCheckForm {
	@NotNull
	private List<String> imgValue;

	@NotNull
	private String token;
}