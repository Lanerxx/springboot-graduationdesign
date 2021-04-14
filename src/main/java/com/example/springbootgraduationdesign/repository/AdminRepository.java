package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Integer>{
    @Query("SELECT a FROM Admin  a WHERE a.a_name=:name")
    Optional<Admin> getAdminByName (@Param("name")String name);

}
