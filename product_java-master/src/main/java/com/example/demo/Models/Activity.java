package com.example.demo.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Activity {
    @Id
    @SequenceGenerator(
            name="activity_sequence",
            sequenceName = "activity_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activity_sequence"
    )
    private Long activityid;
    private String title;
    private String tags;
    @Column(columnDefinition="TEXT")
    private String description;
    private Long creatorid;
    private LocalDateTime date;
     private String status;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "activity")
    private List<StatusDetails> statusDetails;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany( mappedBy = "activityId")
    List<ActivityTags> activitytags;

    private String activityPic;
    private boolean attendenceMarked=false;

    @Column(columnDefinition="TEXT")
    private String morePics;

    public Activity(String title, LocalDateTime date, String tags, String description, Long creatorid, List<StatusDetails> statusDetails) {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.description = description;
        this.creatorid = creatorid;
        this.statusDetails = statusDetails;
    }

    public Activity(String title, LocalDateTime date, String tags, String description, Long creatorid) {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.description = description;
        this.creatorid = creatorid;
    }

    public Activity() {
    }

    public Activity(Long activityid, String title,LocalDateTime date, String tags, String description, Long creatorid) {
        this.activityid = activityid;
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.description = description;
        this.creatorid = creatorid;
    }

    public Activity(Long activityid, String title, String tags, String description) {
        this.activityid = activityid;
        this.title = title;
        this.tags = tags;
        this.description = description;
    }

    public List<ActivityTags> getActivitytags() {
        return activitytags;
    }

    public void setActivitytags(List<ActivityTags> activitytags) {
        this.activitytags = activitytags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isAttendenceMarked() {
        return attendenceMarked;
    }

    public String getMorePics() {
        return morePics;
    }

    public void setMorePics(String morePics) {
        this.morePics = morePics;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<StatusDetails> getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(List<StatusDetails> statusDetails) {
        this.statusDetails = statusDetails;
    }

    public String getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(String activityPic) {
        this.activityPic = activityPic;
    }

    public boolean getAttendenceMarked() {
        return attendenceMarked;
    }

    public void setAttendenceMarked(boolean attendenceMarked) {
        this.attendenceMarked = attendenceMarked;
    }
}
