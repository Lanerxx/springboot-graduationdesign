package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Integer>{
}
