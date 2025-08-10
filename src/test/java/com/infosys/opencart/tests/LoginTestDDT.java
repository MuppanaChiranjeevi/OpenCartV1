package com.infosys.opencart.tests;



import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.infosys.opencart.base.BaseTest;
import com.infosys.opencart.pages.HomePage;
import com.infosys.opencart.pages.LoginPage;
import com.infosys.opencart.pages.MyAccount;
import com.infosys.opencart.utils.DataProviderUtility;

public class LoginTestDDT extends BaseTest{
	 private HomePage homePage;
	 private LoginPage loginPage;
	 private MyAccount myAccount;
	@BeforeMethod( groups = {"Datadriven","Master"})
	public void setUpPages() {
	    homePage = new HomePage(getDriver());
	    loginPage = new LoginPage(getDriver());
	    myAccount = new MyAccount(getDriver());
	}
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviderUtility.class, groups = {"Datadriven","Master"})
	public void verify_login_test(String username, String password, String exp) {
	    logger.info("******** Starting User Login Test ********");

	    try {
	        logger.info("Navigating to Login Page via My Account");
	        homePage.clickMyAccount();
	        homePage.clickLogin();

	        logger.info("Attempting login with username: " + username);
	        loginPage.setEmail(username);
	        loginPage.setPassword(password);
	        loginPage.clickLoginbtn();

	        boolean loginSuccessful = myAccount.isMyAccountPageReached();

	        /*Data is valid  - login success - test pass  - logout
							   login failed - test fail

			  Data is invalid - login success - test fail  - logout
				       			login failed - test pass
				       			
		     | **Expected** | **Actual Result** | **Outcome**  | **Action** |
			 | ------------ | ----------------- | ------------ | ---------- |
			 | Valid        | Login Success     | ✅ Test Pass | Logout     |
			 | Valid        | Login Failed      | ❌ Test Fail | No Logout  |
			 | InValid      | Login Success     | ❌ Test Fail | Logout     |
			 | InValid      | Login Failed      | ✅ Test Pass | No Logout  |

	        */
	        if (exp.equalsIgnoreCase("Valid")) {
	            if (loginSuccessful) {
	                logger.info("Login successful as expected for valid credentials");
	                assertTrue(loginSuccessful, "My Account page was not reached for valid credentials: " + username);
	                logger.info("Logging out the user");
	                myAccount.clickLogout();
	            } else {
	                logger.error("Login failed unexpectedly for valid credentials");
	                Assert.fail("Expected login to succeed but it failed for valid user: " + username);
	            }
	        } 
	        else if (exp.equalsIgnoreCase("InValid")) {
	            if (loginSuccessful) {
	                logger.error("Login succeeded unexpectedly for invalid credentials");
	                myAccount.clickLogout(); // logout to reset state
	                Assert.fail("Expected login to fail but it succeeded for invalid user: " + username);
	            } else {
	                logger.info("Login failed as expected for invalid credentials");
	                String actualError = loginPage.getErrorMsg();
	                assertEquals(actualError,
	                        "Warning: No match for E-Mail Address and/or Password.",
	                        "Expected login failure message not shown for invalid credentials: " + username);
	            }
	        }

	    } catch (Exception e) {
	        logger.error("Login test failed due to unexpected exception", e);
	        Assert.fail("Login test failed due to exception: " + e.getMessage());
	    }
	}


}