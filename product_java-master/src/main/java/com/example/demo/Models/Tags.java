package com.example.demo.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table()
public class Tags {
    @Id
    @SequenceGenerator(
            name="tags_sequence",
            sequenceName = "tags_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tags_sequence"
    )
    private Long id;
    private String tag;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tagId")
   List<UserTags> user=new ArrayList<>();

    @OneToMany( mappedBy = "tagId")
    List<ActivityTags>activities=new ArrayList<>();

    public List<UserTags> getUser() {
        return user;
    }

    public void setUser(List<UserTags> user) {
        this.user = user;
    }

    public Tags(String tag) {
        this.tag = tag;
    }

    public Tags(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public Tags() {

    }

    public List<ActivityTags> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityTags> activities) {
        this.activities = activities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
