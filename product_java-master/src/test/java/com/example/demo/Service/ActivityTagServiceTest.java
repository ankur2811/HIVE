package com.example.demo.Service;

import com.example.demo.Models.ActivityTags;
import com.example.demo.Repository.ActivityTagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class ActivityTagServiceTest {

    @Mock
    ActivityTagRepository activityTagRepository;
    @InjectMocks
    ActivityTagService activityTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddNew() {
        Mockito.when(activityTagRepository.save(ArgumentMatchers.any(ActivityTags.class))).thenReturn(null);
        activityTagService.addNew(new ActivityTags(1L,1L,1L));

    }
}