package com.example.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="imgs")
@Data
public class Image {
	@Id
	@Column(name="img_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long imgId;
	
	@Column(name="img_name", nullable=false, unique=true)
	private String imgName;
	
	@Column(name="img_seed", unique=true)
	private String imgSeed;

	public String getSeed(){
		return imgSeed;
	}
}
