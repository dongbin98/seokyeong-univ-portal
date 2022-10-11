package com.dbsh.skup.tmpstructure.model;

import androidx.annotation.Nullable;

public class Login {
	@Nullable
	private String id;
	@Nullable
	private String password;

	/* Constructor */
	public Login() {

	}

	public Login(String id, String password) {
		this.id = id;
		this.password = password;
	}

	/* Getter Setter */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
