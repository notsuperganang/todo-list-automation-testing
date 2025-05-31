package com.example.tests.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.tests.base.BaseTest;
import com.example.tests.utils.TestUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    private static ExtentReports extent;
    private static ExtentTest test;
    
    public void onStart(org.testng.ITestContext context) {
        // Initialize ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("ToDo List Automation Test Report");
        sparkReporter.config().setReportName("Mobile App Testing Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "ToDo List Mobile App");
        extent.setSystemInfo("Testing Framework", "Appium + TestNG");
        extent.setSystemInfo("Platform", "Android");
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Tester", "Automation Test");
    }
    
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.INFO, "Test started: " + result.getMethod().getMethodName());
        test.log(Status.INFO, "Description: " + result.getMethod().getDescription());
    }
    
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }
    
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        test.log(Status.FAIL, "Error: " + result.getThrowable().getMessage());
        
        // Capture screenshot on failure
        if (BaseTest.driver != null) {
            String screenshotPath = TestUtils.captureScreenshot(BaseTest.driver, 
                                                               result.getMethod().getMethodName());
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }
        }
    }
    
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
        test.log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
    }
    
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }
}