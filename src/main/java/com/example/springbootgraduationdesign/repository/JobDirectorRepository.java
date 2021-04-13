package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.JobDrector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobDirectorRepository extends BaseRepository<JobDrector, Integer>{
    @Query("SELECT jd FROM JobDrector  jd WHERE jd.jd_telephone=:telephone")
    Optional<JobDrector> getJobDirectorByTelephone (@Param("telephone")String telephone);

    @Query("SELECT jd FROM JobDrector  jd WHERE jd.jd_name=:name")
    Optional<List<JobDrector>> getJobDirectorByName (@Param("name")String name);

}
