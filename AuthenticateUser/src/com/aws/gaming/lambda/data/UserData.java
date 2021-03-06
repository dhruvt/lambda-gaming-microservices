package com.aws.gaming.lambda.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="User")
public class UserData {
	
	private String userId;
	private String userName;
	private String passwordHash;
	private String openIdToken;
	private String emailAddress;
	
    @DynamoDBAttribute(attributeName="userid")  
    @DynamoDBAutoGeneratedKey
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    @DynamoDBHashKey(attributeName="username")  
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@DynamoDBAttribute(attributeName="passwordhash")
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	@DynamoDBAttribute(attributeName="openidtoken")
	public String getOpenIdToken() {
		return openIdToken;
	}
	public void setOpenIdToken(String openIdToken) {
		this.openIdToken = openIdToken;
	}
	@DynamoDBAttribute(attributeName="emailaddress")
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public UserData(String userId, String userName, String passwordHash, String openIdToken, String emailAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passwordHash = passwordHash;
		this.openIdToken = openIdToken;
		this.emailAddress = emailAddress;
	}
	
	public UserData(){
		super();
	}

}
