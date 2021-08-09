package com.example.demo.Service;

import com.example.demo.Models.*;
import com.example.demo.Repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceTest {

    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagService tagservice;
    List<Login>userList1=new ArrayList<>();

    LocalDateTime l=LocalDateTime.of(2021,12,12,12,00);
    Login user1=new Login(1L,"abc@gmail.com","pass1","hisbio","hisprofilepic", "dob","abc",0);
    Login user2=new Login(2L,"xyz@gmail.com","pass1","hisbio","hisprofilepic", "dob","abc",0);
    Activity activity1=new Activity(1L,"this is my title",l,"tags","this is description",2L);
    Activity activity2=new Activity(2L,"this is my title",l,"tags","this is description",2L);
    Activity activity3=new Activity(3L,"this is my title",l,"tags","this is description",2L);

    Tags tag1=new Tags(1L,"first");
    Tags tag2=new Tags(2L,"second");
    List<Tags>t=new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userList1.add(user1);
        userList1.add(user2);
        t.add(tag1);
        t.add(tag2);

    }

    @Test
    void testGetUsers() {
        Mockito.when(tagRepository.findUserByTags("first")).thenReturn(t);
        List<Tags> result=tagservice.GetUsers("first");
        assertEquals(result.size(),t.size());
    }

    @Test
    void testGetTagId() {
        Mockito.when(tagRepository.findIdbyTag("first")).thenReturn(1L);
        Long l=tagservice.GetTagId("first");
        assertEquals(1L,l);
    }
}