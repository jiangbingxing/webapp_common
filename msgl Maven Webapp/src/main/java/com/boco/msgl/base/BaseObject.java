package com.boco.msgl.base;

import java.io.Serializable;
import java.util.Date;



public class BaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1547318250574882913L;
	private Integer id;
	private Date lastTime;
	private Integer lastManager;
	public Integer getLastManager() {
		return lastManager;
	}
	public void setLastManager(Integer lastManager) {
		this.lastManager = lastManager;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}
