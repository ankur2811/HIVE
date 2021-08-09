package com.example.demo.Controller;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import com.example.demo.Service.ActivityService;
import com.example.demo.Service.LoginService;
import com.example.demo.utilities.ShowingUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ActivityRepository activityRepository;

    @MockBean
    LoginRepository loginRepository;

    @MockBean
    ActivityService activityService;

    @MockBean
    TagRepository tagRepository;

    @MockBean
    StatusRepository statusRepository;

    @MockBean
    ActivityTagRepository activityTagRepository;

    List<StatusDetails> statusList1=new ArrayList<>();
    List<StatusDetails>statusList2=new ArrayList<>();
    List<Activity>activitylist1=new ArrayList<>();
    List<Activity>activityList2=new ArrayList<>();
    List<Login>userList1=new ArrayList<>();
    List<Login>userList2=new ArrayList<>();
    LocalDateTime l=LocalDateTime.of(2022,12,12,12,00);
    Login user1=new Login(1L,"abc@gmail.com","pass1","hisbio","hisprofilepic", "dob","abc",0);
    Login user2=new Login(2L,"xyz@gmail.com","pass1","hisbio","hisprofilepic", "dob","abc",0);
    Activity activity1=new Activity(1L,"this is my title",l,"first","this is description",2L);
    Activity activity2=new Activity(2L,"this is my title",l,"first","this is description",2L);
    Activity activity3=new Activity(3l,"this is my title",l,"second","this is description",2L);
    StatusDetails sd1=new StatusDetails(1L,"invited",user1,activity1,0);
    StatusDetails sd2=new StatusDetails(1L,"participated",user1,activity1,0);
    StatusDetails sd3=new StatusDetails(1L,"registered",user1,activity1,0);
    Optional<Login> useroptional1= Optional.of(user1);
    Optional<Login>useroptional2=Optional.of(user2);
    Optional<Activity>activityoptional1= Optional.of(activity1);
    Optional<Activity>activityoptional2=Optional.of(activity2);
    Optional<StatusDetails>statusDetailsOptional1=Optional.ofNullable(sd1);
    List<Login>userList=new ArrayList<>();
    Tags tag1=new Tags(1L,"first");
    Tags tag2=new Tags(2L,"second");
    List<Tags>t=new ArrayList<>();
    Optional userListOptional;
    ActivityTags activityTags1=new ActivityTags(1L,1L);
    ShowingUser showingUser2=new ShowingUser(2L,"abc@gmail.com","this is my bio","abcd");


    @BeforeEach
    void setUp() {
       t.add(tag1);
       t.add(tag2);
       userList.add(user1);
       userList.add(user2);

    }

    @Test
    void testAddActivity() throws Exception {
        MvcResult mvcResult=this.mockMvc.perform(MockMvcRequestBuilders.post("/activity/add/1",activity1)).andReturn();
        Mockito.when(activityService.addActivity(1L,activity1)).thenReturn(activity1);
        //System.out.println(mvcResult.getResponse());
        //assertEquals(mvcResult.getResponse(),activity1);
    }

    @Test
    void getAll() {

    }

    @Test
    void countPast() {
    }

    @Test
    void getActivityById() {
    }

    @Test
    void deleteActivity() {
    }

    @Test
    void getCoursesPaginated() {
    }

    @Test
    void markPresent() {
    }

    @Test
    void getPastActivityByTitle() {
    }

    @Test
    void getTagWiseCount() {
    }

    @Test
    void getYearWiseCount() {
    }
}