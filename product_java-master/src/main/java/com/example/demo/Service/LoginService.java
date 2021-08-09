package com.example.demo.Service;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import com.example.demo.exceptions.DataNotPresentException;
import com.example.demo.scheduledjob.EmailEntity;
import com.example.demo.scheduledjob.SendEmail;
import com.example.demo.utilities.ShowingActivities;
import com.example.demo.utilities.ShowingUser;
import freemarker.template.TemplateException;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final ActivityRepository activityRepository;
    private final StatusRepository statusRepository;
    private final TagRepository tagRepository;
    private final UserTag usertagrepository;

    @Autowired
    SendEmail sendEmail;

    public LoginService(LoginRepository loginRepository, ActivityRepository activityRepository, StatusRepository statusRepository,TagRepository tagRepository,UserTag usertagrepository,SendEmail sendEmail) {
        this.loginRepository = loginRepository;
        this.activityRepository=activityRepository;
        this.statusRepository=statusRepository;
        this.tagRepository=tagRepository;
        this.usertagrepository=usertagrepository;
        this.sendEmail=sendEmail;
    }


    //Function to update the status of user for particular activity
    public void updateStatus(Long userId, Long activityId, String status) throws MessagingException, TemplateException, IOException {
        Login user=loginRepository.findById(userId).get();
        Activity activity=activityRepository.findById(activityId).get();
        Optional op=statusRepository.findstatus(user,activity);
        if(!op.isPresent())
        {
            this.addToStatus(userId,activityId,status);
            //return new StatusDetails(status,user,activity,0);
            return;
        }
        StatusDetails sdr1=statusRepository.findstatusByUser(user,activity);
        if(sdr1.getStatus().equals("registered")&&status.equals("invited"))
        {
            return;
        }
        if(status.equals("participated"))
        {
            sdr1.setPoints(100);
            user.setScore(user.getScore()+100);
            loginRepository.save(user);
        }
        sdr1.setStatus(status);
        statusRepository.save(sdr1);
        //System.out.println(sdr1);
    }


    //Function to get the list of all users
    public  List<ShowingUser> GetUsers() {
       List<Login>usersList=loginRepository.findAll();
       List<ShowingUser> sl=new ArrayList<>();
        for(Login user:usersList)
        {
         List<Activity>invited=new ArrayList<>();
         List<Activity>participated=new ArrayList<>();
         List<Activity>registered=new ArrayList<>();
         List<Activity>cancelled=new ArrayList<>();
         ShowingUser sh=new ShowingUser();
         sh.setId(user.getId());
         sh.setBio(user.getBio());
         sh.setDOB(user.getDOB());
        // sh.setPassword(user.getPassword());
         sh.setName(user.getName());
         sh.setUsername(user.getUsername());
         sh.setScore(user.getScore());
         sh.setOrganisedCount(user.getCount());
         sh.setTags(user.getAreaofinterest());
         sh.setProfilePicture(user.getProfilePicture());

            List<UserTags> utg=user.getUsertags();
            System.out.println(utg);
            /*List<Tags> tagg=new ArrayList<>();
            for(int i=0;i<utg.size();i++)
            {
              tagg.add(  tagRepository.findById(utg.get(i).getTagId()).get());
            }
         sh.setTg(tagg);
           */
            List<String>tags=new ArrayList<>();
            if(utg!=null) {
                for (UserTags t : utg) {
                    System.out.println(t);
                    tags.add(tagRepository.findTagById(t.getTagId()));
                }
                sh.setTagList(tags);
            }
         List<Activity> organised=user.getActivitiesOrganised();

         if(organised!=null)
         {
             for(int j=0;j<=organised.size()-1;j++)
             {
                 organised.get(j).setStatusDetails(new ArrayList<>());
             }
         }
         List<StatusDetails>status=user.getStatusDetails();
         Integer score=0;
         if(status!=null) {
             for (int j = 0; j <= status.size() - 1; j++) {
                 status.get(j).getActivity().setStatusDetails(new ArrayList<>());
                 String st = status.get(j).getStatus();
                 if (st.equals("invited")) {
                     invited.add(status.get(j).getActivity());
                 } else if (st.equals("registered")) {
                     registered.add(status.get(j).getActivity());
                 } else if (st.equals("participated")) {
                     participated.add(status.get(j).getActivity());
                     score = score + 100;
                 } else if (st.equals("cancelled")) {
                     cancelled.add(status.get(j).getActivity());
                 }
             }
         }
         sh.setInvited(invited);
         sh.setScore(score);
         sh.setCancelled(cancelled);
         sh.setParticipated(participated);
         sh.setRegistered(registered);
         sh.setOrganised(organised);
         sl.add(sh);
        }
        return sl;
    }


    //Function to add new user
    public Login addNewUser(Login user)
    {
        boolean res=this.UserNameExists(user.getUsername());
        if(res)
            return null;
        String str=user.getAreaofinterest();
        if(user.getGender().equals("female"))
            user.setProfilePicture("https://bootdey.com/img/Content/avatar/avatar3.png");
        else
        user.setProfilePicture("https://bootdey.com/img/Content/avatar/avatar7.png");


        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        loginRepository.save(user);
        if(str!=null) {
            List<String> ls = Arrays.asList(str.split(","));
            for (String s : ls) {
                Long tid = tagRepository.findIdbyTag(s);
                UserTags ut = new UserTags(tid, user.getId());
                usertagrepository.save(ut);
            }
        }
        user.setStatusDetails(new ArrayList<>());
        return user;
    }


    //Function to get particular user by id
    public ShowingUser getUserById(Long id){
        boolean exists=loginRepository.existsById(id);
        if(!exists)
        {
            //throw new IllegalStateException("review with id"+id+"does not exists");
        }
       Login user =loginRepository.findById(id).get();
        List<Activity>invited=new ArrayList<>();
        List<Activity>participated=new ArrayList<>();
        List<Activity>registered=new ArrayList<>();
        List<Activity>cancelled=new ArrayList<>();
        List<ShowingActivities> part=new ArrayList<>();
        ShowingUser sh=new ShowingUser();
        sh.setId(user.getId());
        sh.setBio(user.getBio());
        sh.setDOB(user.getDOB());
        //sh.setPassword(user.getPassword());
        sh.setName(user.getName());
        sh.setOrganisedCount(user.getCount());
        sh.setProfilePicture(user.getProfilePicture());
        sh.setUsername(user.getUsername());
        sh.setScore(user.getScore());
        sh.setTags(user.getAreaofinterest());
        sh.setProfilePicture(user.getProfilePicture());
        List<UserTags> utg=user.getUsertags();

        List<String>tags=new ArrayList<>();
        if(utg!=null) {
            for (UserTags t : utg) {

                tags.add(tagRepository.findTagById(t.getTagId()));
            }
            sh.setTagList(tags);
        }


        List<Activity> organised=user.getActivitiesOrganised();
        if(organised!=null) {
            for (int j = 0; j <= organised.size() - 1; j++) {
                organised.get(j).setStatusDetails(new ArrayList<>());
            }
        }


        List<StatusDetails>status=user.getStatusDetails();
        Integer score=0;
        if(status!=null) {
            for (int j = 0; j <= status.size() - 1; j++) {
                status.get(j).getActivity().setStatusDetails(new ArrayList<>());
                String st = status.get(j).getStatus();


                Activity activity = status.get(j).getActivity();

                LocalDateTime timelocal = LocalDateTime.now();
                ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
                //"d" is the current date and
                //"ZonedDateTime" accepts "d" as UTC
                //"atZone" specifies the time zone

                // converting to IST
                ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                        ZoneId.of("Asia/Kolkata"));
                LocalDateTime t=LocalDateTime.from(currenttime);
                boolean isAfter = t.isAfter(activity.getDate());
                if (isAfter && activity.getStatus() == null) {
                    activity.setStatus("closed");
                    activityRepository.save(activity);
                }


                if (st.equals("invited") && !((status.get(j).getActivity().getStatus() != null))) {
                    System.out.println(status.get(j).getActivity().getStatus());
                    invited.add(status.get(j).getActivity());
                } else if (st.equals("registered") && !(status.get(j).getActivity().getStatus() != null)) {
                    registered.add(status.get(j).getActivity());
                } else if (st.equals("participated")) {


                    ShowingActivities shn=new ShowingActivities();
                    Activity ac=status.get(j).getActivity();
                    shn.setReviewdone(status.get(j).getReviewdone());
                    shn.setDate(ac.getDate());
                    shn.setTags(ac.getTags());
                    shn.setDescription(ac.getDescription());
                    shn.setReview(status.get(j).getReview());
                    shn.setTitle(ac.getTitle());
                    shn.setStatus(ac.getStatus());
                    shn.setAttendenceMarked(ac.getAttendenceMarked());

                    shn.setActivityid(ac.getActivityid());
                    participated.add(status.get(j).getActivity());
                    part.add(shn);
                    score = score + 100;

                } else if (st.equals("cancelled")) {
                    cancelled.add(status.get(j).getActivity());
                }
            }
        }
        sh.setInvited(invited);
        sh.setScore(score);
        sh.setCancelled(cancelled);
        sh.setParticipated(participated);
        sh.setRegistered(registered);
        sh.setOrganised(organised);
        sh.setParticipatedactivities(part);
        return sh;

    }


    //Function to add new mapping between user and activity
    public void addToStatus(Long userid,Long activityid,String status) throws MessagingException, TemplateException, IOException {
        Login user=loginRepository.findById(userid).get();
        Activity activity=activityRepository.findById(activityid).get();
        Long activitycreator=activity.getCreatorid();
        //Check if creator is invited
        if(userid==activitycreator)
        {
            return ;
        }

        //check if status is already present or not
        StatusDetails sdr1=statusRepository.findstatusByUser(user,activity);
        if(sdr1!=null) {
            System.out.println("present");
            return;
        }

        System.out.println("status");
        StatusDetails sd1=new StatusDetails();
        sd1.setUser(user);
        sd1.setActivity(activity);
        sd1.setStatus(status);
        if(status.equals("participated")) {
            sd1.setPoints(100);
            user.setScore(user.getScore()+100);
        }
        List<EmailEntity>emailEntityList=new ArrayList<>();
        if(status.equals("invited"))
        {
            //sendEmail.sendInvitedEmail(user.getUsername(),user.getName(),activity);
            EmailEntity e=new EmailEntity(user.getUsername(),user.getName(),activity);
            emailEntityList.add(e);
        }
        List<StatusDetails>statusList=user.getStatusDetails();
        statusList.add(sd1);
       user.setStatusDetails(statusList);
        loginRepository.save(user);
        statusRepository.save(sd1);
        if(emailEntityList.size()>0) {
            sendEmail.sendInvitationToAll(emailEntityList);
        }
    }


    //Function to get user details based on username
    public ShowingUser getUserByEmail(String email) {
        Optional<Login>op=loginRepository.findUserByEmail(email);
        Login user=op.get();
        List<Activity>invited=new ArrayList<>();
        List<Activity>participated=new ArrayList<>();
        List<Activity>registered=new ArrayList<>();
        List<Activity>cancelled=new ArrayList<>();
        ShowingUser sh=new ShowingUser();
        sh.setId(user.getId());
        sh.setBio(user.getBio());
        sh.setDOB(user.getDOB());
        //sh.setPassword(user.getPassword());
        sh.setName(user.getName());
        sh.setProfilePicture(user.getProfilePicture());
        sh.setUsername(user.getUsername());
        sh.setScore(user.getScore());
        sh.setTags(user.getAreaofinterest());
        sh.setProfilePicture(user.getProfilePicture());
        List<UserTags> utg=user.getUsertags();

        List<String>tags=new ArrayList<>();
        if(utg!=null) {
            for (UserTags t : utg) {

                tags.add(tagRepository.findTagById(t.getTagId()));
            }
        }
        sh.setTagList(tags);


        List<Activity> organised=user.getActivitiesOrganised();
        if(organised!=null) {
            for (int j = 0; j <= organised.size() - 1; j++) {
                organised.get(j).setStatusDetails(new ArrayList<>());
            }
        }
        List<StatusDetails>status=user.getStatusDetails();
        Integer score=0;
        if(status!=null) {
            for (int j = 0; j <= status.size() - 1; j++) {
                status.get(j).getActivity().setStatusDetails(new ArrayList<>());
                String st = status.get(j).getStatus();
                if (st.equals("invited")) {
                    invited.add(status.get(j).getActivity());
                } else if (st.equals("registered")) {
                    registered.add(status.get(j).getActivity());
                } else if (st.equals("participated")) {
                    participated.add(status.get(j).getActivity());
                    score = score + 100;
                } else if (st.equals("cancelled")) {
                    cancelled.add(status.get(j).getActivity());
                }
            }
        }
        sh.setInvited(invited);
        sh.setScore(score);
        sh.setCancelled(cancelled);
        sh.setParticipated(participated);
        sh.setRegistered(registered);
        sh.setOrganised(organised);
        return sh;
    }

    //Function to validate username and password
    public Login validate(String useremail,String password)
    {
        if(!this.UserNameExists(useremail))
            return null;
        Optional<Login>op=loginRepository.findUserByEmail(useremail);
        Login user=op.get();
       // if(user.getPassword().equals(password))
        System.out.println();
        if(BCrypt.checkpw(password, user.getPassword()))
        {
            return user;
        }
        else
        {
            return null;
        }

    }



    //Function to check if particular username exists or not
    public boolean UserNameExists(String username) {
        Optional<Login> user=loginRepository.findUserByEmail(username);
        if(user.isPresent())
        {
            return true;
        }
        return false;
    }



    //Function to update user details
    public void updateUser(Long id, Login newuser) {
        Login user =loginRepository.findById(id).get();
        if(newuser.getPassword()!=null) {
            String hashedPassword = BCrypt.hashpw(newuser.getPassword(), BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }

        if(newuser.getAreaofinterest()!=null)
        {
            if(newuser.getAreaofinterest().trim().length()==0)
            {
                usertagrepository.deletefromtag(id);
                newuser.setAreaofinterest(null);
            }
        }
        if(newuser.getAreaofinterest()!=null) {
            usertagrepository.deletefromtag(id);
            user.setAreaofinterest(newuser.getAreaofinterest());
            String str = user.getAreaofinterest();
            loginRepository.save(user);
            List<String> ls = Arrays.asList(str.split(","));
            for (String s : ls) {
                Long tid = tagRepository.findIdbyTag(s);
                UserTags ut = new UserTags(tid, user.getId());
                usertagrepository.save(ut);
            }
        }

        if(newuser.getBio()!=null)
            user.setBio(newuser.getBio());

        if(newuser.getDOB()!=null)
            user.setDOB(newuser.getDOB());

        if(newuser.getProfilePicture()!=null) {
            user.setProfilePicture(newuser.getProfilePicture());
            System.out.println("profilepicture1");
        }
        loginRepository.save(user);
    }


    public List<Activity> getInvited(Long id)
    {
        Login user =loginRepository.findById(id).get();
        List<Activity>invited=new ArrayList<>();
        List<UserTags> utg=user.getUsertags();
        List<StatusDetails>status=user.getStatusDetails();
        Integer score=0;
        if(status!=null) {
            for (int j = 0; j <= status.size() - 1; j++) {
                status.get(j).getActivity().setStatusDetails(new ArrayList<>());
                String st = status.get(j).getStatus();
                Activity activity = status.get(j).getActivity();
                LocalDateTime timelocal = LocalDateTime.now();
                ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
                //"d" is the current date and
                //"ZonedDateTime" accepts "d" as UTC
                //"atZone" specifies the time zone

                // converting to IST
                ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                        ZoneId.of("Asia/Kolkata"));
                LocalDateTime t=LocalDateTime.from(currenttime);
                boolean isAfter = t.isAfter(activity.getDate());
                if (isAfter && activity.getStatus() == null) {
                    activity.setStatus("closed");
                    activityRepository.save(activity);
                }
                if (st.equals("invited") && !((status.get(j).getActivity().getStatus() != null))) {
                    System.out.println(status.get(j).getActivity().getStatus());
                    invited.add(status.get(j).getActivity());
                }
            }
        }
        return invited;
    }
    public List<Activity> getRegistered(Long id)
    {
        Login user =loginRepository.findById(id).get();
        List<Activity>registered=new ArrayList<>();
        List<UserTags> utg=user.getUsertags();
        List<StatusDetails>status=user.getStatusDetails();
        Integer score=0;
        if(status!=null) {
            for (int j = 0; j <= status.size() - 1; j++) {
                status.get(j).getActivity().setStatusDetails(new ArrayList<>());
                String st = status.get(j).getStatus();
                Activity activity = status.get(j).getActivity();
                LocalDateTime timelocal = LocalDateTime.now();
                ZonedDateTime UTCtime = timelocal.atZone(ZoneId.of("UTC"));
                //"d" is the current date and
                //"ZonedDateTime" accepts "d" as UTC
                //"atZone" specifies the time zone

                // converting to IST
                ZonedDateTime currenttime = UTCtime.withZoneSameInstant(
                        ZoneId.of("Asia/Kolkata"));
                LocalDateTime t=LocalDateTime.from(currenttime);
                boolean isAfter = t.isAfter(activity.getDate());
                if(isAfter && activity.getStatus() == null){
                    activity.setStatus("closed");
                    activityRepository.save(activity);
                }
                if (st.equals("registered") && !((status.get(j).getActivity().getStatus() != null))) {
                    System.out.println(status.get(j).getActivity().getStatus());
                    registered.add(status.get(j).getActivity());
                }
            }
        }
        return registered;
    }

    public List<ShowingUser> getUsersPaginated(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending() );
        Page<Login> pagedResult = loginRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            List<Login> ls=pagedResult.getContent();
            List<ShowingUser> sh=new ArrayList<>();
            for(Login ac:ls)
            {
                ShowingUser sa=new ShowingUser();
                sa.setUsername(ac.getUsername());
                sa.setId(ac.getId());
                sa.setScore(ac.getScore());
                sa.setName(ac.getName());
                sa.setProfilePicture(ac.getProfilePicture());
               sa.setOrganisedCount(ac.getCount());
               sa.setGender(ac.getGender());
                sh.add(sa);
            }
            return sh;
        } else {
            return new ArrayList<ShowingUser>();
        }
    }

    public String forgetPassword(String email) throws MessagingException, TemplateException, IOException {
        Optional<Login>op=loginRepository.findUserByEmail(email);
        Login user=op.get();
        if(op==null)
        {
            return null;
        }

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < 10; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        String password= sb.toString();
        Login lg=new Login(password);
        this.updateUser(user.getId(),lg);
        sendEmail.sendPassword(user.getUsername(),password,user.getName());
        System.out.println(password);
        return "success";
    }

    public String sentOtp(String email)  throws MessagingException, TemplateException, IOException{
        Optional<Login>op=loginRepository.findUserByEmail(email);
        if(op.isPresent())
        {
            return "failure";
        }

        final String chars = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < 6; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        String password= sb.toString();
        System.out.println(password);
        return password;
    }

    public void addReview(Long userId, Long activityId, String review) {
        Login user=loginRepository.findById(userId).get();
        Activity activity=activityRepository.findById(activityId).get();


        StatusDetails sdr1=statusRepository.findstatusByUser(user,activity);
        if(sdr1.getReviewdone()==true)
            return;
        sdr1.setReview(review);
        sdr1.setReviewdone(true);
        statusRepository.save(sdr1);
    }
}
