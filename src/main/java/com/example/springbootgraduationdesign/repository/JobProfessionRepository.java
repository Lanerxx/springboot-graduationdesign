package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.JobProfession;
import com.example.springbootgraduationdesign.entity.Profession;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobProfessionRepository extends BaseRepository<JobProfession, Integer> {
    @Modifying
    @Query("DELETE FROM JobProfession jp WHERE jp.jobProfessionPK.jp_job.j_id =:jid AND jp.jobProfessionPK.jp_profession.pr_id=:pid")
    void deleteJobProfession(@Param("jid")int jid,@Param("pid")int pid);

    @Modifying
    @Query("DELETE FROM JobProfession jp WHERE jp.jobProfessionPK.jp_job.j_id =:jid")
    void deleteJobProfessionsByJob(@Param("jid")int jid);

    @Query("SELECT jp FROM JobProfession jp WHERE jp.jobProfessionPK.jp_job.j_id=:jid")
    Optional<List<JobProfession>> getJobProfessionsByJob (@Param("jid")int jid);

}
