package com.example.taskmanageras2;

public class Task {
    private long id;
    private String title;
    private String uuid;
    private String description;
    private String creationDate;
    private String dueDate;
    private String priority;
    private String category;
    private String time;
    private boolean completed;


    public Task() {
        // Default constructor
    }

    public Task(String title, String uuid, String description, String creationDate, String dueDate, String priority, String category, String time, boolean completed) {
        this.title = title;
        this.uuid = uuid;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
        this.time = time;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}