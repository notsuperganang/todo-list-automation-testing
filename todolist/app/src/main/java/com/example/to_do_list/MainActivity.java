package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskAdapterListener {

    private EditText etNewTask;
    private Button btnAddTask;
    private Button btnClearAll;
    private ListView lvTasks;
    private TextView tvTaskCount;

    private List<Task> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupTaskList();
        setupListeners();
        updateTaskCount();
    }

    private void initViews() {
        etNewTask = findViewById(R.id.et_new_task);
        btnAddTask = findViewById(R.id.btn_add_task);
        btnClearAll = findViewById(R.id.btn_clear_all);
        lvTasks = findViewById(R.id.lv_tasks);
        tvTaskCount = findViewById(R.id.tv_task_count);
    }

    private void setupTaskList() {
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList, this);
        lvTasks.setAdapter(taskAdapter);

        // Add some sample tasks for testing
        addSampleTasks();
    }

    private void addSampleTasks() {
        taskList.add(new Task("Sample Task 1"));
        taskList.add(new Task("Sample Task 2"));
        taskAdapter.notifyDataSetChanged();
        updateTaskCount();
    }

    private void setupListeners() {
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearAllDialog();
            }
        });

        // Handle Enter key press in EditText
        etNewTask.setOnEditorActionListener((v, actionId, event) -> {
            addNewTask();
            return true;
        });
    }

    private void addNewTask() {
        String taskText = etNewTask.getText().toString().trim();

        if (TextUtils.isEmpty(taskText)) {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check for duplicate tasks
        for (Task task : taskList) {
            if (task.getText().equalsIgnoreCase(taskText)) {
                Toast.makeText(this, "Task already exists", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Task newTask = new Task(taskText);
        taskList.add(newTask);
        taskAdapter.notifyDataSetChanged();

        etNewTask.setText("");
        updateTaskCount();

        Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
    }

    private void showClearAllDialog() {
        if (taskList.isEmpty()) {
            Toast.makeText(this, "No tasks to clear", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear All Tasks");
        builder.setMessage("Are you sure you want to delete all tasks?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            clearAllTasks();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }

    private void clearAllTasks() {
        taskList.clear();
        taskAdapter.notifyDataSetChanged();
        updateTaskCount();
        Toast.makeText(this, "All tasks cleared", Toast.LENGTH_SHORT).show();
    }

    private void updateTaskCount() {
        int totalTasks = taskList.size();
        int completedTasks = 0;

        for (Task task : taskList) {
            if (task.isDone()) {
                completedTasks++;
            }
        }

        String countText = "Total Tasks: " + totalTasks + " | Completed: " + completedTasks;
        tvTaskCount.setText(countText);
    }

    // TaskAdapter.TaskAdapterListener implementations
    @Override
    public void onTaskDeleted(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            taskList.remove(task);
            taskAdapter.notifyDataSetChanged();
            updateTaskCount();
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }

    @Override
    public void onTaskStatusChanged(Task task) {
        updateTaskCount();
        String message = task.isDone() ? "Task completed!" : "Task marked as incomplete";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Public methods for testing purposes
    public int getTaskCount() {
        return taskList.size();
    }

    public int getCompletedTaskCount() {
        int count = 0;
        for (Task task : taskList) {
            if (task.isDone()) {
                count++;
            }
        }
        return count;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(taskList);
    }
}