package com.boco.msgl.returnmessage;

public class Message {
	protected Integer code;
	protected String message;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "{\"code\":\"" + code + "\",\"message\":\"" + message + "\"} ";
	}
}
