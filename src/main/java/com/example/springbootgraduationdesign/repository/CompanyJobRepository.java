package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.CompanyJob;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyJobRepository extends BaseRepository<CompanyJob, Integer>{
    @Query("SELECT cj FROM CompanyJob cj WHERE cj.companyJobPk.cj_job.j_position.po_name=:name")
    Optional<List<CompanyJob>> getCompanyJobsByPositionName (@Param("name")String name);

    @Query("SELECT cj FROM CompanyJob  cj WHERE cj.companyJobPk.cj_company.c_id=:cid")
    Optional<List<CompanyJob>> getCompanyJobByCompany (@Param("cid")int cid);

    @Query("SELECT cj FROM CompanyJob  cj WHERE cj.companyJobPk.cj_job.j_id=:jid ")
    Optional<CompanyJob> getCompanyJobByJob (@Param("jid")int jid);

    @Modifying
    @Query("DELETE  FROM CompanyJob  cj WHERE cj.companyJobPk.cj_job.j_id=:jid ")
    void deleteCompanyJobByJob (@Param("jid")int jid);

}
