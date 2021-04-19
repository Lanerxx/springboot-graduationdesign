package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.JobSMR;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobSMRRepository extends BaseRepository<JobSMR, Integer>{
    @Query("SELECT smr FROM JobSMR smr WHERE smr.smr_job.j_id=:jid")
    Optional<List<JobSMR>> getJobSMRsByJob (@Param("jid")int jid);

    @Query("SELECT smr FROM JobSMR  smr WHERE smr.smr_job.j_id=:jid AND smr.smr_resume.r_id=:rid")
    Optional<JobSMR> getJobSMRByJobAndResume (@Param("jid")int jid, @Param("rid") int rid);

    @Modifying
    @Query("DELETE  FROM JobSMR  smr WHERE smr.smr_job.j_id=:jid ")
    void deleteJobSMRsByJob (@Param("jid")int jid);

    @Modifying
    @Query("DELETE  FROM JobSMR  smr WHERE smr.smr_resume.r_id=:rid ")
    void deleteJobSMRsByResume (@Param("rid")int rid);
}
