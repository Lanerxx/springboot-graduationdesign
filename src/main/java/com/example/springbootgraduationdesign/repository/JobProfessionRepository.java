package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.JobProfession;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobProfessionRepository extends BaseRepository<JobProfession, Integer> {
    @Modifying
    @Query("DELETE FROM JobProfession jp WHERE jp.jobProfessionPK.jp_job.j_id =:jid AND jp.jobProfessionPK.jp_profession.pr_id=:pid")
    void deleteJobProfession(@Param("jid")int jid,@Param("pid")int pid);

    @Modifying
    @Query("DELETE FROM JobProfession jp WHERE jp.jobProfessionPK.jp_job.j_id =:jid")
    void deleteJobProfessionsByJob(@Param("jid")int jid);
}
