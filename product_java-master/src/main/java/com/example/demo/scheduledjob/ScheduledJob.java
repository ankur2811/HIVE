package com.example.demo.scheduledjob;

import com.example.demo.Models.Activity;
import com.example.demo.Models.Login;
import com.example.demo.Models.StatusDetails;
import com.example.demo.Repository.ActivityRepository;
import com.example.demo.Repository.LoginRepository;
import com.example.demo.Repository.StatusRepository;
import com.example.demo.Service.ActivityService;
import com.example.demo.utilities.ShowingActivities;
import freemarker.template.TemplateException;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledJob {

    @Autowired
    ActivityService activityService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    SendEmail sendEmail;

    public ScheduledJob(StatusRepository statusRepository,ActivityRepository activityRepository,LoginRepository loginRepository, SendEmail sendEmail)
    {
        this.statusRepository=statusRepository;
        this.activityRepository=activityRepository;
        this.loginRepository=loginRepository;
        this.sendEmail=sendEmail;
    }

    @Scheduled(initialDelay = 1000,fixedDelay =300000)
    public void sendingMails() throws MessagingException, TemplateException, IOException {
        System.out.println("calling it from scheduled");
       List<StatusDetails> statusList=statusRepository.findRegistered();
       System.out.println(statusList.size());
       for(StatusDetails a :statusList)
       {
          Login user=loginRepository.findById(a.getUser().getId()).get();
          Activity activity=activityRepository.findById(a.getActivity().getActivityid()).get();
          LocalDateTime timelocal=LocalDateTime.now();


           ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
           //"d" is the current date and
           //"ZonedDateTime" accepts "d" as UTC
           //"atZone" specifies the time zone

           // converting to IST
           ZonedDateTime t = UTCtime.withZoneSameInstant(
                   ZoneId.of("Asia/Kolkata"));




           LocalDateTime timenow=LocalDateTime.from(t);
          LocalDateTime activityTime=activity.getDate();
           if(activityTime.getYear()==timenow.getYear())
           {
               if(activityTime.getMonth()==timenow.getMonth())
               {
                   if(activityTime.getDayOfMonth()==timenow.getDayOfMonth())
                   {
                       if(activityTime.getHour()-timenow.getHour()<=1)
                       {
                           //sendEmail.sendmail(user.getUsername(),user.getName(),activity);
                           sendEmail.sendTemplateEmail(user.getUsername(),user.getName(),activity);
                           a.setEmailSent(1);
                           statusRepository.save(a);
                       }
                   }
               }
           }
       }
    }

    @Scheduled(initialDelay = 100000,fixedDelay =500000)
    public void checkClosed()
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
        List<Activity> activities=activityRepository.findAll();

        for(Activity activity:activities) {
            LocalDateTime activitytime = activity.getDate();
            boolean isAfter = t.isAfter(activitytime);
            System.out.println(activity.getStatus());
            if (activity.getStatus() != null || isAfter) {
                activity.setStatus("closed");
                activityRepository.save(activity);
                continue;
            }
        }
    }
}

