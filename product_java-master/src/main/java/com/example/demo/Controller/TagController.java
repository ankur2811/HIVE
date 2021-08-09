package com.example.demo.Controller;

import com.example.demo.Models.Tags;
import com.example.demo.Service.TagService;
import com.example.demo.Service.UsertagService;
import com.example.demo.utilities.ShowingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path="api/tagg")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService =tagService;

    }

    @GetMapping(path="{name}")
    public List<Tags> GetUsersByTags(@PathVariable("name") String username){

        try {
            return tagService.GetUsers(username);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"users not present.");
        }
    }
}
