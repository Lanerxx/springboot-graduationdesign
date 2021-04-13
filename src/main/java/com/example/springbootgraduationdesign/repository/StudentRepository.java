package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer>{
}
