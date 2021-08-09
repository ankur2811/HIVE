package com.example.demo.utilities;

public class NewActivity {
    private Long activityid;
    private String title;
    private String date;
    private String tags;
    private String description;
    private Long creatorid;


    public NewActivity(Long activityid, String title, String date, String tags, String description, Long creatorid) {
        this.activityid = activityid;
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.description = description;
        this.creatorid = creatorid;
    }

    public NewActivity() {
    }

    public Long getActivityid() {
        return activityid;
    }

    public void setActivityid(Long activityid) {
        this.activityid = activityid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }
}

