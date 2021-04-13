package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Resume;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends BaseRepository<Resume, Integer> {
}
