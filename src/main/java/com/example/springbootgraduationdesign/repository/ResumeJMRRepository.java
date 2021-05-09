package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.ResumeJMR;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeJMRRepository extends BaseRepository<ResumeJMR, Integer>{
    @Modifying
    @Query("DELETE  FROM ResumeJMR jmr WHERE jmr.jmr_job.j_id=:jid ")
    void deleteResumeJMRsByJob (@Param("jid")int jid);

    @Modifying
    @Query("DELETE  FROM ResumeJMR jmr WHERE jmr.jmr_resume.r_id=:rid ")
    void deleteResumeJMRsByResume (@Param("rid")int rid);

    @Query("SELECT jmr FROM ResumeJMR  jmr WHERE jmr.jmr_resume.r_student.s_id = :sid")
    Optional<List<ResumeJMR>> getResumeJMRsByStudent (@Param("sid")int sid);

    @Query("SELECT jmr FROM ResumeJMR  jmr WHERE jmr.jmr_resume.r_id = :rid")
    Optional<List<ResumeJMR>> getResumeJMRsByResume (@Param("rid")int rid);

    @Query("SELECT jmr FROM ResumeJMR  jmr WHERE jmr.jmr_job.j_id = :jid")
    Optional<List<ResumeJMR>> getResumeJMRsByJob (@Param("jid")int jid);

}
