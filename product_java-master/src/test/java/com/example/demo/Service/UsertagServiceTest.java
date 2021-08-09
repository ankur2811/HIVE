package com.example.demo.Service;

import com.example.demo.Models.ActivityTags;
import com.example.demo.Models.UserTags;
import com.example.demo.Repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.jupiter.api.Assertions.*;

class UsertagServiceTest {

    @InjectMocks
    UsertagService usertagService;

    @Mock
    TagRepository tagRepository;


    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddNew() {


    }
}