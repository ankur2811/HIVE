package com.example.demo.Controller;

import com.example.demo.Models.Login;
import com.example.demo.Models.UserTags;
import com.example.demo.Service.LoginService;
import com.example.demo.Service.UsertagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path="api/tag")
public class UserTagController {

    private final UsertagService usertagService;

    @Autowired
    public UserTagController(UsertagService usertagService) {
        this.usertagService =usertagService;

    }

    @PostMapping
    public void registerNewUserTag(@RequestBody UserTags user)
    {
       if(user.getUserId()==null || user.getTagId()==null )
       {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"users data insufficient");

        }


        System.out.println("post");
        usertagService.addNew(user);
    }
}
