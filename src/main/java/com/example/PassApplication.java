package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PassApplication {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		SpringApplication.run(PassApplication.class, args);
	}
}

