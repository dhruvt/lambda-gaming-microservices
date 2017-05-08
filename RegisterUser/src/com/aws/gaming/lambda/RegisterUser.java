package com.aws.gaming.lambda;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.gaming.lambda.RegisterUser.RegisterUserRequest;
import com.aws.gaming.lambda.RegisterUser.RegisterUserResponse;
import com.aws.gaming.lambda.data.UserDAO;
import com.aws.gaming.lambda.data.UserDAOImpl;
import com.aws.gaming.lambda.data.UserData;
import com.aws.gaming.lambda.exception.RegisterUserException;

public class RegisterUser implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {

    @Override
    public RegisterUserResponse handleRequest(RegisterUserRequest input, Context context) {
    	String userId=null;
    	
    	StringBuilder sb = new StringBuilder();
        sb.append("Received Input Email:").append(input.getEmailAddress()).append(" UserName:").append(input.getUserName());
        context.getLogger().log(sb.toString());
        
        try{        
	        UserData user = new UserData();
	        user.setEmailAddress(input.getEmailAddress());
	        user.setUserName(input.getUserName());
	        user.setPasswordHash(getPasswordHash(input.getPassword()));
	      
	        UserDAO userDao = new UserDAOImpl();
            userId = userDao.saveUser(user);
        }catch (RegisterUserException registerUserException){
        	context.getLogger().log("RegisterUserException: " + registerUserException.getMessage());
        	return new RegisterUserResponse(null,500);
        }
        
        return new RegisterUserResponse(userId,200);
    }
    
    public String getPasswordHash(String password){
        String generatedPassword=null;
    	try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
    	return generatedPassword;
    }
    
    public static class RegisterUserRequest{
    	String userName;
    	String password;
    	String emailAddress;
    	
    	public RegisterUserRequest(){};
    	
		public RegisterUserRequest(String userName, String password, String emailAddress) {
			super();
			this.userName = userName;
			this.password = password;
			this.emailAddress = emailAddress;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
    	  	
    }
    
    public static class RegisterUserResponse{
    	
    	String userId;
    	Integer responseCode;
    	
    	public RegisterUserResponse(){};
    	
		public RegisterUserResponse(String userId, Integer responseCode) {
			super();
			this.userId = userId;
			this.responseCode = responseCode;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public Integer getResponseCode() {
			return responseCode;
		}
		public void setResponseCode(Integer responseCode) {
			this.responseCode = responseCode;
		}
    	
    	
    }

}
