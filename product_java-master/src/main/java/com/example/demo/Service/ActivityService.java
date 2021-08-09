package com.example.demo.Service;
import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import com.example.demo.scheduledjob.EmailEntity;
import com.example.demo.scheduledjob.SendEmail;
import com.example.demo.utilities.GroupByYear;
import com.example.demo.utilities.ShowingActivities;
import com.example.demo.utilities.ShowingUser;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ActivityService {

    LoginRepository loginRepository;
    ActivityRepository activityRepository;
    LoginService loginService;
    StatusRepository statusRepository;
    private final TagRepository tagRepository;
    private final ActivityTagRepository activityTagRepository;

    @Autowired
    SendEmail sendEmail;
    public ActivityService(LoginRepository loginRepository,ActivityRepository activityRepository,LoginService loginService,TagRepository tagRepository,ActivityTagRepository activityTagRepository, StatusRepository statusRepository,SendEmail sendEmail)
    {
        this.loginRepository=loginRepository;
        this.activityRepository=activityRepository;
        this.loginService=loginService;
        this.tagRepository=tagRepository;
        this.activityTagRepository=activityTagRepository;
        this.statusRepository=statusRepository;
        this.sendEmail=sendEmail;
    }

    //Add new Activity to the database
    public Activity addActivity(Long uid, Activity activity) throws MessagingException, TemplateException, IOException {
        Login user=loginRepository.findById(uid).get();
        List<Activity> ls=user.getActivitiesOrganised();
        if(ls!=null)
        {
            ls.add(activity);
           user.setActivitiesOrganised(ls);
        }
        user.setCount(user.getCount()+1);
     //   loginRepository.save(user);
        activity.setCreatorid(user.getId());
        List<String> tagsList= Arrays.asList(activity.getTags().split(","));
        activityRepository.save(activity);
        for(String t:tagsList)
        {
            Long tagId=tagRepository.findIdbyTag(t);
            ActivityTags at=new ActivityTags(tagId,activity.getActivityid());

            activityTagRepository.save(at);
            LocalDateTime timelocal = LocalDateTime.now();

            ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
            //"d" is the current date and
            //"ZonedDateTime" accepts "d" as UTC
            //"atZone" specifies the time zone

            // converting to IST
            ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                    ZoneId.of("Asia/Kolkata"));
            LocalDateTime dt=LocalDateTime.from(currenttime);

            if(activity.getDate().isBefore(dt))
            {
                continue;
            }
            List<Tags>ts=tagRepository.findUserByTags(t);

            if(ts!=null) {
                for (Tags tg : ts) {

                    List<UserTags> usertags = tg.getUser();
                    for (UserTags loopuser : usertags) {
                        loginService.addToStatus(loopuser.getUserId(), activity.getActivityid(), "invited");
                    }
                }
            }
        }
        return activity;
    }



    //Function to show all activities which are active
    public List<ShowingActivities>getAll()
    {
        LocalDateTime timelocal = LocalDateTime.now();

        ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
        //"d" is the current date and
        //"ZonedDateTime" accepts "d" as UTC
        //"atZone" specifies the time zone

        // converting to IST
        ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                ZoneId.of("Asia/Kolkata"));
        LocalDateTime t=LocalDateTime.from(currenttime);
        List<Activity> activities=activityRepository.FindAllOpenActivities(t);
        List<ShowingActivities>showingActivities=new ArrayList<>();

        //Check activity is closed or open
        System.out.println(activities.size()+" and time "+currenttime);
        for(Activity activity:activities)
        {
            System.out.println("inside this ");
            ShowingActivities sa=new ShowingActivities();
            sa.setActivityid(activity.getActivityid());
            sa.setTitle(activity.getTitle());
            sa.setCreatorid(activity.getCreatorid());
            sa.setDate(activity.getDate());
            sa.setDescription(activity.getDescription());
            sa.setTags(activity.getTags());
            sa.setStatus(activity.getStatus());
            ShowingUser user1=loginService.getUserById(activity.getCreatorid());
            sa.setCreatorName(user1.getName());
            sa.setCreatorPic(user1.getProfilePicture());
            sa.setActivityPic(activity.getActivityPic());
            sa.setAttendenceMarked(activity.getAttendenceMarked());
            List<ActivityTags> utg=activity.getActivitytags();

            List<String>tags=new ArrayList<>();
           if(utg!=null) {
               for (ActivityTags at : utg) {
                   tags.add(tagRepository.findTagById(at.getTagId()));
               }
               sa.setTagList(tags);
           }



            showingActivities.add(sa);
        }
        Collections.reverse(showingActivities);
        return showingActivities;
    }



    //Function to show activity based on given id
    public ShowingActivities getActivityById(Long id) {

        Activity activity=activityRepository.findById(id).get();
        ShowingActivities sa=new ShowingActivities();
        sa.setActivityid(activity.getActivityid());
        sa.setTitle(activity.getTitle());
        sa.setCreatorid(activity.getCreatorid());
        sa.setDate(activity.getDate());
        sa.setDescription(activity.getDescription());
        sa.setTags(activity.getTags());
        sa.setStatus(activity.getStatus());
        sa.setActivityPic(activity.getActivityPic());
        sa.setAttendenceMarked(activity.getAttendenceMarked());
        sa.setMorePics(activity.getMorePics());
        List<ShowingUser>participated=new ArrayList<>();
        List<ShowingUser>registered=new ArrayList<>();
        List<ShowingUser>invited=new ArrayList<>();
        if(activity.getStatusDetails()!=null) {
            for (int i = 0; i <= activity.getStatusDetails().size() - 1; i++) {

                if (activity.getStatusDetails().get(i).getStatus().equals("participated")) {

                    activity.getStatusDetails().get(i).getUser().setStatusDetails(new ArrayList<>());
                    ShowingUser sh = new ShowingUser();
                    sh = loginService.getUserByEmail(activity.getStatusDetails().get(i).getUser().getUsername());
                    sh.setInvited(new ArrayList<>());
                    sh.setRegistered(new ArrayList<>());
                    sh.setCancelled(new ArrayList<>());
                    sh.setParticipated(new ArrayList<>());
                    sh.setOrganised(new ArrayList<>());
                    sh.setReview(activity.getStatusDetails().get(i).getReview());
                    participated.add(sh);
                } else if (activity.getStatusDetails().get(i).getStatus().equals("invited")) {
                    activity.getStatusDetails().get(i).getUser().setStatusDetails(new ArrayList<>());
                    ShowingUser sh = new ShowingUser();
                    sh = loginService.getUserByEmail(activity.getStatusDetails().get(i).getUser().getUsername());
                    sh.setInvited(new ArrayList<>());
                    sh.setRegistered(new ArrayList<>());
                    sh.setCancelled(new ArrayList<>());
                    sh.setParticipated(new ArrayList<>());
                    sh.setOrganised(new ArrayList<>());

                    invited.add(sh);
                } else if (activity.getStatusDetails().get(i).getStatus().equals("registered")) {
                    activity.getStatusDetails().get(i).getUser().setStatusDetails(new ArrayList<>());
                    ShowingUser sh = new ShowingUser();
                    sh = loginService.getUserByEmail(activity.getStatusDetails().get(i).getUser().getUsername());
                    sh.setInvited(new ArrayList<>());
                    sh.setRegistered(new ArrayList<>());
                    sh.setCancelled(new ArrayList<>());
                    sh.setParticipated(new ArrayList<>());
                    sh.setOrganised(new ArrayList<>());
                    registered.add(sh);
                }
            }
        }
        sa.setRegistered(registered);
        sa.setParticipated(participated);
        sa.setInvited(invited);



        ShowingUser user1=loginService.getUserById(activity.getCreatorid());
        sa.setCreatorName(user1.getName());
        sa.setCreatorPic(user1.getProfilePicture());
        List<ActivityTags> utg=activity.getActivitytags();

        List<String>tags=new ArrayList<>();
        if(utg!=null) {
            for (ActivityTags at : utg) {
                tags.add(tagRepository.findTagById(at.getTagId()));
            }
        }
        sa.setTagList(tags);
        return sa;
    }


    //Function to delete activity
    public void deleteActivity(Long id) {
        boolean exists=activityRepository.existsById(id);
        if(!exists)
        {
            throw new IllegalStateException("activity with id"+id+"does not exists");

        }
        activityRepository.deleteById(id);
    }

    public List<ShowingActivities> getActivityPaginated(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy) );

//        Page<Activity> pagedResult = activityRepository.findByStatusContaining("closed",paging);
        LocalDateTime timelocal = LocalDateTime.now();
        ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
        //"d" is the current date and
        //"ZonedDateTime" accepts "d" as UTC
        //"atZone" specifies the time zone

        // converting to IST
        ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                ZoneId.of("Asia/Kolkata"));
        LocalDateTime t=LocalDateTime.from(currenttime);
        Page<Activity> pagedResult = activityRepository.findAllclosedActivities(t,paging);

        if(pagedResult.hasContent()) {
            List<Activity> ls=pagedResult.getContent();
            List<ShowingActivities> sh=new ArrayList<>();
            for(Activity ac:ls)
            {
                ShowingActivities sa=new ShowingActivities();
                sa.setActivityid(ac.getActivityid());
                sa.setCreatorid(ac.getCreatorid());
                sa.setTitle(ac.getTitle());
                sa.setDescription(ac.getDescription());
                sa.setTags(ac.getTags());
                sa.setDate(ac.getDate());
                sh.add(sa);
            }
            return sh;
        } else {
            return new ArrayList<ShowingActivities>();
        }
    }
    public Integer getPastActivityCount()
    {
        LocalDateTime timelocal=LocalDateTime.now();
        ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
        //"d" is the current date and
        //"ZonedDateTime" accepts "d" as UTC
        //"atZone" specifies the time zone

        // converting to IST
        ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                ZoneId.of("Asia/Kolkata"));
        LocalDateTime t=LocalDateTime.from(currenttime);
        return activityRepository.getPastCount(t);
    }
    public void markPresent(Long activityid,List<Long>userids) throws MessagingException, TemplateException, IOException {
        for(Long userid:userids)
       {
           loginService.updateStatus(userid,activityid,"participated");
       }
        Activity ac=activityRepository.findById(activityid).get();
        ac.setAttendenceMarked(true);
        activityRepository.save(ac);
    }


    public List<ShowingActivities>getPastActivitiesByTitle(String title)
    {
        LocalDateTime timelocal=LocalDateTime.now();
        ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
        //"d" is the current date and
        //"ZonedDateTime" accepts "d" as UTC
        //"atZone" specifies the time zone

        // converting to IST
        ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                ZoneId.of("Asia/Kolkata"));
        LocalDateTime t=LocalDateTime.from(currenttime);
       title=title.toLowerCase();
        List<Activity> activityList=activityRepository.getPastActivitiesByTitle(t,title);
       List<ShowingActivities>sh=new ArrayList<>();
       for(Activity activity:activityList)
       {
           ShowingActivities sa=new ShowingActivities();
           sa.setTitle(activity.getTitle());
           sa.setDescription(activity.getDescription());
           sa.setTags(activity.getTags());
           sa.setActivityid(activity.getActivityid());
           sa.setCreatorid(activity.getCreatorid());
           sa.setDate(activity.getDate());
           sh.add(sa);
       }
       return sh;
    }




    public List<ShowingActivities> getPastActivitiesByTag(String tag)
    {
        LocalDateTime timelocal=LocalDateTime.now();
        ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
        ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                ZoneId.of("Asia/Kolkata"));
        LocalDateTime t=LocalDateTime.from(currenttime);

        tag=tag.toLowerCase();
        Long tagId=tagRepository.findIdbyTag1(tag);
        List<ShowingActivities> sh=new ArrayList<>();
        List<Long>activityids=activityTagRepository.getActivitiesIds(tagId);
        for(Long id:activityids)
        {
            ShowingActivities sa=new ShowingActivities();
            Activity activity=activityRepository.findById(id).get();
            if(activity.getDate().isAfter(t))
                continue;
            sa.setTitle(activity.getTitle());
            sa.setDescription(activity.getDescription());
            sa.setTags(activity.getTags());
            sa.setActivityid(activity.getActivityid());
            sa.setCreatorid(activity.getCreatorid());
            sa.setDate(activity.getDate());
            sh.add(sa);
        }
        return sh;
    }





    public Map<String,Object> getTagWiseCount()
    {
        Map<String,Object>m=new HashMap<>();
       List<Tags>taglist=tagRepository.findAll();
       if(taglist!=null) {
           for (Tags t : taglist) {
               Long count = activityTagRepository.getTagWiseActivityCount(t.getId());
               //System.out.println("count is "+count);
               m.put(t.getTag(), count);
           }
       }
       Long totalact=activityRepository.count();
       m.put("totalactivities",totalact);
       Long totaluser=loginRepository.count();
       m.put("totaluser",totaluser);

        LocalDateTime timelocal=LocalDateTime.now();
        ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
        ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                ZoneId.of("Asia/Kolkata"));
        LocalDateTime t=LocalDateTime.from(currenttime);

        Integer pastcount=activityRepository.getPastCount(t);
        m.put("totalactive",totalact-pastcount);
        List<BigInteger> arr=statusRepository.getTopActivity();
        //arr=statusRepository.getTopActivity().size();
        if(arr.size()!=0) {
            Activity act = activityRepository.findById(arr.get(0).longValue()).get();
            act.setStatusDetails(null);
            act.setTags(null);
            act.setActivitytags(null);
            m.put("top activity", act);
        }
        else
        {
            m.put("top activity",null);
        }
       return m;
    }
    public List<BigInteger[]>getYearWiseCount()
    {
        return activityRepository.getYearWiseCount();
    }

    public void updateActivity(Long id, Activity activity) throws MessagingException, TemplateException, IOException {
        Activity ac =activityRepository.findById(id).get();
        if(activity.getActivityPic()!=null)
        {
            ac.setActivityPic(activity.getActivityPic());
        }

        if(activity.getDate()!=null)
        {
            ac.setDate(activity.getDate());
        }

        if(activity.getDescription()!=null)
        {
            ac.setDescription(activity.getDescription());
        }
        if(activity.getMorePics()!=null)
        {
            System.out.println("called");
            if(ac.getMorePics()!=null)
              ac.setMorePics(ac.getMorePics()+","+activity.getMorePics());
            else
                ac.setMorePics(activity.getMorePics());

        }
        List<StatusDetails> statusList=ac.getStatusDetails();
        List<EmailEntity>emailList=new ArrayList<>();
        if(statusList!=null)
        {
            System.out.println("not null");
            for(StatusDetails sd:statusList)
            {
                if(sd.getStatus().equals("registered"))
                {
                    EmailEntity e=new EmailEntity();
                    e.setActivity(ac);
                    e.setName(sd.getUser().getName());
                    e.setUsername(sd.getUser().getUsername());
                    emailList.add(e);
                }
            }
        }
        sendEmail.sendUpdateToAll(emailList);
        activityRepository.save(ac);
    }

    public List<ShowingActivities> getRecentParticipated(Long userid) {
        List<StatusDetails>sd=statusRepository.getRecentParticipated(userid);
        List<ShowingActivities> sa=new ArrayList<>();
        if(sd.size()>=5)
        {
            for(int i=0;i<=4;i++)
            {
                Activity activity=sd.get(i).getActivity();
                ShowingActivities sh=new ShowingActivities();
                sh.setActivityid(activity.getActivityid());
                sh.setDate(activity.getDate());
                sh.setReview(sd.get(i).getReview());
                sh.setTitle(activity.getTitle());
                sh.setTags(activity.getTags());
                sh.setCreatorName(loginRepository.findById(activity.getCreatorid()).get().getName());
                sa.add(sh);
            }
        }
        else
        {
           for(StatusDetails s:sd)
           {
               Activity activity=s.getActivity();
               ShowingActivities sh=new ShowingActivities();
               sh.setActivityid(activity.getActivityid());
               sh.setDate(activity.getDate());
               sh.setReview(s.getReview());
               sh.setTitle(activity.getTitle());
               sh.setTags(activity.getTags());
               sh.setCreatorName(loginRepository.findById(activity.getCreatorid()).get().getName());
               sa.add(sh);
           }
        }
        return sa;
    }
}
