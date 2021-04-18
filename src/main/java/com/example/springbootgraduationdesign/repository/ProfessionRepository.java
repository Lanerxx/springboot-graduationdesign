package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Profession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionRepository extends BaseRepository<Profession,Integer>{
    @Query("SELECT pr FROM Profession  pr WHERE pr.pr_s_class=:name")
    Optional<Profession> getProfessionBySClass (@Param("name")String name);

    @Query("SELECT pr FROM Profession  pr WHERE pr.pr_m_class=:name")
    Optional<List<Profession>> getProfessionByMClass (@Param("name")String name);


}
