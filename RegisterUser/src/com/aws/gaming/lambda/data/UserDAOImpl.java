package com.aws.gaming.lambda.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.aws.gaming.lambda.exception.RegisterUserException;



public class UserDAOImpl implements UserDAO {
	

	@Override
	public UserData getUserByUserName(String userName) throws RegisterUserException{
		
		try{
			AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			DynamoDBMapper ddbMapper = new DynamoDBMapper(ddbClient);
			UserData user = ddbMapper.load(UserData.class, userName);		
			return user!=null?user:null;
		}catch(Exception e){
			throw new RegisterUserException(e.toString());
		}
	}

	@Override
	public String saveUser(UserData user) throws RegisterUserException {
		try{
			AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			DynamoDBMapper ddbMapper = new DynamoDBMapper(ddbClient);
			ddbMapper.save(user);
			return user.getUserId();
		}catch(Exception e){
			throw new RegisterUserException(e.toString());
		}
	}

	@Override
	public String updateUser(UserData user) throws RegisterUserException {
		try{
			AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			DynamoDBMapper ddbMapper = new DynamoDBMapper(ddbClient);
			ddbMapper.save(user);
			
	        DynamoDBMapperConfig config = new DynamoDBMapperConfig.Builder().withConsistentReads(ConsistentReads.CONSISTENT).build();
			UserData updatedUser = ddbMapper.load(UserData.class, user.getUserName(),config);
	        return updatedUser.getUserId();
		}catch(Exception e){
			throw new RegisterUserException(e.toString());
		}
	}

	@Override
	public void deleteUser(UserData user) throws RegisterUserException {
		try{
			AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			DynamoDBMapper ddbMapper = new DynamoDBMapper(ddbClient);
			ddbMapper.delete(user);
		}catch(Exception e){
			throw new RegisterUserException(e.toString());
		}
	}

}
