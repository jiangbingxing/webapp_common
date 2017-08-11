package com.boco.msgl.returnmessage;

public class FailedMessage extends Message{
	public FailedMessage(String message){
		super.code = -1;
		super.message = message;
	}
}
