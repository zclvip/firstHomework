package com.zcl.work.parser.exception;

public class ScriptFormatException extends Exception {
	
	public ScriptFormatException(){
		super();
	}
	
	public ScriptFormatException(String message){
		super(message);
	}
	
	public ScriptFormatException(String message, Throwable cause){
		super(message, cause);
	}
	
}
