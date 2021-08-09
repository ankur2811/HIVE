package com.example.demo.Models;

import javax.persistence.*;


@Entity
@Table
public class ActivityTags {
    @Id
    @SequenceGenerator(
            name="activitytag_sequence",
            sequenceName = "activitytag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activitytag_sequence"
    )
    private Long id;
    private Long tagId;
    private Long activityId;

    public ActivityTags() {
    }

    public ActivityTags(Long id, Long tagId, Long activityId) {
        this.id = id;
        this.tagId = tagId;
        this.activityId = activityId;
    }

    public ActivityTags(Long tagId, Long activityId) {
        this.tagId = tagId;
        this.activityId = activityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
