package com.example.demo.utilities;

import com.example.demo.Models.Login;
import com.example.demo.Models.Tags;

import java.time.LocalDateTime;
import java.util.List;

public class ShowingActivities {
    private Long activityid;
    private String title;
    private LocalDateTime date;
    private String tags;
    private String description;
    private Long creatorid;
    private Login creator;
    private String creatorName;
    List<ShowingUser>participated;
    List<ShowingUser>invited;
    List<ShowingUser>registered;
    private List<Tags> tg;
    private List<String>tagList;
    private String status;

    private String creatorPic;
    private boolean attendenceMarked;
    private String ActivityPic;
    private String review;
    private Boolean reviewdone;
    private String morePics;

    public Boolean getReviewdone() {
        return reviewdone;
    }

    public void setReviewdone(Boolean reviewdone) {
        this.reviewdone = reviewdone;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getMorePics() {
        return morePics;
    }

    public void setMorePics(String morePics) {
        this.morePics = morePics;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public ShowingActivities() {
    }

    public ShowingActivities(Long activityid, String title, LocalDateTime date, String tags, String description, Long creatorid, Login creator, List<ShowingUser> participated, List<ShowingUser> invited, List<ShowingUser> registered) {
        this.activityid = activityid;
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.description = description;
        this.creatorid = creatorid;
        this.creator = creator;
        this.participated = participated;
        this.invited = invited;
        this.registered = registered;
    }

    public List<Tags> getTg() {
        return tg;
    }

    public void setTg(List<Tags> tg) {
        this.tg = tg;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public Login getCreator() {
        return creator;
    }

    public void setCreator(Login creator) {
        this.creator = creator;
    }

    public List<ShowingUser> getParticipated() {
        return participated;
    }

    public void setParticipated(List<ShowingUser> participated) {
        this.participated = participated;
    }

    public List<ShowingUser> getInvited() {
        return invited;
    }

    public void setInvited(List<ShowingUser> invited) {
        this.invited = invited;
    }

    public List<ShowingUser> getRegistered() {
        return registered;
    }

    public void setRegistered(List<ShowingUser> registered) {
        this.registered = registered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatorPic() {
        return creatorPic;
    }

    public void setCreatorPic(String creatorPic) {
        this.creatorPic = creatorPic;
    }

    public boolean isAttendenceMarked() {
        return attendenceMarked;
    }

    public void setAttendenceMarked(boolean attendenceMarked) {
        this.attendenceMarked = attendenceMarked;
    }

    public String getActivityPic() {
        return ActivityPic;
    }

    public void setActivityPic(String activityPic) {
        ActivityPic = activityPic;
    }
}
