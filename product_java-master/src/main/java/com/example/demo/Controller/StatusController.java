package com.example.demo.Controller;
import com.example.demo.Models.StatusDetails;
import com.example.demo.Service.LoginService;
import com.example.demo.utilities.ShowingUser;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path="api/status")
public class StatusController {

    private final LoginService loginService;

    @Autowired
    public StatusController(LoginService loginService) {
        this.loginService =loginService;

    }


    @PostMapping(path="/{userid}/{status}/activity/{activityid}")
    public boolean addToStatus(@PathVariable("userid") Long userid, @PathVariable("activityid") Long activityid,@PathVariable("status") String status) throws MessagingException, TemplateException, IOException {
        loginService.addToStatus(userid,activityid,status);
        return true;
    }


    @PutMapping(path="{userId}/{activityId}/{status}")
    public void updateUser( @PathVariable("userId") Long userId,@PathVariable("activityId") Long activityId,@PathVariable("status") String status)
    {
        try {
            loginService.updateStatus(userId,activityId,status);
        }
        catch (Exception ex) {
            System.out.println("exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user not present");
        }
    }

    @PostMapping(path="/review/{userId}/{activityId}")
    public boolean addReview(@RequestBody String review,@PathVariable("userId") Long userId,@PathVariable("activityId") Long activityId) throws MessagingException, TemplateException, IOException {
        try {
            loginService.addReview(userId,activityId,review);
        }
        catch (Exception ex) {
            System.out.println("exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user not present");
        }
        return true;
    }

    
}
