package com.example.domain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name="accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="mail_address", nullable=false, unique=true)
	private String mailAddress;
	
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