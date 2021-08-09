package com.example.demo.Service;

import com.example.demo.Models.*;
import com.example.demo.Repository.ActivityRepository;
import com.example.demo.Repository.LoginRepository;
import com.example.demo.Repository.StatusRepository;
import com.example.demo.Repository.TagRepository;
import com.example.demo.utilities.ShowingUser;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    @Mock
    StatusRepository statusRepository;
    @Mock
    LoginRepository loginRepository;
    @Mock
    ActivityRepository activityRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private  TagRepository userTagRepository;
    @InjectMocks
    private LoginService loginService;
    List<StatusDetails> statusList1=new ArrayList<>();
    List<StatusDetails>statusList2=new ArrayList<>();
    List<Activity>activitylist1=new ArrayList<>();
    List<Activity>activityList2=new ArrayList<>();
    List<Login>userList1=new ArrayList<>();
    List<Login>userList2=new ArrayList<>();
    LocalDateTime l=LocalDateTime.of(2021,12,12,12,00);
    Login user1=new Login(1L,"abc@gmail.com","pass1","hisbio","hisprofilepic", "dob","abc",0);
    Login user2=new Login(2L,"xyz@gmail.com","pass1","hisbio","hisprofilepic", "dob","abc",0);
    Activity activity1=new Activity(1l,"this is my title",l,"tags","this is description",2L);
    Activity activity2=new Activity(2L,"this is my title",l,"tags","this is description",2L);
    Activity activity3=new Activity(3l,"this is my title",l,"tags","this is description",2L);
    Activity activity4=new Activity(4l,"this is my title",l,"tags","this is description",2L);
    StatusDetails sd1=new StatusDetails(1L,"invited",user1,activity1,0);
    StatusDetails sd2=new StatusDetails(1L,"registered",user1,activity2,0);
    StatusDetails sd3=new StatusDetails(1L,"participated",user1,activity3,0);
    StatusDetails sd4=new StatusDetails(1L,"cancelled",user1,activity1,0);

    Optional<Login>useroptional1= Optional.of(user1);
    Optional<Login>useroptional2=Optional.of(user2);
    Optional<Activity>activityoptional1= Optional.of(activity1);
    Optional<Activity>activityoptional2=Optional.of(activity2);
    Optional<StatusDetails>statusDetailsOptional1=Optional.ofNullable(sd1);

    List<Login>userList=new ArrayList<>();
    Tags tag1=new Tags(1L,"first");
    Tags tag2=new Tags(2L,"second");
    UserTags usertags1=new UserTags(1L,1L,1L);
    UserTags usertags2=new UserTags(2L,2L,2L);
    List<UserTags>userTagsList1=new ArrayList<>();
    List<UserTags>userTagsList2=new ArrayList<>();
    List<Activity>organized=new ArrayList<>();
    List<Tags>t=new ArrayList<>();
    Optional userListOptional;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        userTagsList1.add(usertags1);
        user1.setAreaofinterest("first");
        String hashedPassword = BCrypt.hashpw("pass1", BCrypt.gensalt(10));
        user1.setPassword(hashedPassword);
        userTagsList2.add(usertags2);
        statusList1.add(sd1);
        statusList1.add(sd2);
        statusList1.add(sd3);
        statusList1.add(sd4);
        user1.setStatusDetails(statusList1);
        user1.setUsertags(userTagsList1);
        user2.setUsertags(userTagsList2);
        organized.add(activity1);
        organized.add(activity2);
        organized.add(activity3);
        organized.add(activity4);
        user2.setActivitiesOrganised(organized);
        userList.add(user1);
        userList.add(user2);
        Optional<Login>useroptional1= Optional.of(user1);
        Optional<Login>useroptional2=Optional.of(user2);
        Optional<Activity>activityoptional1= Optional.of(activity1);
        Optional<Activity>activityoptional2=Optional.of(activity2);
        Optional<StatusDetails>statusDetailsOptional1=Optional.ofNullable(sd1);
    }
/*
    @Test
    void updateStatus() {
        Mockito.when(loginRepository.findById(1L)).thenReturn(useroptional1);
        Mockito.when(activityRepository.findById(1L)).thenReturn(activityoptional1);
        Mockito.when(statusRepository.findstatus(user1,activity1)).thenReturn(statusDetailsOptional1);
        Mockito.when(statusRepository.save(sd1)).thenReturn(sd1);
        Mockito.when(statusRepository.findstatusByUser(user1,activity1)).thenReturn(sd1);
        //StatusDetails sd=new StatusDetails("invited",user2,activity1,0);
        StatusDetails sd=loginService.updateStatus(1L,1L,"invited");
        System.out.println(sd.getActivity());
        assertEquals(sd.getUser().getId(),1);
    }
*/
    @Test
    void getUsers() {
        Mockito.when(loginRepository.findAll()).thenReturn(userList);
        Mockito.when(tagRepository.findTagById(1L)).thenReturn("first");
        Mockito.when(tagRepository.findTagById(2L)).thenReturn("second");
        //Mockito.when(userTagRepository.save(usertags1)).thenReturn(usertags1);
        List<ShowingUser> l=loginService.GetUsers();
        assertEquals(l.size(),userList.size());
    }

   /* @Test
    void addNewUser() {
        Mockito.when(loginRepository.save(user1)).thenReturn(user1);
        Mockito.when(tagRepository.findIdbyTag("first")).thenReturn(1L);
        //Mockito.when()
        Login output_user=loginService.addNewUser(user1);
        assertEquals(user1.getId(),output_user.getId());
    }*/

    @Test
    void getUserById() {
        Mockito.when(loginRepository.existsById(1L)).thenReturn(true);
        Mockito.when(loginRepository.findById(1L)).thenReturn(useroptional1);
        ShowingUser sh=loginService.getUserById(1L);
        assertEquals(sh.getName(),user1.getName());
    }

    @Test
    void addToStatus() {
        Mockito.when(loginRepository.findById(1L)).thenReturn(useroptional1);
        Mockito.when(activityRepository.findById(1L)).thenReturn(activityoptional1);

    }

    @Test
    void getUserByEmail() {
        Mockito.when(loginRepository.existsById(1L)).thenReturn(true);
        Mockito.when(loginRepository.findUserByEmail("abc@gmail.com")).thenReturn(useroptional1);
        ShowingUser sh=loginService.getUserByEmail("abc@gmail.com");
        assertEquals(sh.getId(),user1.getId());
    }

    @Test
    void validate() {
        Mockito.when(loginRepository.findUserByEmail("abc@gmail.com")).thenReturn(useroptional1);
        Login sh=loginService.validate("abc@gmail.com","pass1");
        assertEquals(user1.getId(),sh.getId());
    }

    @Test
    void invalidateWrongPassword()
    {
        Mockito.when(loginRepository.findUserByEmail("abc@gmail.com")).thenReturn(useroptional1);
        Login sh=loginService.validate("abc@gmail.com","pass");
        assertEquals(sh,null);

    }

    @Test
    void userNameExists() {
        Mockito.when(loginRepository.findUserByEmail("abc@gmail.com")).thenReturn(useroptional1);
        boolean b=loginService.UserNameExists("abc@gmail.com");
        assertEquals(b,true);
    }
    @Test
    void userNameNotExists()
    {
        Mockito.when(loginRepository.findUserByEmail("abc@gmail.com")).thenReturn(useroptional1);
        boolean b=loginService.UserNameExists("ac@gmail.com");
        assertEquals(b,false);
    }

    @Test
    void updateUser() {
    }
}