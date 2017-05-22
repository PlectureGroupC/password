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

	@Column(name="imgHash", nullable=false)
	private String imgHash;
	
	@Column(name="enabled")
	private boolean enabled;
	//accountロック
	
	@Column(name="created_on")
	private Date createdOn;
	//account生成時の時間、resetToken発行の時間
	
	@Column(name = "last_login")
	private Date lastLogin;
	
	@Column(name="reset_token")
	private String resetToken;
	//メール送る時のトークン

}