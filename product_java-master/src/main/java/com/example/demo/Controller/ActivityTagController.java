package com.example.demo.Controller;

import com.example.demo.Models.ActivityTags;
import com.example.demo.Models.UserTags;
import com.example.demo.Service.ActivityTagService;
import com.example.demo.Service.UsertagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path="api/activitytag")
public class ActivityTagController {

    private final ActivityTagService activitytagService;

    @Autowired
    public ActivityTagController(ActivityTagService activitytagService) {
        this.activitytagService=activitytagService;

    }

    @PostMapping
    public void registerNewUserTag(@RequestBody ActivityTags activitytag)
    {
        if(activitytag.getActivityId()==null || activitytag.getTagId()==null )
        {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"users data insufficient");

        }
        System.out.println("post");
        activitytagService.addNew(activitytag);
    }
}
