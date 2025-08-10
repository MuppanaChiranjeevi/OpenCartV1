package com.infosys.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.infosys.opencart.utils.WaitUtil;

public class LoginPage extends BasePage{

	private WaitUtil waitUtil;
	public LoginPage(WebDriver driver) {
		super(driver);
		this.waitUtil = new WaitUtil(driver);
	}
	
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;
	@FindBy(css = "input[value='Login']")
	WebElement btnLogin;
	
	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	WebElement errorMsg;
	 public void setEmail(String email) {
	     waitUtil.waitForElementVisible(txtEmail).sendKeys(email);
	  }
	 public void setPassword(String password) {
		 waitUtil.waitForElementVisible(txtPassword).sendKeys(password);
	  }
	 public void clickLoginbtn() {
		 waitUtil.waitForElementClickable(btnLogin).click();
	  }
	 public String getErrorMsg() {
		 return waitUtil.waitForElementVisible(errorMsg).getText();
     }
}
