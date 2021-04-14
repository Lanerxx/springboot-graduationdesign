package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.StudentJMR;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentJMRRepository extends BaseRepository<StudentJMR, Integer>{
    @Modifying
    @Query("DELETE  FROM StudentJMR jmr WHERE jmr.jmr_job.j_id=:jid ")
    void deleteStudentJMRsByJob (@Param("jid")int jid);

    @Query("SELECT jmr FROM StudentJMR  jmr WHERE jmr.jmr_student.s_id=:sid")
    Optional<List<StudentJMR>> getStudentJMRsByStudent (@Param("sid")int sid);

}
