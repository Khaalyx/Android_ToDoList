package com.efrei.dev1.projet.model;

public class Task {
    private int id;
    private String task;
    private boolean checked;

    public Task(int id, String task, boolean checked) {
        this.id = id;
        this.task = task;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return " • " + task;
    }
}
