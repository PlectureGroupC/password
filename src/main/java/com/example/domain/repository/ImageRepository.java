package com.example.domain.repository;

import com.example.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

//Long or Integer
public interface ImageRepository extends JpaRepository<Image, Long> {
	Image findImageByImgName(String name);
}
