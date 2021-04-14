package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Industry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndustryRepository extends BaseRepository<Industry, Integer>{
    @Query("SELECT i FROM Industry i WHERE i.i_name=:name")
    Optional<Industry> getIndustryByName (@Param("name")String name);
}
