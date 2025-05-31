package com.example.to_do_list;

public class Task {
    private String text;
    private boolean isDone;
    private long id;

    public Task(String text) {
        this.text = text;
        this.isDone = false;
        this.id = System.currentTimeMillis();
    }

    public Task(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
        this.id = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return text;
    }
}