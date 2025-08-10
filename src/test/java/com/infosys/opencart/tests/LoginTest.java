package com.infosys.opencart.tests;



import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.infosys.opencart.base.BaseTest;
import com.infosys.opencart.pages.HomePage;
import com.infosys.opencart.pages.LoginPage;
import com.infosys.opencart.pages.MyAccount;
import com.infosys.opencart.utils.ConfigReader;

public class LoginTest extends BaseTest{
	 private HomePage homePage;
	 private LoginPage loginPage;
	 private MyAccount myAccount;
	@BeforeMethod(groups = {"Sanity","Master"})
	public void setUpPages() {
	    homePage = new HomePage(getDriver());
	    loginPage = new LoginPage(getDriver());
	    myAccount = new MyAccount(getDriver());
	}
	@Test(description = "Verify that a user can login with valid credentials", retryAnalyzer = com.infosys.opencart.listeners.RetryAnalyzer.class,
			groups = {"Sanity","Master"})
	public void verify_login_test() {
		logger.info("********Starting User Login Test *******");
		try {
		logger.info("Navigating to Login Page via My Account");
		homePage.clickMyAccount();
		homePage.clickLogin();
		logger.info("Logging in user with valid credentials");
		loginPage.setEmail(ConfigReader.get("email"));
		loginPage.setPassword(ConfigReader.get("password"));
		loginPage.clickLoginbtn();
		logger.info("Asserting My Account page reached after login");
		assertTrue(myAccount.isMyAccountPageReached(), "Expected My Account page was not reached after login");
		logger.info("Logging out the user");
		myAccount.clickLogout();
		}
		catch(Exception e) {
			logger.error("Login failed due to unexpected exception", e);
	        Assert.fail("Login test failed due to exception: " + e.getMessage());
		}
		
	}

}