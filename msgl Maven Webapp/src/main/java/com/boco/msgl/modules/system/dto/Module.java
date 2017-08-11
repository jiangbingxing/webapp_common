package com.boco.msgl.modules.system.dto;

import com.boco.msgl.base.BaseObject;

public class Module extends BaseObject{
	private String name;
	private String url;
	private Integer parentId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
