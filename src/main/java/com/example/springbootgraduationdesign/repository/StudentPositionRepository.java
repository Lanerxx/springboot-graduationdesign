package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Position;
import com.example.springbootgraduationdesign.entity.StudentPosition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentPositionRepository extends BaseRepository<StudentPosition, Integer>{
    @Modifying
    @Query("DELETE FROM StudentPosition sp WHERE sp.studentPositionPK.sp_student.s_id = :sid")
    void deleteStudentPositionsByStudent(@Param("sid") int sid);

    @Query("select sp from StudentPosition sp WHERE sp.studentPositionPK.sp_student.s_id = :sid")
    Optional<List<StudentPosition>> listStudentPositionByStudent(@Param("sid") int sid);

    @Query("select sp.studentPositionPK.sp_position.po_name from StudentPosition sp WHERE sp.studentPositionPK.sp_student.s_id = :sid")
    Optional<List<String>> listPositionNameByStudent(@Param("sid") int sid);

    @Query("select sp.studentPositionPK.sp_position from StudentPosition sp WHERE sp.studentPositionPK.sp_student.s_id = :sid")
    Optional<List<Position>> getPositionsByStudent(@Param("sid") int sid);


}
