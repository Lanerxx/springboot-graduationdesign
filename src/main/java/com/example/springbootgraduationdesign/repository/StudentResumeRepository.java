package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.StudentResume;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentResumeRepository extends  BaseRepository<StudentResume, Integer>{
    @Query("SELECT sr FROM StudentResume  sr WHERE sr.studentResumePK.sr_student.s_id=:sid")
    Optional<List<StudentResume>> getStudentResumesByStudent (@Param("sid")int sid);

    @Modifying
    @Query("DELETE  FROM StudentResume  sr WHERE sr.studentResumePK.sr_student.s_id=:sid AND sr.studentResumePK.sr_resume.r_id=:rid ")
    void deleteStudentResumeByStudentAndResume (@Param("sid")int sid,@Param("rid")int rid);

    @Query("SELECT sr FROM StudentResume  sr WHERE sr.studentResumePK.sr_student.s_id=:sid AND sr.studentResumePK.sr_resume.r_id=:rid ")
    Optional<StudentResume> getStudentResumeByStudentAndResume (@Param("sid")int sid, @Param("rid")int rid);

}
