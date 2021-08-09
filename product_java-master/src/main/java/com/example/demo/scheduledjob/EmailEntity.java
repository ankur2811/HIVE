package com.example.demo.scheduledjob;

import com.example.demo.Models.Activity;

public class EmailEntity {
    private String username;
    private String name;
    private Activity activity;

    public EmailEntity() {
    }

    public EmailEntity(String username, String name, Activity activity) {
        this.username = username;
        this.name = name;
        this.activity = activity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
