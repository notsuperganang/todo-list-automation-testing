package com.example.tests.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
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
        
        // Basic Android Capabilities - sesuai device Anda
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformVersion", platformVersion);
        
        // App Configuration - sesuai APK path Anda
        capabilities.setCapability("app", System.getProperty("user.dir") + appPath);
        capabilities.setCapability("appPackage", "com.example.to_do_list");
        capabilities.setCapability("appActivity", "com.example.to_do_list.MainActivity");
        
        // UiAutomator2 Configuration - compatible dengan API 30
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("newCommandTimeout", 300);
        
        // Additional capabilities untuk Android 11 (API 30)
        capabilities.setCapability("disableWindowAnimation", true);
        capabilities.setCapability("skipServerInstallation", true);
        
        // Initialize Android Driver - Appium 2.18.0 compatible
        URL appiumServer = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(appiumServer, capabilities);
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        System.out.println("Appium driver initialized successfully");
        System.out.println("Session ID: " + driver.getSessionId());
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("Closing the application and driver");
            driver.quit();
        }
    }
    
    protected void waitForElement(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}