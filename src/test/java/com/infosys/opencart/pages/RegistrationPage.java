package com.infosys.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.infosys.opencart.utils.WaitUtil;

public class RegistrationPage extends BasePage{
	WaitUtil waitUtil;
	public RegistrationPage(WebDriver driver) {
		super(driver);
		this.waitUtil = new WaitUtil(driver);
	
	}
	
	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstname;
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastname;
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;
	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTelephone;
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;
	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtConfirmPassword;
	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkbPolicy;
	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirm;
	
	public void setFirstname(String fname) {
        waitUtil.waitForElementVisible(txtFirstname).sendKeys(fname);
    }

    public void setLastname(String lname) {
        txtLastname.sendKeys(lname);
    }

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setTelephone(String phone) {
        txtTelephone.sendKeys(phone);
    }

    public void setPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        txtConfirmPassword.sendKeys(confirmPassword);
    }

    public void acceptPolicy() {
        chkbPolicy.click();
    }

    public void clickContinue() {
        btnContinue.click();
    }

    public String getConfirmationMessage() {
    	try {
        return waitUtil.waitForElementVisible(msgConfirm).getText();
    	}
    	catch(Exception e){
    		 System.out.println("Confirmation message not found: " + e.getMessage());
    	     return "Confirmation message not displayed.";
    	}
    }
    
    // One combined method for easier usage in tests
    public void register(String fname, String lname, String email, String phone, String password) {
        setFirstname(fname);
        setLastname(lname);
        setEmail(email);
        setTelephone(phone);
        setPassword(password);
        setConfirmPassword(password);
        acceptPolicy();
        clickContinue();
    }

}
