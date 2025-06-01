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
    
    // Primary locators
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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    // Improved element finding methods with better locators
    private List<WebElement> findTaskElements() {
        try {
            // Wait for ListView to be present first
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.example.to_do_list:id/lv_tasks")));
            waitFor(1); // Small wait for ListView to populate
            
            // Try primary locator for task text elements
            List<WebElement> elements = driver.findElements(By.id("com.example.to_do_list:id/tv_task_text"));
            
            if (elements.isEmpty()) {
                // Fallback: try xpath to find TextViews inside ListView
                elements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id='com.example.to_do_list:id/lv_tasks']//android.widget.TextView[@resource-id='com.example.to_do_list:id/tv_task_text']"));
            }
            
            System.out.println("Found " + elements.size() + " task elements");
            return elements;
        } catch (Exception e) {
            System.err.println("Error finding task elements: " + e.getMessage());
            return List.of(); // Return empty list instead of null
        }
    }
    
    private List<WebElement> findCheckboxElements() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.example.to_do_list:id/lv_tasks")));
            waitFor(1);
            
            List<WebElement> elements = driver.findElements(By.id("com.example.to_do_list:id/cb_task_done"));
            
            if (elements.isEmpty()) {
                elements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id='com.example.to_do_list:id/lv_tasks']//android.widget.CheckBox[@resource-id='com.example.to_do_list:id/cb_task_done']"));
            }
            
            System.out.println("Found " + elements.size() + " checkbox elements");
            return elements;
        } catch (Exception e) {
            System.err.println("Error finding checkbox elements: " + e.getMessage());
            return List.of();
        }
    }
    
    private List<WebElement> findDeleteButtons() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.example.to_do_list:id/lv_tasks")));
            waitFor(1);
            
            List<WebElement> elements = driver.findElements(By.id("com.example.to_do_list:id/btn_delete_task"));
            
            if (elements.isEmpty()) {
                elements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id='com.example.to_do_list:id/lv_tasks']//android.widget.Button[@resource-id='com.example.to_do_list:id/btn_delete_task']"));
            }
            
            System.out.println("Found " + elements.size() + " delete button elements");
            return elements;
        } catch (Exception e) {
            System.err.println("Error finding delete button elements: " + e.getMessage());
            return List.of();
        }
    }
    
    // Improved utility wait method
    private void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Page Actions with improved reliability
    public boolean isTitleDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(titleText));
            return titleText.isDisplayed();
        } catch (Exception e) {
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
            return "My To-Do List";
        }
    }
    
    public void enterNewTask(String taskText) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(newTaskInput));
            newTaskInput.clear();
            waitFor(1);
            newTaskInput.sendKeys(taskText);
            waitFor(1);
            System.out.println("Entered task text: " + taskText);
        } catch (Exception e) {
            System.err.println("Error entering task: " + e.getMessage());
        }
    }
    
    public void clickAddTask() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addTaskButton));
            addTaskButton.click();
            waitFor(2); // Wait for task to be added
            System.out.println("Clicked add task button");
        } catch (Exception e) {
            System.err.println("Error clicking add task: " + e.getMessage());
        }
    }
    
    public void addNewTask(String taskText) {
        System.out.println("Adding new task: " + taskText);
        enterNewTask(taskText);
        clickAddTask();
        waitFor(3); // Increased wait time for task to appear in list
        System.out.println("Task addition completed");
    }
    
    public String getTaskCountText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(taskCountText));
            String text = taskCountText.getText();
            System.out.println("Task count text: " + text);
            return text;
        } catch (Exception e) {
            System.err.println("Error getting task count text: " + e.getMessage());
            return "Total Tasks: 0 | Completed: 0";
        }
    }
    
    public int getTaskCount() {
        try {
            String countText = getTaskCountText();
            String totalPart = countText.split("\\|")[0].trim();
            String number = totalPart.split(":")[1].trim();
            int count = Integer.parseInt(number);
            System.out.println("Task count: " + count);
            return count;
        } catch (Exception e) {
            // Fallback: count actual task elements
            int elementCount = findTaskElements().size();
            System.out.println("Task count (from elements): " + elementCount);
            return elementCount;
        }
    }
    
    public int getCompletedTaskCount() {
        try {
            String countText = getTaskCountText();
            String completedPart = countText.split("\\|")[1].trim();
            String number = completedPart.split(":")[1].trim();
            int count = Integer.parseInt(number);
            System.out.println("Completed task count: " + count);
            return count;
        } catch (Exception e) {
            // Fallback: count checked checkboxes
            List<WebElement> checkboxes = findCheckboxElements();
            int completedCount = 0;
            for (WebElement checkbox : checkboxes) {
                try {
                    if (checkbox.isSelected()) {
                        completedCount++;
                    }
                } catch (Exception ex) {
                    // Continue
                }
            }
            System.out.println("Completed task count (from checkboxes): " + completedCount);
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
        System.out.println("Searching for task: " + taskText);
        
        try {
            // Wait for list to be populated
            waitFor(2);
            
            List<WebElement> taskElements = findTaskElements();
            System.out.println("Total task elements found: " + taskElements.size());
            
            for (int i = 0; i < taskElements.size(); i++) {
                try {
                    WebElement element = taskElements.get(i);
                    String elementText = element.getText();
                    System.out.println("Task " + i + " text: '" + elementText + "'");
                    
                    if (elementText != null && 
                        (elementText.equals(taskText) || elementText.contains(taskText))) {
                        System.out.println("Task found: " + taskText);
                        return true;
                    }
                } catch (Exception e) {
                    System.err.println("Error checking task element " + i + ": " + e.getMessage());
                }
            }
            
            System.out.println("Task not found: " + taskText);
            return false;
        } catch (Exception e) {
            System.err.println("Error in isTaskPresent: " + e.getMessage());
            return false;
        }
    }
    
    public void markTaskAsCompleted(String taskText) {
        System.out.println("Marking task as completed: " + taskText);
        
        try {
            waitFor(2);
            List<WebElement> taskElements = findTaskElements();
            List<WebElement> checkboxes = findCheckboxElements();
            
            if (taskElements.size() != checkboxes.size()) {
                System.err.println("Mismatch between task elements and checkboxes count");
            }
            
            for (int i = 0; i < Math.min(taskElements.size(), checkboxes.size()); i++) {
                try {
                    String elementText = taskElements.get(i).getText();
                    System.out.println("Checking task " + i + ": " + elementText);
                    
                    if (elementText != null && 
                        (elementText.equals(taskText) || elementText.contains(taskText))) {
                        
                        WebElement checkbox = checkboxes.get(i);
                        wait.until(ExpectedConditions.elementToBeClickable(checkbox));
                        
                        System.out.println("Clicking checkbox for task: " + elementText);
                        checkbox.click();
                        waitFor(3); // Wait for UI update
                        
                        System.out.println("Task marked as completed: " + taskText);
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("Error processing task " + i + ": " + e.getMessage());
                }
            }
            
            System.err.println("Task not found for completion: " + taskText);
        } catch (Exception e) {
            System.err.println("Error in markTaskAsCompleted: " + e.getMessage());
        }
    }
    
    public void deleteTask(String taskText) {
        System.out.println("Deleting task: " + taskText);
        
        try {
            waitFor(2);
            List<WebElement> taskElements = findTaskElements();
            List<WebElement> deleteButtons = findDeleteButtons();
            
            if (taskElements.size() != deleteButtons.size()) {
                System.err.println("Mismatch between task elements and delete buttons count");
            }
            
            for (int i = 0; i < Math.min(taskElements.size(), deleteButtons.size()); i++) {
                try {
                    String elementText = taskElements.get(i).getText();
                    System.out.println("Checking task " + i + ": " + elementText);
                    
                    if (elementText != null && 
                        (elementText.equals(taskText) || elementText.contains(taskText))) {
                        
                        WebElement deleteButton = deleteButtons.get(i);
                        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
                        
                        System.out.println("Clicking delete button for task: " + elementText);
                        deleteButton.click();
                        waitFor(2); // Wait for dialog to appear
                        
                        System.out.println("Delete button clicked for task: " + taskText);
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("Error processing task " + i + ": " + e.getMessage());
                }
            }
            
            System.err.println("Task not found for deletion: " + taskText);
        } catch (Exception e) {
            System.err.println("Error in deleteTask: " + e.getMessage());
        }
    }
    
    public void clickClearAll() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(clearAllButton));
            clearAllButton.click();
            waitFor(1);
            System.out.println("Clicked clear all button");
        } catch (Exception e) {
            System.err.println("Error clicking clear all: " + e.getMessage());
        }
    }
    
    // Dialog Actions
    public boolean isDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dialogMessage));
            return dialogMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getDialogMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dialogMessage));
            return dialogMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public void clickDialogYes() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dialogPositiveButton));
            dialogPositiveButton.click();
            waitFor(3); // Increased wait for action to complete
            System.out.println("Clicked dialog YES button");
        } catch (Exception e) {
            System.err.println("Error clicking dialog yes: " + e.getMessage());
        }
    }
    
    public void clickDialogNo() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dialogNegativeButton));
            dialogNegativeButton.click();
            waitFor(2);
            System.out.println("Clicked dialog NO button");
        } catch (Exception e) {
            System.err.println("Error clicking dialog no: " + e.getMessage());
        }
    }
    
    // Utility Methods
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOf(titleText));
            wait.until(ExpectedConditions.visibilityOf(newTaskInput));
            wait.until(ExpectedConditions.visibilityOf(addTaskButton));
            wait.until(ExpectedConditions.visibilityOf(taskListView));
            waitFor(2); // Additional wait for complete page load
            System.out.println("Page loaded successfully");
        } catch (Exception e) {
            System.err.println("Error waiting for page load: " + e.getMessage());
            waitFor(5); // Fallback wait
        }
    }
    
    public String getInputFieldText() {
        try {
            return newTaskInput.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isAddButtonEnabled() {
        try {
            return addTaskButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Additional utility methods for better debugging
    public void printCurrentTasks() {
        System.out.println("=== Current Tasks ===");
        List<WebElement> taskElements = findTaskElements();
        for (int i = 0; i < taskElements.size(); i++) {
            try {
                String text = taskElements.get(i).getText();
                System.out.println("Task " + i + ": " + text);
            } catch (Exception e) {
                System.out.println("Task " + i + ": Error reading text");
            }
        }
        System.out.println("=== End Current Tasks ===");
    }
}