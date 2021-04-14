package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.StudentIndustry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIndustryRepository extends BaseRepository<StudentIndustry, Integer>{
    @Modifying
    @Query("DELETE FROM StudentIndustry si WHERE si.studentIndustryPK.si_student.s_id =:sid")
    void deleteStudentIndustriesByStudent(@Param("sid") int sid);
}
