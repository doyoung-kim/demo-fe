package com.example.demofe.model;

import lombok.Data;

/**
 * AppUser
 */
@Data
public class AppUser {
	
	private String id; 	
	private String idNo;
	private String password;
	private String memberName;	 
	private String email;
	private String sex; 
	private Integer age;
	private String role;

}
