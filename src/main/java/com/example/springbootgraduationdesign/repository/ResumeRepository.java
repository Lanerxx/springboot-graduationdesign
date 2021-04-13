package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Resume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends BaseRepository<Resume, Integer> {
    @Query("SELECT r FROM Resume  r WHERE r.r_student.s_id=:sid")
    Optional<List<Resume>> getResumesByStudent (@Param("sid")int sid);

}
