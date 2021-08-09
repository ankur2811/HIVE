package com.example.demo.Models;

import javax.persistence.*;

@Entity
@Table
public class UserTags {
    @Id
    @SequenceGenerator(
            name="usertag_sequence",
            sequenceName = "usertag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usertag_sequence"
    )
    private Long id;
    private Long tagId;
    private Long UserId;

    public UserTags() {
    }

    public UserTags(Long id, Long tagId, Long userId) {
        this.id = id;
        this.tagId = tagId;
        UserId = userId;
    }

    public UserTags(Long tagId, Long userId) {
        this.tagId = tagId;
        UserId = userId;
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

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }
}
