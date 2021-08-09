package com.example.demo.Repository;

import com.example.demo.Models.Activity;
import com.example.demo.Models.ActivityTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityTagRepository extends JpaRepository<ActivityTags,Long> {

    @Query(value="Select count(*) from activity_tags where tag_id=:id",nativeQuery = true)
    Long getTagWiseActivityCount(Long id);

    @Query("SELECT activityId from ActivityTags where tagId=:id")
    List<Long> getActivitiesIds(Long id);
}
