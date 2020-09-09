package com.example.todolist.model;

public class Project
{
    private String projectName,projectDescription,noOfTasks,projectId;

    public Project() {
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Project(String projectName, String projectDescription, String noOfTasks, String projectId) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.noOfTasks = noOfTasks;
        this.projectId = projectId;
    }

    public Project(String projectName, String projectDescription, String noOfTasks) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.noOfTasks = noOfTasks;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getNoOfTasks() {
        return noOfTasks;
    }

    public void setNoOfTasks(String noOfTasks) {
        this.noOfTasks = noOfTasks;
    }
}
