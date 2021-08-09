package com.example.demo.Models;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table()
public class Login {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String password;
    private String bio;
    private String profilePicture;
    private String DOB;
    private String name;
    private Integer count=0;
    private Integer score=0;
    private String areaofinterest;
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "creatorid")
    List<Activity> activitiesOrganised;

    @OneToMany(mappedBy = "user")
    private List<StatusDetails> statusDetails;

    @OneToMany( mappedBy = "UserId")
   List<UserTags> usertags;

    private String gender="male";

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAreaofinterest() {
        return areaofinterest;
    }

    public void setAreaofinterest(String areaofinterest) {
        this.areaofinterest = areaofinterest;
    }

    public List<StatusDetails> getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(List<StatusDetails> statusDetails) {
        this.statusDetails = statusDetails;
    }

    public List<Activity> getActivitiesOrganised() {
        return activitiesOrganised;
    }

    public void setActivitiesOrganised(List<Activity> activitiesOrganised) {
        this.activitiesOrganised = activitiesOrganised;
    }

    public Login() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserTags> getUsertags() {
        return usertags;
    }

    public void setUsertags(List<UserTags> usertags) {
        this.usertags = usertags;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public Login(String username, String password, String bio, String profilePicture, String DOB, String name, Integer score) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.DOB = DOB;
        this.name = name;
        this.score = score;
    }

    public Login(Long id, String username, String password, String bio, String profilePicture, String DOB, String name, Integer score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.DOB = DOB;
        this.name = name;
        this.score = score;
    }

    public Login(Long id, String username, String password, String bio, String profilePicture, String DOB, String name, Integer score, List<Activity> activitiesOrganised, List<StatusDetails> statusDetails) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.DOB = DOB;
        this.name = name;
        this.score = score;
        this.activitiesOrganised = activitiesOrganised;
        this.statusDetails = statusDetails;
    }

    public Login(Long id, String username, String password, String bio, String profilePicture, String DOB, String name, Integer count, Integer score, String areaofinterest, List<Activity> activitiesOrganised, List<StatusDetails> statusDetails, List<UserTags> usertags, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.DOB = DOB;
        this.name = name;
        this.count = count;
        this.score = score;
        this.areaofinterest = areaofinterest;
        this.activitiesOrganised = activitiesOrganised;
        this.statusDetails = statusDetails;
        this.usertags = usertags;
        this.gender = gender;
    }

    public Login(String password) {
        this.password = password;
    }
}
