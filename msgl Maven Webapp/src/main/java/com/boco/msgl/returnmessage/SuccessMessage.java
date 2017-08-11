package com.boco.msgl.returnmessage;

public class SuccessMessage extends Message{
	public SuccessMessage(String message){
		super.code = 0;
		super.message = message;
	}
}
