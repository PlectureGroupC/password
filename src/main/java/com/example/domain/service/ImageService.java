package com.example.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.model.Image;
import com.example.domain.repository.ImageRepository;

@Service
@Transactional
public class ImageService {
	@Autowired
	ImageRepository imgRepository;

	public List<Image> findAll() {
		return imgRepository.findAll();
	}

	public Image findOne(Long id) {
		return imgRepository.findOne(id);
	}

	public Image create(Image img) {
		return imgRepository.saveAndFlush(img);
	}

	public Image update(Image img) {
		return imgRepository.save(img);
	}

	public void delete(Long id) {
		imgRepository.delete(id);
	}

	public String findImgSeedByImgName(String name){
		Image image = imgRepository.findImageByImgName(name);
		return image.getImgSeed();
	}
}