package com.example.domain.service;

import com.example.domain.model.Image;
import com.example.domain.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageService {
	@Autowired
	ImageRepository repository;

	public String findImgSeedByImgName(String name){
		Image image = repository.findImageByImgName(name);
		return image.getSeed();
	}
}
