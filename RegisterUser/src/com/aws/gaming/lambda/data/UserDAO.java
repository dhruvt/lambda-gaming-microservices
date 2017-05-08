package com.aws.gaming.lambda.data;

import com.aws.gaming.lambda.exception.RegisterUserException;

public interface UserDAO {
	
	public UserData getUserByUserName(String userName) throws RegisterUserException;
	public String saveUser(UserData user) throws RegisterUserException;
	public String updateUser(UserData user) throws RegisterUserException;
	public void deleteUser(UserData user) throws RegisterUserException;

}
