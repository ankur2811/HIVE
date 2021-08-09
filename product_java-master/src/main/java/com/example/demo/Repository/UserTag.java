package com.example.demo.Repository;


import com.example.demo.Models.Tags;
import com.example.demo.Models.UserTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserTag extends JpaRepository<UserTags,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserTags s WHERE s.UserId=?1")
    void deletefromtag(Long UserId);
}
