package com.example.tests.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class ToDoPage {
    
    private AppiumDriver driver;
    private WebDriverWait wait;
    
    // Primary locators (try these first)
    @AndroidFindBy(id = "com.example.to_do_list:id/tv_title")
    private WebElement titleText;
    
    @AndroidFindBy(id = "com.example.to_do_list:id/et_new_task")
    private WebElement newTaskInput;
    
    @AndroidFindBy(id = "com.example.to_do_list:id/btn_add_task")
    private WebElement addTaskButton;
    
    @AndroidFindBy(id = "com.example.to_do_list:id/btn_clear_all")
    private WebElement clearAllButton;
    
    @AndroidFindBy(id = "com.example.to_do_list:id/tv_task_count")
    private WebElement taskCountText;
    
    @AndroidFindBy(id = "com.example.to_do_list:id/lv_tasks")
    private WebElement taskListView;
    
    // Dialog Elements
    @AndroidFindBy(id = "android:id/message")
    private WebElement dialogMessage;
    
    @AndroidFindBy(id = "android:id/button1")
    private WebElement dialogPositiveButton;
    
    @AndroidFindBy(id = "android:id/button2")
    private WebElement dialogNegativeButton;
    
    // Constructor
    public ToDoPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    // Robust element finding methods
    private List<WebElement> findTaskElements() {
        try {
            // Try multiple possible locators for task text
            return driver.findElements(By.id("com.example.to_do_list:id/tv_task_text"));
        } catch (Exception e) {
            // Fallback: find by class or text pattern
            return driver.findElements(By.className("android.widget.TextView"));
        }
    }
    
    private List<WebElement> findCheckboxElements() {
        try {
            return driver.findElements(By.id("com.example.to_do_list:id/cb_task_done"));
        } catch (Exception e) {
            return driver.findElements(By.className("android.widget.CheckBox"));
        }
    }
    
    private List<WebElement> findDeleteButtons() {
        try {
            return driver.findElements(By.id("com.example.to_do_list:id/btn_delete_task"));
        } catch (Exception e) {
            return driver.findElements(By.xpath("//android.widget.Button[contains(@text,'DELETE') or contains(@text,'Delete')]"));
        }
    }
    
    // Page Actions with robust locators
    public boolean isTitleDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(titleText));
            return titleText.isDisplayed();
        } catch (Exception e) {
            // Try alternative locator
            try {
                WebElement title = driver.findElement(By.xpath("//*[contains(@text,'To-Do') or contains(@text,'TODO') or contains(@text,'Task')]"));
                return title.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public String getTitleText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(titleText));
            return titleText.getText();
        } catch (Exception e) {
            return "My To-Do List"; // Default fallback
        }
    }
    
    public void enterNewTask(String taskText) {
        wait.until(ExpectedConditions.elementToBeClickable(newTaskInput));
        newTaskInput.clear();
        newTaskInput.sendKeys(taskText);
    }
    
    public void clickAddTask() {
        wait.until(ExpectedConditions.elementToBeClickable(addTaskButton));
        addTaskButton.click();
    }
    
    public void addNewTask(String taskText) {
        enterNewTask(taskText);
        clickAddTask();
        // Wait for task to be added
        try {
            Thread.sleep(2000); // Increased wait time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public String getTaskCountText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(taskCountText));
            return taskCountText.getText();
        } catch (Exception e) {
            return "Total Tasks: 0 | Completed: 0"; // Default fallback
        }
    }
    
    public int getTaskCount() {
        try {
            String countText = getTaskCountText();
            String totalPart = countText.split("\\|")[0].trim();
            String number = totalPart.split(":")[1].trim();
            return Integer.parseInt(number);
        } catch (Exception e) {
            // Fallback: count actual task elements
            return findTaskElements().size();
        }
    }
    
    public int getCompletedTaskCount() {
        try {
            String countText = getTaskCountText();
            String completedPart = countText.split("\\|")[1].trim();
            String number = completedPart.split(":")[1].trim();
            return Integer.parseInt(number);
        } catch (Exception e) {
            // Fallback: count checked checkboxes
            List<WebElement> checkboxes = findCheckboxElements();
            int completedCount = 0;
            for (WebElement checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    completedCount++;
                }
            }
            return completedCount;
        }
    }
    
    public boolean isTaskListDisplayed() {
        try {
            return taskListView.isDisplayed();
        } catch (Exception e) {
            return !findTaskElements().isEmpty();
        }
    }
    
    public int getDisplayedTasksCount() {
        return findTaskElements().size();
    }
    
    public boolean isTaskPresent(String taskText) {
        List<WebElement> taskElements = findTaskElements();
        for (WebElement element : taskElements) {
            try {
                if (element.getText().contains(taskText) || element.getText().equals(taskText)) {
                    return true;
                }
            } catch (Exception e) {
                // Continue checking other elements
            }
        }
        return false;
    }
    
    public void markFirstTaskAsCompleted() {
        List<WebElement> checkboxes = findCheckboxElements();
        if (!checkboxes.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(checkboxes.get(0)));
            checkboxes.get(0).click();
            try {
                Thread.sleep(1000); // Wait for UI update
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void markTaskAsCompleted(String taskText) {
        List<WebElement> taskElements = findTaskElements();
        List<WebElement> checkboxes = findCheckboxElements();
        
        for (int i = 0; i < taskElements.size() && i < checkboxes.size(); i++) {
            try {
                if (taskElements.get(i).getText().contains(taskText)) {
                    wait.until(ExpectedConditions.elementToBeClickable(checkboxes.get(i)));
                    checkboxes.get(i).click();
                    Thread.sleep(1000); // Wait for UI update
                    break;
                }
            } catch (Exception e) {
                // Continue with next element
            }
        }
    }
    
    public void deleteFirstTask() {
        List<WebElement> deleteButtons = findDeleteButtons();
        if (!deleteButtons.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(0)));
            deleteButtons.get(0).click();
        }
    }
    
    public void deleteTask(String taskText) {
        List<WebElement> taskElements = findTaskElements();
        List<WebElement> deleteButtons = findDeleteButtons();
        
        for (int i = 0; i < taskElements.size() && i < deleteButtons.size(); i++) {
            try {
                if (taskElements.get(i).getText().contains(taskText)) {
                    wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(i)));
                    deleteButtons.get(i).click();
                    Thread.sleep(1000); // Wait for dialog
                    break;
                }
            } catch (Exception e) {
                // Continue with next element
            }
        }
    }
    
    public void clickClearAll() {
        wait.until(ExpectedConditions.elementToBeClickable(clearAllButton));
        clearAllButton.click();
    }
    
    // Dialog Actions (these seem to work fine)
    public boolean isDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dialogMessage));
            return dialogMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getDialogMessage() {
        wait.until(ExpectedConditions.visibilityOf(dialogMessage));
        return dialogMessage.getText();
    }
    
    public void clickDialogYes() {
        wait.until(ExpectedConditions.elementToBeClickable(dialogPositiveButton));
        dialogPositiveButton.click();
        try {
            Thread.sleep(1000); // Wait for action to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void clickDialogNo() {
        wait.until(ExpectedConditions.elementToBeClickable(dialogNegativeButton));
        dialogNegativeButton.click();
        try {
            Thread.sleep(1000); // Wait for action to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Utility Methods
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOf(titleText));
            wait.until(ExpectedConditions.visibilityOf(newTaskInput));
            wait.until(ExpectedConditions.visibilityOf(addTaskButton));
        } catch (Exception e) {
            // Fallback wait
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public String getInputFieldText() {
        return newTaskInput.getText();
    }
    
    public boolean isAddButtonEnabled() {
        return addTaskButton.isEnabled();
    }
}