package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.CompanyFavoredResume;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyFavoredResumeRepository  extends BaseRepository<CompanyFavoredResume, Integer> {
    @Query("SELECT cfr FROM CompanyFavoredResume cfr WHERE cfr.companyFavoredResumePK.cfr_company.c_id=:cid")
    Optional<List<CompanyFavoredResume>> getCompanyFavoredResumesByCompany (@Param("cid")int cid);

    @Query("SELECT cfr FROM CompanyFavoredResume cfr WHERE cfr.companyFavoredResumePK.cfr_company.c_id=:cid AND cfr.companyFavoredResumePK.cfr_resume.r_id=:rid")
    Optional<CompanyFavoredResume> getCompanyFavoredResumeByCompanyAndResume (@Param("cid")int cid, @Param("rid")int rid);

    @Modifying
    @Query("DELETE FROM CompanyFavoredResume cfr WHERE cfr.companyFavoredResumePK.cfr_company.c_id=:cid AND cfr.companyFavoredResumePK.cfr_resume.r_id=:rid")
    void deleteCompanyFavoredResumeByCompanyAndResume(@Param("cid")int cid, @Param("rid")int rid);

}
