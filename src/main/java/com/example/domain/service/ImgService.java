package com.example.domain.service;

import com.example.domain.model.Image;
import com.example.domain.repository.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImgService {
	@Autowired
	ImgRepository repository;

	@Autowired
	PasswordEncoder encoder;

	public Image findImageByImgName(String name){
		return repository.findImageByImgName(name);
	}

	public String findSeedByImgName(String name){
		Image image = repository.findImageByImgName(name);
		return image.getSeed();
	}
}
