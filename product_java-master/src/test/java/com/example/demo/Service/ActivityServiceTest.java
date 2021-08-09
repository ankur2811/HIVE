package com.example.demo.Service;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import com.example.demo.utilities.ShowingActivities;
import com.example.demo.utilities.ShowingUser;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class ActivityServiceTest {

    @Mock
    ActivityRepository activityRepository;

    @Mock
    LoginRepository loginRepository;

    @Mock
    LoginService loginService;
    @InjectMocks
    ActivityService activityService;

    @Mock
    TagRepository tagRepository;

    @Mock
    StatusRepository statusRepository;
    @Mock
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
    Optional<Login>useroptional1= Optional.of(user1);
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
      MockitoAnnotations.initMocks(this);
      activitylist1.add(activity1);
      activitylist1.add(activity2);
      t.add(tag1);
      t.add(tag2);
      showingUser2.setTg(t);
    }



    @Test
    void whenActivityCreationSuccessful() throws Exception
    {
      //Login first=new Login(1L,"abc@gmail.com","pass1","lorem ipsum","url1","28-11-1998","ankur",0);
      Mockito.when(loginRepository.findById(1L)).thenReturn(useroptional1);
      Mockito.when(tagRepository.findIdbyTag("first")).thenReturn(1L);
      Mockito.when(activityRepository.save(ArgumentMatchers.any(Activity.class))).thenReturn(activity1);
      Mockito.when(activityTagRepository.save(activityTags1)).thenReturn(activityTags1);
      Mockito.when(tagRepository.findUserByTags("first")).thenReturn(null);
      Mockito.when(loginRepository.findById(1L)).thenReturn(useroptional1);
      Mockito.when(activityRepository.findById(1L)).thenReturn(activityoptional1);
      Mockito.when(statusRepository.findstatus(user1,activity1)).thenReturn(statusDetailsOptional1);
      Mockito.when(statusRepository.save(sd1)).thenReturn(sd1);
      Mockito.when(statusRepository.findstatusByUser(user1,activity1)).thenReturn(sd1);
      //StatusDetails sd=new StatusDetails("invited",user2,activity1,0);
      //StatusDetails sd=loginService.updateStatus(1L,1L,"invited");
      Activity ac=activityService.addActivity(1L,activity1);
      assertEquals(ac.getActivityid(),activity1.getActivityid());
    }

    @Test
    public void testGetAllActivities() throws Exception
    {
      Mockito.when(activityRepository.FindAllOpenActivities(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(activitylist1);
      Mockito.when(loginService.getUserById(2L)).thenReturn(showingUser2);
      List<ShowingActivities> showingActivities=activityService.getAll();
      assertEquals(showingActivities.size(),activitylist1.size());
    }
    @Test
    public void testGetActivityById() throws Exception
    {
      Mockito.when(activityRepository.findById(1L)).thenReturn(activityoptional1);
      Mockito.when(loginService.getUserById(2L)).thenReturn(showingUser2);
      Mockito.when(tagRepository.findTagById(1L)).thenReturn(tag1.getTag());
      Mockito.when(tagRepository.findTagById(2L)).thenReturn(tag2.getTag());
      ShowingActivities sa =activityService.getActivityById(1L);
      assertEquals(sa.getActivityid(),activity1.getActivityid());
    }

    @Test
    public void testDeleteActivity(){
      Mockito.lenient().doNothing().when(activityRepository).deleteById(1L);
      Mockito.when(activityRepository.existsById(1L)).thenReturn(true);
      activityService.deleteActivity(1L);
    }

    @Test
    public void testGetPaginatedActivities()
    {
      Mockito.when(activityRepository.findAllclosedActivities(ArgumentMatchers.any(LocalDateTime.class),ArgumentMatchers.any(Pageable.class))).thenReturn(null);
    }
    @Test
    public void testGetPastActivityCount()
    {
      Mockito.when(activityRepository.getPastCount(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(2);
      int expected=activityService.getPastActivityCount();
      int actual=2;
      assertEquals(expected,actual);
    }

    @Test
    public void testGetPastActivityByTitle()
    {
      Mockito.when(activityRepository.getPastActivitiesByTitle(ArgumentMatchers.any(LocalDateTime.class),ArgumentMatchers.any(String.class))).thenReturn(activitylist1);
      List<ShowingActivities> sh=activityService.getPastActivitiesByTitle("this is my title");
      assertEquals(sh.size(),activitylist1.size());
    }

    @Test
    public void testGetTagWiseCount()
    {
      Map<Object,Long> m=new HashMap<>();
      Mockito.when(tagRepository.findAll()).thenReturn(t);
      Mockito.when(activityTagRepository.getTagWiseActivityCount(ArgumentMatchers.any())).thenReturn(1L);

    }
}
