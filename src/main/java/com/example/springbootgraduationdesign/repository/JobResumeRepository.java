package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Job;
import com.example.springbootgraduationdesign.entity.JobResume;
import com.example.springbootgraduationdesign.entity.Resume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobResumeRepository extends BaseRepository<Job,Integer>{
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid")
    Optional<JobResume> getJobResumesByJob (@Param("jid")int jid);

    @Query("SELECT jr.jobResumePK.jr_resume FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid")
    Optional<Resume> getJRResumesByJob (@Param("jid")int jid);


    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid")
    Optional<JobResume> getJobResumesByResume (@Param("rid")int rid);

    @Query("SELECT jr.jobResumePK.jr_job FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid")
    Optional<Job> getJRJobsByResume (@Param("sid")int sid);

}
