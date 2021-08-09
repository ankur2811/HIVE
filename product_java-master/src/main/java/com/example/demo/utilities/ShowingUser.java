package com.example.demo.utilities;

import com.example.demo.Models.Activity;
import com.example.demo.Models.Tags;
import com.example.demo.Models.UserTags;

import java.util.List;

public class ShowingUser {
    private Long id;
    private String username;
    private String bio;
    private String profilePicture;
    private String DOB;
    private String name;
    private Integer score;
    private String tags;
    private Integer organisedCount;
    private List<Activity>invited;
    private List<Activity>participated;
    private List<Activity>cancelled;
    private List<Activity>registered;
    private List<Activity>organised;
    private List<ShowingActivities> participatedactivities;
    private List<Tags> tg;
    private List<String>tagList;
    private String gender;
    private String review;

    public List<ShowingActivities> getParticipatedactivities() {
        return participatedactivities;
    }

    public void setParticipatedactivities(List<ShowingActivities> participatedactivities) {
        this.participatedactivities = participatedactivities;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getGender() {
        return gender;
    }

    public Integer getOrganisedCount() {
        return organisedCount;
    }

    public void setOrganisedCount(Integer organisedCount) {
        this.organisedCount = organisedCount;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public ShowingUser(Long id, String username,String bio, String profilePicture, String DOB, String name, Integer score, List<Activity> invited, List<Activity> participated, List<Activity> cancelled, List<Activity> registered, List<Activity> organised) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.DOB = DOB;
        this.name = name;
        this.score = score;
        this.invited = invited;
        this.participated = participated;
        this.cancelled = cancelled;
        this.registered = registered;
        this.organised=organised;
    }

    public ShowingUser(Long id, String username, String bio, String name) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.name = name;
        this.tg = tg;
    }

    public ShowingUser() {
    }

    public List<Tags> getTg() {
        return tg;
    }

    public void setTg(List<Tags> tg) {
        this.tg = tg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public List<Activity> getOrganised() {
        return organised;
    }

    public void setOrganised(List<Activity> organised) {
        this.organised = organised;
    }

    public void setUsername(String username) {
        this.username = username;
    }
/*
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
*/
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Activity> getInvited() {
        return invited;
    }

    public void setInvited(List<Activity> invited) {
        this.invited = invited;
    }

    public List<Activity> getParticipated() {
        return participated;
    }

    public void setParticipated(List<Activity> participated) {
        this.participated = participated;
    }

    public List<Activity> getCancelled() {
        return cancelled;
    }

    public void setCancelled(List<Activity> cancelled) {
        this.cancelled = cancelled;
    }

    public List<Activity> getRegistered() {
        return registered;
    }

    public void setRegistered(List<Activity> registered) {
        this.registered = registered;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
