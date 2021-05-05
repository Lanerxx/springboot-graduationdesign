package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Resume;
import com.example.springbootgraduationdesign.entity.StudentFavoredJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentFavoredJobRepository extends BaseRepository<StudentFavoredJob,Integer>{
    @Query("SELECT sfj FROM StudentFavoredJob  sfj WHERE sfj.studentFavoredJobPK.sfj_student.s_id=:sid")
    Optional<List<StudentFavoredJob>> getStudentFavoredJobsByStudent (@Param("sid")int sid);

}
