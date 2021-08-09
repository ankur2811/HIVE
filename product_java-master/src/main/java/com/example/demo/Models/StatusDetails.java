package com.example.demo.Models;

import javax.persistence.*;

@Entity
@Table
public class StatusDetails {
    @Id
    @SequenceGenerator(
            name="status_sequence",
            sequenceName = "status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status_sequence"
    )
    private Long statusid;
    @Column(name="status")
    private String status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Login user;

    @ManyToOne(cascade=CascadeType.ALL)
    private Activity activity;
    private Integer points=0;

    private Integer emailSent=0;
    private Boolean reviewdone=false;

    @Column(columnDefinition="TEXT")
    private String review;


    public StatusDetails(Long statusid, String status, Login user, Activity activity, Integer points) {
        this.statusid = statusid;
        this.status = status;
        this.user = user;
        this.activity = activity;
        this.points = points;
    }

    public StatusDetails()
    {

    }

    public StatusDetails(String status, Login user, Activity activity, Integer points) {
        this.status = status;
        this.user = user;
        this.activity = activity;
        this.points = points;
    }

    public Integer getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Integer emailSent) {
        this.emailSent = emailSent;
    }

    public Long getStatusid() {
        return statusid;
    }

    public void setStatusid(Long statusid) {
        this.statusid = statusid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Login getUser() {
        return user;
    }

    public void setUser(Login user) {
        this.user = user;
    }

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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
