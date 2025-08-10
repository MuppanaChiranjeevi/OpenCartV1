package com.infosys.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.infosys.opencart.utils.WaitUtil;

public class MyAccount extends BasePage {
	private WaitUtil waitUtil;
	public MyAccount(WebDriver driver) {
		super(driver);
		this.waitUtil = new WaitUtil(driver);
	}

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement txtmyAccount;
	@FindBy(xpath = "(//a[normalize-space()='Logout'])[2]")
	WebElement linklogout;

	
	public boolean isMyAccountPageReached() {
		 try {
		        return waitUtil.waitForElementVisible(txtmyAccount).isDisplayed();
		    } catch (Exception e) {
		        System.out.println("My Account page not loaded: "+e.getMessage());
		        return false;
		    }

	}
	 public void clickLogout() {
		 waitUtil.waitForElementClickable(linklogout).click();
	  }

}
