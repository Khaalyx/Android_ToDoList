package com.efrei.dev1.projet.model;

public class ToDoList {
    private int id;
    private String title;
    private Task[] tasks;

    public ToDoList(int id, String title, Task[] tasks) {
        this.id = id;
        this.title = title;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Task[] getTasks() {
        return tasks;
    }
}
