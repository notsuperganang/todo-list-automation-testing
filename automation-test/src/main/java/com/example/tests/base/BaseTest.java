package com.example.tests.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    
    public static AppiumDriver driver;
    
    @BeforeClass
    @Parameters({"deviceName", "platformVersion", "appPath"})
    public void setUp(String deviceName, String platformVersion, String appPath) throws MalformedURLException {
        System.out.println("Setting up Appium test environment...");
        System.out.println("Target Device: " + deviceName);
        System.out.println("Android Version: " + platformVersion);
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        // Basic Android Capabilities
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformVersion", platformVersion);
        
        // App Configuration
        capabilities.setCapability("app", System.getProperty("user.dir") + appPath);
        capabilities.setCapability("appPackage", "com.example.to_do_list");
        capabilities.setCapability("appActivity", "com.example.to_do_list.MainActivity");
        
        // UiAutomator2 Configuration - enhanced for stability
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("newCommandTimeout", 600); // Increased timeout
        
        // Enhanced capabilities for better stability
        capabilities.setCapability("disableWindowAnimation", true);
        capabilities.setCapability("skipServerInstallation", true);
        capabilities.setCapability("ignoreUnimportantViews", false); // Better element detection
        capabilities.setCapability("waitForIdleTimeout", 0); // Disable wait for idle
        capabilities.setCapability("waitForQuiescence", false); // Disable quiescence wait
        
        // Android specific optimizations
        capabilities.setCapability("uiautomator2ServerLaunchTimeout", 60000);
        capabilities.setCapability("uiautomator2ServerInstallTimeout", 60000);
        capabilities.setCapability("adbExecTimeout", 60000);
        
        // Element finding strategy
        capabilities.setCapability("shouldUseSingletonTestManager", false);
        capabilities.setCapability("elementResponseAttributes", "name,text,className,resourceId");
        
        try {
            // Initialize Android Driver - using URI.create to avoid deprecation warning
            URL appiumServer = URI.create("http://127.0.0.1:4723").toURL();
            driver = new AndroidDriver(appiumServer, capabilities);
            
            // Set enhanced timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            
            System.out.println("Appium driver initialized successfully");
            System.out.println("Session ID: " + driver.getSessionId());
            
            // Wait for app to fully load
            Thread.sleep(5000);
            
        } catch (Exception e) {
            System.err.println("Failed to initialize Appium driver: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                System.out.println("Closing the application and driver");
                driver.quit();
                System.out.println("Driver closed successfully");
            } catch (Exception e) {
                System.err.println("Error closing driver: " + e.getMessage());
            }
        }
    }
    
    protected void waitForElement(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }
}