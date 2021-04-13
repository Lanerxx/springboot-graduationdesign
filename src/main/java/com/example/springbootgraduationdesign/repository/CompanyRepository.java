package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends BaseRepository<Company, Integer>{
    @Query("SELECT c FROM Company  c WHERE c.c_f_telephone=:telephone")
    Optional<Company> getCompanyByFTelephone (@Param("telephone")String telephone);

    @Query("SELECT c FROM Company  c WHERE c.c_name=:name")
    Optional<List<Company>> getCompanyByName (@Param("name")String name);

    @Query("SELECT c FROM Company  c WHERE c.c_s_code=:code")
    Optional<Company> getCompanyBySCode (@Param("code")String code);
}
