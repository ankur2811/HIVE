package com.example.demo.Repository;


import com.example.demo.Models.Login;
import com.example.demo.Models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tags,Long> {
    @Query("SELECT s FROM Tags s WHERE s.tag=?1")
    List<Tags> findUserByTags(String username);

    @Query("SELECT s.id FROM Tags s WHERE s.tag=?1")
    Long findIdbyTag(String tag);

    @Query("SELECT s.id FROM Tags s WHERE LOWER(s.tag)=?1")
    Long findIdbyTag1(String tag);

    @Query("SELECT s.tag FROM Tags s WHERE s.id=?1")
    String findTagById(Long id);
}
