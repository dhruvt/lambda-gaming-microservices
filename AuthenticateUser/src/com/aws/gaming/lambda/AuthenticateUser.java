package com.aws.gaming.lambda;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.gaming.lambda.AuthenticateUser.AuthenticateUserRequest;
import com.aws.gaming.lambda.AuthenticateUser.AuthenticateUserResponse;
import com.aws.gaming.lambda.data.UserDAO;
import com.aws.gaming.lambda.data.UserDAOImpl;
import com.aws.gaming.lambda.data.UserData;
import com.aws.gaming.lambda.exception.RegisterUserException;

public class AuthenticateUser implements RequestHandler<AuthenticateUserRequest, AuthenticateUserResponse> {

    @Override
    public AuthenticateUserResponse handleRequest(AuthenticateUserRequest input, Context context) {
    	UserData user=null;
    	
    	StringBuilder sb = new StringBuilder();
        sb.append("Received Input UserName:").append(" UserName:").append(input.getUserName());
        context.getLogger().log(sb.toString());
        
        try{        
	        UserDAO userDao = new UserDAOImpl();
            user = userDao.getUserByUserName(input.getUserName());
            
            if(user!=null){
            	boolean passwordMatch = user.getPasswordHash().equals(getPasswordHash(input.getPassword()));
            	if(passwordMatch)
            		return new AuthenticateUserResponse(user.getUserId(),200);
            	else
            		return new AuthenticateUserResponse(user.getUserId(),401);
            }else{
            	return new AuthenticateUserResponse(null,500);
            }
        }catch (RegisterUserException registerUserException){
        	context.getLogger().log("RegisterUserException: " + registerUserException.getMessage());
        	return new AuthenticateUserResponse(null,500);
        }
        
        
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
    
    
    public static class AuthenticateUserRequest{
    	String userName;
    	String password;
    	
    	
    	public AuthenticateUserRequest(){};
    	
		public AuthenticateUserRequest(String userName, String password) {
			super();
			this.userName = userName;
			this.password = password;
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
			  	
    }
    
    public static class AuthenticateUserResponse{
    	
    	String userId;
    	Integer responseCode;
    	
    	public AuthenticateUserResponse(){};
    	
		public AuthenticateUserResponse(String userId, Integer responseCode) {
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
