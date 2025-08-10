package com.infosys.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0; 
    //  retryCount is static, it is shared across all tests. That means:
    // Once it reaches maxRetryCount (2), no other test will retry again, even if it’s their first failure.
    private static final int maxRetryCount = 2; // Retry 2 times max

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + " | Attempt " + (retryCount + 1));
            return true;
        }
        return false;
    }
}
