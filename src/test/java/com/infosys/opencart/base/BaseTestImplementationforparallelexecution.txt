package com.infosys.opencart.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.infosys.opencart.utils.ConfigReader;

public class BaseTest {
	// Thread-safe WebDriver
	// Using ThreadLocal to ensure each thread gets its own isolated WebDriver instance.
	// This is essential when running tests in parallel to avoid driver conflicts between threads.
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// Logger instance to log test execution details
	public Logger logger;

	/**
	 * Getter method to retrieve the WebDriver instance for the current thread.
	 * ThreadLocal.get() returns the WebDriver that was set for the currently executing thread.
	 */
	public static WebDriver getDriver() {
	    return driver.get();
	}

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "os"})
    public void setup(String browser, String os) {
        logger = LogManager.getLogger(this.getClass());
        ConfigReader.loadConfig();
        String executionEnv = ConfigReader.get("execution_env");
        logger.info("Execution environment: " + executionEnv);

        WebDriver localDriver = null;

        if (executionEnv.equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            // Set platform
            switch (os.toLowerCase()) {
                case "windows":
                    capabilities.setPlatform(Platform.WIN11);
                    break;
                case "linux":
                    capabilities.setPlatform(Platform.LINUX);
                    break;
                case "mac":
                    capabilities.setPlatform(Platform.MAC);
                    break;
                default:
                    logger.error("Unsupported OS: " + os);
                    return;
            }

            // Set browser
            switch (browser.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                default:
                    logger.error("Unsupported browser: " + browser);
                    return;
            }

            try {
                localDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                logger.info("Started remote WebDriver: " + browser + " on " + os);
            } catch (MalformedURLException e) {
                logger.error("Remote URL malformed", e);
            }

        } else {
            // Local execution
            localDriver = DriverFactory.initDriver(browser);
            logger.info("Started local WebDriver: " + browser);
        }

        // Common setup
        localDriver.manage().deleteAllCookies();
        localDriver.manage().window().maximize();
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Set thread-safe driver
        driver.set(localDriver);

        // Navigate to base URL
        String url = ConfigReader.get("baseURL");
        getDriver().get(url);
        logger.info("Navigated to: " + url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Closing the browser.");
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();  // clean up the thread-local instance
        }
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String targetPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + tname + "_" + timeStamp + ".png";
        File target = new File(targetPath);
        FileUtils.copyFile(source, target);
        return targetPath;
    }
}
