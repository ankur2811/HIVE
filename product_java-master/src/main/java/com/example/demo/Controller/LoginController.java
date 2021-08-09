package com.example.demo.Controller;

import com.example.demo.Constants;
import com.example.demo.Models.Activity;
import com.example.demo.Models.Login;
import com.example.demo.Service.LoginService;
import com.example.demo.utilities.ShowingActivities;
import com.example.demo.utilities.ShowingUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path="api/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService =loginService;

    }

    @GetMapping
    public List<ShowingUser> GetUsers(){

        try {
            return loginService.GetUsers();
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"users not present.");
        }
    }

    //Controller to register a new user

    @PostMapping
    public ResponseEntity<Map<String, String>> registerNewUser(@RequestBody Login user)
    {
        if(user.getUsername()==null || user.getPassword()==null )
        {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"users data insufficient");

        }
        System.out.println("post");
       Login res= loginService.addNewUser(user);
       if(res==null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user already present.");
       }
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }



    @PutMapping(path="jwt/id")
    public void UpdateUser(HttpServletRequest request,  @RequestBody Login user)
    {
        Long id = (Long) request.getAttribute("id");
        loginService.updateUser(id,user);
    }





    @GetMapping(path="jwt/id")
    public ShowingUser getUserById(HttpServletRequest request)
    {
        Long id = (Long) request.getAttribute("id");
        try {
            return loginService.getUserById(id);


        }
        catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with id"+id+ "not present.");

        }
    }

    @GetMapping(path="{userId}")
    public ShowingUser getUserById(@PathVariable("userId") Long id)
    {
        try {
            return loginService.getUserById(id);

        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with id"+id+ "not present.");
        }
    }



    @GetMapping(path="email/{userId}/password/{password}")
    public ResponseEntity<Map<String, String>> getUserByEmail(@PathVariable("userId") String id, @PathVariable("password") String password)
    {
       Login user=  loginService.validate(id,password);
       if(user==null)
       {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with id"+id+ "not present.");
       }
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }


    @GetMapping(path="exists/email/{useremail}")
    public boolean checkUser(@PathVariable("useremail") String username)
    {
        return loginService.UserNameExists(username);
    }


    @GetMapping(path="jwt/invited/id")
    public List<Activity>getInvited(HttpServletRequest request)
    {
        Long id = (Long) request.getAttribute("id");
        return loginService.getInvited(id);
    }
    @GetMapping(path="jwt/registered/id")
    public List<Activity>getRegistered(HttpServletRequest request)
    {
        Long id = (Long) request.getAttribute("id");
        return loginService.getRegistered(id);
    }



    private Map<String, String> generateJWTToken(Login user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
              //  .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("id", user.getId())
                .claim("email", user.getUsername())
                .claim("firstName", user.getName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }



    @GetMapping("/paging")
    public List<ShowingUser> getUsersPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                                       @RequestParam(defaultValue = "1") Integer pageSize,
                                                       @RequestParam(defaultValue = "score") String sortBy
                                                      )
    {
        List<ShowingUser> ans=loginService.getUsersPaginated(pageNo, pageSize, sortBy);
        return ans;
    }

    @GetMapping(path="forget/{userId}")
    public void forgetPassword(@PathVariable("userId") String email)
    {
        try {
            loginService.forgetPassword(email);

        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with id"+email+ "not present.");
        }
    }

    @GetMapping(path="otp/{userId}")
    public String sentOTP(@PathVariable("userId") String email)
    {
        try {
          String str=  loginService.sentOtp(email);
          if(str.equals("failure"))
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with id"+email+ "already present.");

          return str;
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with id"+email+ "already present.");
        }
    }


}
