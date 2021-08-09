package com.example.demo.Repository;

import com.example.demo.Models.Activity;
import com.example.demo.Models.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login,Long> {
    @Query("SELECT s FROM Login s WHERE s.username=?1")
    Optional<Login> findUserByEmail(String username);


}
