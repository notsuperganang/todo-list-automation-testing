package com.example.tests.utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";
    
    /**
     * Take screenshot and save to file
     */
    public static String captureScreenshot(AppiumDriver driver, String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Generate timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;
            
            // Take screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            
            FileUtils.copyFile(sourceFile, destFile);
            
            System.out.println("Screenshot captured: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Wait for specified number of seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }
    
    /**
     * Generate random task name for testing
     */
    public static String generateRandomTaskName() {
        String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
        return "Test Task " + timestamp;
    }
    
    /**
     * Get current timestamp as string
     */
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    /**
     * Log test step with timestamp
     */
    public static void logStep(String stepDescription) {
        System.out.println("[" + getCurrentTimestamp() + "] " + stepDescription);
    }
    
    /**
     * Extract number from text (useful for parsing counters)
     */
    public static int extractNumberFromText(String text) {
        String numbers = text.replaceAll("[^0-9]", "");
        return numbers.isEmpty() ? 0 : Integer.parseInt(numbers);
    }
}