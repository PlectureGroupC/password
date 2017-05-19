package com.example.domain.repository;

import com.example.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<Image,Integer>{
	Image findImageByImgName(String name);
}
