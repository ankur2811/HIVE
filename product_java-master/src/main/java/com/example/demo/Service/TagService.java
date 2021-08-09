package com.example.demo.Service;


import com.example.demo.Models.Tags;
import com.example.demo.Repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository)
    {
        this.tagRepository=tagRepository;
    }

    //Function to get list of all users and activities based on given tag
    public List<Tags> GetUsers(String tagName) {
        return tagRepository.findUserByTags(tagName);

    }


    //Function to get tag id of given tag
    public Long GetTagId(String tag)
    {
        return tagRepository.findIdbyTag(tag);
    }
}
