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
public interface JobResumeRepository extends BaseRepository<JobResume,Integer>{
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid")
    Optional<JobResume> getJobResumesByJob (@Param("jid")int jid);

    @Query("SELECT jr.jobResumePK.jr_resume FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid")
    Optional<Resume> getJRResumesByJob (@Param("jid")int jid);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.studentToJob =:studentToJob AND jr.jobResumePK.jr_resume.r_student.s_id=:sid")
    Optional<List<JobResume>> getJobResumesByStudent (@Param("studentToJob") boolean studentToJob,@Param("sid")int sid);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid AND jr.studentToJob=:studentToJob")
    Optional<List<JobResume>> getJobResumesByResume (@Param("rid")int rid,@Param("studentToJob") boolean studentToJob);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid")
    Optional<List<JobResume>> getJobResumesByResume (@Param("rid")int rid);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid AND jr.jobResumePK.jr_resume.r_id=:rid")
    Optional<JobResume> getJobResumeByJobAndResume (@Param("jid")int jid,@Param("rid")int rid);

}
