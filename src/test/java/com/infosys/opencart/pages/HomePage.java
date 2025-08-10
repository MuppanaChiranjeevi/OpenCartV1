package com.infosys.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.infosys.opencart.utils.WaitUtil;

public class HomePage extends BasePage{

	private WaitUtil waitUtil;
	public HomePage(WebDriver driver) {
		super(driver);
		this.waitUtil = new WaitUtil(driver);
	}
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement myAccount;
	@FindBy(xpath="(//a[normalize-space()='Register'])[1]")
	WebElement register;
	@FindBy(xpath="//a[normalize-space()='Login']")
	WebElement login;
	@FindBy(xpath="(//li/a[normalize-space()='Logout'])[1]")
	WebElement logout;
	

	public void clickMyAccount() {
		waitUtil.waitForElementClickable(myAccount).click();
	}
	public void clickRegister() {
		waitUtil.waitForElementClickable(register).click();
	}
	public void clickLogin() {
		waitUtil.waitForElementClickable(login).click();
	}
	public void clickLogout() {
		waitUtil.waitForElementClickable(logout).click();
	}
}
