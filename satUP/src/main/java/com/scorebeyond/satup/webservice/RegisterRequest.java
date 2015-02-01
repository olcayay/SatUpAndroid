package com.scorebeyond.satup.webservice;

public class RegisterRequest {
	String email;
	String password;
	String username;

	public RegisterRequest(String email, String password, String username) {
		this.email = email;
		this.password = password;
		this.username = username;
	}
}