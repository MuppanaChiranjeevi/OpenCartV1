package com.infosys.opencart.tests;


import org.testng.Assert;
import org.testng.annotations.*;

import com.infosys.opencart.base.BaseTest;
import com.infosys.opencart.pages.HomePage;
import com.infosys.opencart.pages.RegistrationPage;
import com.infosys.opencart.utils.RandomDataUtil;

public class RegistrationTest extends BaseTest {
	@Test(description = "Verify that a user can register with valid details", retryAnalyzer = com.infosys.opencart.listeners.RetryAnalyzer.class,
		   groups={"Regression","Master"})
	public void valid_Account_registration_test() {
		logger.info("*********Starting Account Registration*******");
		try {
		HomePage hp = new  HomePage(getDriver());
		logger.info("Home Page Loaded");
		logger.info("Clicking on My Account link");
        hp.clickMyAccount();
        logger.info("Clicking on Register link");
        hp.clickRegister();
		RegistrationPage reg = new  RegistrationPage(getDriver());
		logger.info("Registration Page Loaded");
		logger.info("Registering user with required details");
		reg.register(RandomDataUtil.getFirstName(), RandomDataUtil.getLastName(), RandomDataUtil.getEmail(), RandomDataUtil.getPhoneNumber(), RandomDataUtil.getPassword());
		String confirm_msg = reg.getConfirmationMessage();
		logger.info("Confirmation Message: " + confirm_msg);
		logger.info("Clicking on My Account to Logout");
		hp.clickMyAccount();
		hp.clickLogout();
		logger.info("Validating expected confirmation message");
		Assert.assertEquals(confirm_msg,"Your Account Has Been Created!","Confirmation message does not match!");
		}
		catch (Exception e) {
            logger.error("Test failed due to exception: ", e);
            Assert.fail("Test failed due to unexpected exception: " + e.getMessage());
        }
		logger.info("********* Account Registration Test Completed *********");
	}

}


