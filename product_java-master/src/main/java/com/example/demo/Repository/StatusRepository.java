package com.example.demo.Repository;

import com.example.demo.Models.Activity;
import com.example.demo.Models.Login;
import com.example.demo.Models.StatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusDetails,Long> {
    @Query("SELECT s FROM StatusDetails s WHERE s.user=?1 AND s.activity=?2")
    StatusDetails findstatusByUser(Login userid,Activity activity);

    @Query("SELECT s FROM StatusDetails s WHERE s.user=?1 AND s.activity=?2")
    Optional<StatusDetails> findstatus(Login user,Activity activity);

    @Query("SELECT s FROM StatusDetails s WHERE s.status='registered' AND s.emailSent<>1")
    List<StatusDetails> findRegistered();

    @Query(value = "SELECT DISTINCT age "+
            "FROM my_table "+
            "WHERE firstname = :firstname AND lastname = :lastname " +
            "GROUP BY age " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1", nativeQuery = true)
    int retrieveAgeByFirstNameAndLastName(@Param("firstname") String firstname,
                                          @Param("lastname") String lastname);

    @Query(value="SELECT DISTINCT activity_activityid,count(*) FROM status_details WHERE"+
                  " status='registered'"+
                  " GROUP BY activity_activityid ORDER BY count(*) DESC LIMIT 1",nativeQuery = true)
    List<BigInteger> getTopActivity();

    @Query("SELECT s FROM StatusDetails s WHERE"+
            " s.status='participated'"+
            " AND s.user.id = ?1 ORDER BY s.activity.date Desc")
    List<StatusDetails> getRecentParticipated(Long id);
}
