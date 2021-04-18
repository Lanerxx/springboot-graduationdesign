package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer>{    @Query("SELECT s FROM Student  s WHERE s.s_telephone=:telephone")
Optional<Student> getStudentByS_telephone (@Param("telephone")String telephone);

    @Query("SELECT s FROM Student  s WHERE s.s_name=:name")
    Optional<List<Student>> getStudentByName (@Param("name")String name);

    @Query("SELECT s FROM Student  s WHERE s.s_college=:college")
    Optional<List<Student>> getStudentByCollege (@Param("college")String college);

    @Query("SELECT s FROM Student  s WHERE s.s_c_level=:level AND s.s_e_history=:history")
    Optional<List<Student>> getStudentByCLevelAndHistory (
            @Param("gender") EnumWarehouse.C_LEVEL level,
            @Param("gender") EnumWarehouse.E_HISTORY history
    );

}
