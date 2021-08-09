package com.example.demo.Repository;

import com.example.demo.Models.Activity;
import com.example.demo.utilities.GroupByYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Page<Activity> findByStatusContaining(String status, Pageable pageable);

    @Query("SELECT a FROM Activity a WHERE a.date >= :todaysdate")
    List<Activity>FindAllOpenActivities(@Param("todaysdate")LocalDateTime date);

    @Query("SELECT count(*) FROM Activity a WHERE a.date >= :todaysdate")
    Long countAllOpenActivities(@Param("todaysdate")LocalDateTime date);

    @Query("SELECT a FROM Activity a WHERE a.date <= :todaysdate")
    Page<Activity>findAllclosedActivities(@Param("todaysdate")LocalDateTime date ,Pageable pageable);

    @Query(value="Select count(*) from Activity where Activity.date<= :todaysdate",nativeQuery = true)
    Integer getPastCount(LocalDateTime todaysdate);

    @Query(value="Select * from Activity where Activity.date<= :todaysdate and LOWER(title) LIKE %:searchtitle%",nativeQuery = true)
    List<Activity>getPastActivitiesByTitle(@Param("todaysdate")LocalDateTime date, @Param("searchtitle") String searchtitle);

    @Query(value="Select EXTRACT(YEAR FROM date),count(*) from Activity GROUP BY EXTRACT(YEAR FROM date)",nativeQuery = true)
    List<BigInteger[]> getYearWiseCount();



}
