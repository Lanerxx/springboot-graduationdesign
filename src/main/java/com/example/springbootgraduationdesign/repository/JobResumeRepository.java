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

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_company.c_id=:cid")
    Optional<List<JobResume>> getJobResumesByCompany (@Param("cid")int cid);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_student.s_id=:sid")
    Optional<List<JobResume>> getJobResumesByStudent (@Param("sid")int sid);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid")
    Optional<List<JobResume>> getJobResumesByResume (@Param("rid")int rid);

    //jobToResume = true 获取某resume被job选中的JobResumes
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid AND jr.jobToResume=:jobToResume")
    Optional<List<JobResume>> getJobResumesByResume_JobToResume (@Param("rid")int rid, @Param("jobToResume") boolean jobToResume);

    //jobToResume = true 获取某job选中过resume的JobResumes
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid AND jr.jobToResume=:jobToResume")
    Optional<List<JobResume>> getJobResumesByJob_JobToResume (@Param("jid")int jid, @Param("jobToResume") boolean jobToResume);

    //jobToResume = true 获取某job被resume选中的JobResumes
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid AND jr.resumeToJob=:resumeToJob")
    Optional<List<JobResume>> getJobResumesByJob (@Param("jid")int jid, @Param("resumeToJob") boolean resumeToJob);

    //resumeToJob = true 获取某resumea选中job的JobResumes
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_resume.r_id=:rid AND jr.resumeToJob=:resumeToJob")
    Optional<List<JobResume>> getJobResumesByResume_ResumeToJob (@Param("rid")int rid, @Param("resumeToJob") boolean resumeToJob);

    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid AND jr.jobResumePK.jr_resume.r_id=:rid")
    Optional<JobResume> getJobResumeByJobAndResume (@Param("jid")int jid,@Param("rid")int rid);


    //resumeToJob = true 获取某job被resumes选中的JobResumes
    @Query("SELECT jr FROM JobResume  jr WHERE jr.jobResumePK.jr_job.j_id=:jid AND jr.resumeToJob=:resumeToJob")
    Optional<List<JobResume>> getJobResumesByJob_ResumeToJob (@Param("jid")int jid, @Param("resumeToJob") boolean resumeToJob);

}
