package com.boco.msgl.modules.system.dto;

import java.util.List;

import com.boco.msgl.base.BaseObject;



public class Role extends BaseObject{
	
	private String name;
	private String description;
	private List<Module> module;
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Module> getModule() {
		return module;
	}

	public void setModule(List<Module> module) {
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
