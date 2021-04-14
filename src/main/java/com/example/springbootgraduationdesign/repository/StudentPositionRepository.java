package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.StudentPosition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPositionRepository extends BaseRepository<StudentPosition, Integer>{
    @Modifying
    @Query("DELETE FROM StudentPosition sp WHERE sp.studentPositionPK.sp_student.s_id = :sid")
    void deleteStudentPositionsByStudent(@Param("sid") int sid);
}
