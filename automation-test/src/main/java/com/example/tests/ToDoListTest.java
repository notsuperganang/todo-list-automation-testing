package com.example.tests;

import com.example.tests.base.BaseTest;
import com.example.tests.pages.ToDoPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToDoListTest extends BaseTest {
    
    private ToDoPage toDoPage;
    
    @BeforeMethod
    public void setUpPage() {
        toDoPage = new ToDoPage(driver);
        toDoPage.waitForPageLoad();
        
        // Clear any existing tasks to start with clean state
        if (toDoPage.getTaskCount() > 0) {
            toDoPage.clickClearAll();
            if (toDoPage.isDialogDisplayed()) {
                toDoPage.clickDialogYes();
            }
            waitForElement(3);
        }
        
        System.out.println("Starting test with " + toDoPage.getTaskCount() + " tasks");
    }
    
    @Test(priority = 1, description = "Verify app launches successfully and displays main elements")
    public void testAppLaunch() {
        System.out.println("Test: App Launch Verification");
        
        // Verify title is displayed
        Assert.assertTrue(toDoPage.isTitleDisplayed(), "App title should be displayed");
        
        // Verify title text
        String expectedTitle = "My To-Do List";
        String actualTitle = toDoPage.getTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "Title text should match expected value");
        
        // Verify input field is displayed
        Assert.assertTrue(toDoPage.isTaskListDisplayed(), "Task list should be displayed");
        
        // Verify add button is enabled
        Assert.assertTrue(toDoPage.isAddButtonEnabled(), "Add button should be enabled");
        
        System.out.println("✓ App launched successfully with all main elements visible");
    }
    
    @Test(priority = 2, description = "Test adding a new task")
    public void testAddNewTask() {
        System.out.println("Test: Add New Task");
        
        String newTask = "Test Task - Automation";
        int initialTaskCount = toDoPage.getTaskCount();
        
        // Add new task
        toDoPage.addNewTask(newTask);
        waitForElement(3); // Wait for task to appear
        
        // Debug: print current tasks
        toDoPage.printCurrentTasks();
        
        // Verify task was added
        Assert.assertTrue(toDoPage.isTaskPresent(newTask), "New task should be present in the list");
        
        // Verify task count increased
        int finalTaskCount = toDoPage.getTaskCount();
        Assert.assertEquals(finalTaskCount, initialTaskCount + 1, "Task count should increase by 1");
        
        System.out.println("✓ New task added successfully: " + newTask);
    }
    
    @Test(priority = 3, description = "Test adding multiple tasks")
    public void testAddMultipleTasks() {
        System.out.println("Test: Add Multiple Tasks");
        
        String[] tasks = {
            "Task 1 - Study Appium",
            "Task 2 - Write Test Cases", 
            "Task 3 - Create Presentation"
        };
        
        int initialTaskCount = toDoPage.getTaskCount();
        
        // Add multiple tasks with proper waits
        for (String task : tasks) {
            System.out.println("Adding task: " + task);
            toDoPage.addNewTask(task);
            waitForElement(3); // Increased wait between additions
            
            // Verify each task was added before continuing
            Assert.assertTrue(toDoPage.isTaskPresent(task), "Task should be present after adding: " + task);
        }
        
        // Debug: print all current tasks
        toDoPage.printCurrentTasks();
        
        // Verify all tasks were added
        for (String task : tasks) {
            Assert.assertTrue(toDoPage.isTaskPresent(task), "Task should be present: " + task);
        }
        
        // Verify task count
        int finalTaskCount = toDoPage.getTaskCount();
        Assert.assertEquals(finalTaskCount, initialTaskCount + tasks.length, 
                           "Task count should increase by " + tasks.length);
        
        System.out.println("✓ Multiple tasks added successfully");
    }
    
    @Test(priority = 4, description = "Test marking task as completed")
    public void testMarkTaskCompleted() {
        System.out.println("Test: Mark Task as Completed");
        
        String testTask = "Task to Complete";
        
        // Add a task first
        toDoPage.addNewTask(testTask);
        waitForElement(3);
        
        // Verify task was added
        Assert.assertTrue(toDoPage.isTaskPresent(testTask), "Task should exist before marking as completed");
        
        int initialCompletedCount = toDoPage.getCompletedTaskCount();
        System.out.println("Initial completed count: " + initialCompletedCount);
        
        // Mark task as completed
        toDoPage.markTaskAsCompleted(testTask);
        waitForElement(4); // Increased wait for UI update
        
        // Verify completed count increased
        int finalCompletedCount = toDoPage.getCompletedTaskCount();
        System.out.println("Final completed count: " + finalCompletedCount);
        
        Assert.assertEquals(finalCompletedCount, initialCompletedCount + 1, 
                           "Completed task count should increase by 1");
        
        System.out.println("✓ Task marked as completed successfully");
    }
    
    @Test(priority = 5, description = "Test deleting a specific task")
    public void testDeleteSpecificTask() {
        System.out.println("Test: Delete Specific Task");
        
        String taskToDelete = "Task to Delete - Test";
        
        // Add a task first
        toDoPage.addNewTask(taskToDelete);
        waitForElement(3);
        
        // Verify task exists before deletion
        Assert.assertTrue(toDoPage.isTaskPresent(taskToDelete), "Task should exist before deletion");
        
        int initialTaskCount = toDoPage.getTaskCount();
        
        // Delete the task
        toDoPage.deleteTask(taskToDelete);
        waitForElement(2);
        
        // Handle confirmation dialog
        if (toDoPage.isDialogDisplayed()) {
            Assert.assertEquals(toDoPage.getDialogMessage(), "Are you sure you want to delete this task?", 
                               "Delete confirmation dialog should appear");
            toDoPage.clickDialogYes();
            waitForElement(3); // Wait for deletion to complete
        }
        
        // Verify task was deleted
        Assert.assertFalse(toDoPage.isTaskPresent(taskToDelete), "Task should be deleted from the list");
        
        // Verify task count decreased
        int finalTaskCount = toDoPage.getTaskCount();
        Assert.assertEquals(finalTaskCount, initialTaskCount - 1, "Task count should decrease by 1");
        
        System.out.println("✓ Task deleted successfully: " + taskToDelete);
    }
    
    @Test(priority = 6, description = "Test delete task with confirmation dialog - Cancel")
    public void testDeleteTaskCancel() {
        System.out.println("Test: Delete Task - Cancel Operation");
        
        String taskToKeep = "Task to Keep - Cancel Test";
        
        // Add a task first
        toDoPage.addNewTask(taskToKeep);
        waitForElement(3);
        
        // Verify task exists
        Assert.assertTrue(toDoPage.isTaskPresent(taskToKeep), "Task should exist before delete attempt");
        
        int initialTaskCount = toDoPage.getTaskCount();
        
        // Attempt to delete but cancel
        toDoPage.deleteTask(taskToKeep);
        waitForElement(2);
        
        // Handle confirmation dialog - click No
        if (toDoPage.isDialogDisplayed()) {
            toDoPage.clickDialogNo();
            waitForElement(2);
        }
        
        // Verify task still exists
        Assert.assertTrue(toDoPage.isTaskPresent(taskToKeep), "Task should still exist after canceling delete");
        
        // Verify task count unchanged
        int finalTaskCount = toDoPage.getTaskCount();
        Assert.assertEquals(finalTaskCount, initialTaskCount, "Task count should remain the same");
        
        System.out.println("✓ Delete operation canceled successfully");
    }
    
    @Test(priority = 7, description = "Test clear all tasks functionality")
    public void testClearAllTasks() {
        System.out.println("Test: Clear All Tasks");
        
        // Add some tasks first
        String[] tasks = {"Clear Test 1", "Clear Test 2", "Clear Test 3"};
        for (String task : tasks) {
            toDoPage.addNewTask(task);
            waitForElement(2);
        }
        
        // Verify tasks exist
        Assert.assertTrue(toDoPage.getTaskCount() >= tasks.length, "Tasks should exist before clearing");
        
        // Clear all tasks
        toDoPage.clickClearAll();
        waitForElement(1);
        
        // Handle confirmation dialog
        if (toDoPage.isDialogDisplayed()) {
            Assert.assertEquals(toDoPage.getDialogMessage(), "Are you sure you want to delete all tasks?", 
                               "Clear all confirmation dialog should appear");
            toDoPage.clickDialogYes();
            waitForElement(3); // Wait for clearing to complete
        }
        
        // Verify all tasks are cleared
        Assert.assertEquals(toDoPage.getTaskCount(), 0, "All tasks should be cleared");
        Assert.assertEquals(toDoPage.getCompletedTaskCount(), 0, "Completed task count should be 0");
        
        System.out.println("✓ All tasks cleared successfully");
    }
    
    @Test(priority = 8, description = "Test input validation - empty task")
    public void testEmptyTaskValidation() {
        System.out.println("Test: Empty Task Input Validation");
        
        int initialTaskCount = toDoPage.getTaskCount();
        
        // Try to add empty task
        toDoPage.enterNewTask("");
        toDoPage.clickAddTask();
        waitForElement(2);
        
        // Verify task count didn't change
        int finalTaskCount = toDoPage.getTaskCount();
        Assert.assertEquals(finalTaskCount, initialTaskCount, 
                           "Task count should not change when adding empty task");
        
        System.out.println("✓ Empty task validation working correctly");
    }
    
    @Test(priority = 9, description = "Test duplicate task validation")
    public void testDuplicateTaskValidation() {
        System.out.println("Test: Duplicate Task Validation");
        
        String duplicateTask = "Duplicate Test Task";
        
        // Add task first time
        toDoPage.addNewTask(duplicateTask);
        waitForElement(3);
        int taskCountAfterFirst = toDoPage.getTaskCount();
        
        // Try to add same task again
        toDoPage.addNewTask(duplicateTask);
        waitForElement(3);
        int taskCountAfterSecond = toDoPage.getTaskCount();
        
        // Verify task count didn't increase
        Assert.assertEquals(taskCountAfterSecond, taskCountAfterFirst, 
                           "Task count should not increase when adding duplicate task");
        
        System.out.println("✓ Duplicate task validation working correctly");
    }
    
    @Test(priority = 10, description = "Test task counter accuracy")
    public void testTaskCounterAccuracy() {
        System.out.println("Test: Task Counter Accuracy");
        
        // Ensure we start with clean state
        if (toDoPage.getTaskCount() > 0) {
            toDoPage.clickClearAll();
            if (toDoPage.isDialogDisplayed()) {
                toDoPage.clickDialogYes();
            }
            waitForElement(3);
        }
        
        // Add tasks and verify counter
        String[] tasks = {"Counter Test 1", "Counter Test 2", "Counter Test 3"};
        
        for (int i = 0; i < tasks.length; i++) {
            toDoPage.addNewTask(tasks[i]);
            waitForElement(3);
            
            Assert.assertEquals(toDoPage.getTaskCount(), i + 1, 
                               "Task count should be " + (i + 1) + " after adding " + (i + 1) + " tasks");
        }
        
        // Mark some as completed and verify counter
        toDoPage.markTaskAsCompleted(tasks[0]);
        waitForElement(3);
        Assert.assertEquals(toDoPage.getCompletedTaskCount(), 1, "Completed count should be 1");
        
        toDoPage.markTaskAsCompleted(tasks[1]);
        waitForElement(3);
        Assert.assertEquals(toDoPage.getCompletedTaskCount(), 2, "Completed count should be 2");
        
        System.out.println("✓ Task counter accuracy verified");
    }
}