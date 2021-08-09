package com.example.demo.Service;

import com.example.demo.Models.ActivityTags;
import com.example.demo.Models.UserTags;
import com.example.demo.Repository.ActivityTagRepository;
import com.example.demo.Repository.UserTag;
import org.springframework.stereotype.Service;

@Service
public class ActivityTagService {
    private final ActivityTagRepository activiytagRepository;
    public ActivityTagService(ActivityTagRepository tagRepository)
    {
        this.activiytagRepository=tagRepository;
    }


    //Function to add new activity-tag mapping
    public void addNew(ActivityTags user) {
        activiytagRepository.save(user);
    }
}
