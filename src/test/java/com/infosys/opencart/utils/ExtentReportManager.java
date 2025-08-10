package com.infosys.opencart.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;

//java utility for date formatting
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//Apache commons email dependencies (used to send report mail)
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.infosys.opencart.base.BaseTest;


/**
 * ExtentReportManager class implements ITestListener so that it gets notified automatically
 * about the test events like onTestSuccess, onTestFailure, onStart, etc.
 * It creates the extent report, records the logs and anomalies, and at the end sends an email
 * with the report.
 */
public class ExtentReportManager implements ITestListener {

    // Extent Spark Reporter (to generate nice HTML report)
    public ExtentSparkReporter sparkReporter;
    // ExtentReports object
    public ExtentReports extent;
    // Represents a single test in report
    public ExtentTest test;
    
    // report file name
    String repName;

    /**
     * Runs before all tests in a given suite start
     */
    public void onStart(ITestContext testContext) {
        // Generates time stamp for unique report name

		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
    	
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // Initialize spark reporter with report path
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        // Set report details
        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        // Attach reporter with ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add environment/system information
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt", "QA");

        // fetch from testng.xml parameters
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // display included groups
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    /**
     * When test case PASSES
     */
    public void onTestSuccess(ITestResult result) {
        // create a test entry in report
        test = extent.createTest(result.getName());
        // assign groups
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName()+" got executed successfully");
    }

    /**
     * When test case FAILS
     */
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, result.getName()+" got failed");
        // print exception message
        test.log(Status.INFO, result.getThrowable().getMessage());

        // attach screenshot
        try {
            String imgPath = new BaseTest().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * When test case is SKIPPED
     */
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName()+" got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    /**
     * Runs after all tests are finished.
     * Generates report, opens report automatically, and emails the report.
     */
    public void onFinish(ITestContext testContext) {
        // Write results to the report
        extent.flush(); 
        
        // open the report in default browser automatically
        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send email with report attached
        try {
            URL url = new URL("file:///"+pathOfExtentReport);
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("unknown.proxyuser369@gmail.com","tnhf asfx bktt qkzn"));
            email.setSSLOnConnect(true);
            email.setFrom("unknown.proxyuser369@gmail.com"); //Sender
            email.setSubject("Automation Test Results");
            email.setMsg("Please find attached Extent Report.");
            email.addTo("muppana.chiranjeevi369@gmail.com");
            email.attach(url, "extent report", "please check report..."); 
  		    email.send(); // send the email 
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 
}
