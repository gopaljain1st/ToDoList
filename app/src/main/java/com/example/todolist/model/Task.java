package com.example.todolist.model;

public class Task {
    private String projectId,name,oName,dueDate,isComplete,taskId;

    public Task(String projectId, String name, String oName, String dueDate, String isComplete, String taskId) {
        this.projectId = projectId;
        this.name = name;
        this.oName = oName;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Task(String projectId, String name, String oName, String dueDate, String isComplete) {
        this.projectId = projectId;
        this.name = name;
        this.oName = oName;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }

    public Task() {
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public Task(String name, String oName, String dueDate) {
        this.name = name;
        this.oName = oName;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
