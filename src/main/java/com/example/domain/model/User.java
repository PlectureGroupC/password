package com.example.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", updatable=false)
	private long id;
	
	@Column(name="email", nullable=false, unique=true)
	@Email(message="メールアドレスを入力してください")
	@NotEmpty(message="メールアドレスは入力必須です")
	private String email;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name = "last_login")
	private Date lastLogin;
	
	@Column(name="reset_token")
	private String resetToken;
}
