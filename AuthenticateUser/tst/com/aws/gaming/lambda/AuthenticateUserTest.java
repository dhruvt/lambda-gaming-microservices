package com.aws.gaming.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.aws.gaming.lambda.AuthenticateUser.AuthenticateUserRequest;
import com.aws.gaming.lambda.AuthenticateUser.AuthenticateUserResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AuthenticateUserTest {

    private static AuthenticateUserRequest goodInput;
    private static AuthenticateUserRequest badInput;

    @BeforeClass
    public static void createInput() throws IOException {
        goodInput = new AuthenticateUserRequest("testUser","password123");
        badInput = new AuthenticateUserRequest("invalidUser","password123");
    }

    private Context createContext() {
        TestContext testContext = new TestContext();

        // TODO: customize your context here if needed.
        testContext.setFunctionName("AuthenticateUser");
        testContext.setMemoryLimitInMB(128);
        testContext.setLogGroupName("AuthenticateUserLogStream");
        testContext.setAwsRequestId("41C359C079CBAFCF");

        return testContext;
    }

    @Test
    public void testValidRegisterUser() {
    	AuthenticateUser handler = new AuthenticateUser();
        Context context = createContext();

        AuthenticateUserResponse response = handler.handleRequest(goodInput, context);
        
        if (response != null) {
        	Assert.assertTrue(response.getResponseCode().equals(200));
        }
    }
    
    @Test
    public void testInvalidRegisterUser() {
    	AuthenticateUser handler = new AuthenticateUser();
        Context context = createContext();

        AuthenticateUserResponse response = handler.handleRequest(badInput, context);
        
        if (response != null) {
        	Assert.assertTrue(response.getResponseCode().equals(500));
        }
    }
}
