package com.example.demo.Controller;

import com.example.demo.Models.Activity;
import com.example.demo.Service.ActivityService;
import com.example.demo.utilities.GroupByYear;
import com.example.demo.utilities.ShowingActivities;
import com.sun.net.httpserver.Authenticator;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path="/activity")
public class ActivityController {
    @Autowired
    private final ActivityService activityService;
    public ActivityController(ActivityService activityService)
    {
        this.activityService=activityService;
    }


    @PostMapping("/add/{userid}")
    public Activity addActivity(@RequestBody Activity req, @PathVariable("userid") Long id) throws MessagingException, TemplateException, IOException {
        return activityService.addActivity(id,req);
    }


    @GetMapping("/allactivities")
    public List<ShowingActivities> getAll()
    {
        return activityService.getAll();
    }

    @GetMapping("/countpast")
    public Integer countPast()
    {
        return activityService.getPastActivityCount();
    }


    @GetMapping(path="{activityId}")
    public ShowingActivities getActivityById(@PathVariable("activityId") Long id)
    {
        return  activityService.getActivityById(id);
    }



    @DeleteMapping(path="{activityId}")
    public void deleteActivity(@PathVariable("activityId") Long id)
    {
        try {
            activityService.deleteActivity(id);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"activity with id"+id+ "not present.");
        }

    }


    @GetMapping("/paging")
    public List<ShowingActivities> getCoursesPaginated(  @RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "1") Integer pageSize,
                                              @RequestParam(defaultValue = "activityid") String sortBy,
                                              @RequestParam(defaultValue = "true") Boolean asc)
    {
        List<ShowingActivities> ans=activityService.getActivityPaginated(pageNo, pageSize, sortBy);
        return ans;
    }

    @PutMapping("/markpresent/{activityid}")
    public ResponseEntity<?> markPresent(@PathVariable("activityid") Long activityid, @RequestBody List<Long>userids) throws MessagingException, TemplateException, IOException {
          activityService.markPresent(activityid,userids);
          return new ResponseEntity<Authenticator.Success>(HttpStatus.OK);
    }

    @GetMapping("/pastactivity/{value}")
    public List<ShowingActivities>getPastActivityByTitle(@RequestParam(defaultValue = "title") String searchParam,@PathVariable("value") String val) {
        if(searchParam.equals("title"))
        {
            System.out.println("inside title");
            return activityService.getPastActivitiesByTitle(val);
        }
        return activityService.getPastActivitiesByTag(val);
    }

    @GetMapping("/tagwisecount")
     public Map<String,Object> getTagWiseCount()
    {
        return activityService.getTagWiseCount();
    }

    @GetMapping("/yearwisecount")
    public List<BigInteger[]>getYearWiseCount()
    {
        return activityService.getYearWiseCount();
    }

    @PutMapping(path="update/{activityid}")
    public void UpdateActivity(@PathVariable("activityid") Long id, @RequestBody Activity activity) throws MessagingException, TemplateException, IOException {
        activityService.updateActivity(id,activity);
    }

    @GetMapping(path="/recentparticipated/{userid}")
    public List<ShowingActivities>getRecentParticipated(@PathVariable("userid") Long id)
    {
        return activityService.getRecentParticipated(id);
    }

}
