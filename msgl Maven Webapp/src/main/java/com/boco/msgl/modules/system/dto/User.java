package com.boco.msgl.modules.system.dto;

import com.boco.msgl.base.BaseObject;

public class User extends BaseObject{
	private String name;
	private String password;
	private Role role;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
