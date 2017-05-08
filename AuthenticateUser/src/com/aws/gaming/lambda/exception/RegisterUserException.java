package com.aws.gaming.lambda.exception;

public class RegisterUserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4203353353661628107L;

	public RegisterUserException(String message){
		super(message);
	}

}
