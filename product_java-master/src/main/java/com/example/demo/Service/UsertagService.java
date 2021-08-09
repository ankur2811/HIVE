package com.example.demo.Service;

import com.example.demo.Models.UserTags;

import com.example.demo.Repository.TagRepository;
import com.example.demo.Repository.UserTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsertagService {

    private final UserTag tagRepository;
    public UsertagService(UserTag tagRepository)
    {
        this.tagRepository=tagRepository;
    }


    //Function to add new-tag mapping
    public void addNew(UserTags user) {
      tagRepository.save(user);
    }
}
