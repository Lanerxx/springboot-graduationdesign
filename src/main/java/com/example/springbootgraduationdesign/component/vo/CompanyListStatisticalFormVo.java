package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import lombok.Data;

@Data
public class CompanyListStatisticalFormVo {
    //企业总体实力
    private int jobTotalCount;
    private int jobAveCount;
    private int jobTotalHot;
    private int jobAveHot;
    private int companyAveScale;
    private EnumWarehouse.FINANCING_STAGE companyAveStage;

    //岗位总体待遇
    private EnumWarehouse.S_RANGE jobAveSalary;
    private EnumWarehouse.IF_IS_OR_NOT jobAveABonus;
    private EnumWarehouse.IF_IS_OR_NOT jobAveCUp;
    private EnumWarehouse.IF_IS_OR_NOT jobAveHSubside;
    private EnumWarehouse.IF_IS_OR_NOT jobAveInsurance;
    private EnumWarehouse.IF_IS_OR_NOT jobAveOAllowance;
    private EnumWarehouse.IF_IS_OR_NOT jobAvePLeave;
    private EnumWarehouse.IF_IS_OR_NOT jobAveStock;
    private EnumWarehouse.IF_IS_OR_NOT jobAveTSubside;

    //岗位总体要求
    private EnumWarehouse.C_LEVEL jobAveCLevel;
    private EnumWarehouse.E_HISTORY jobAveHistory;
    private EnumWarehouse.E_LANGUAGE jobAveELanguage;
    private EnumWarehouse.IF_IS_OR_NOT jobAveIfCareer;
    private EnumWarehouse.IF_IS_OR_NOT jobAveIfProject;
    private EnumWarehouse.IF_IS_OR_NOT jobAveIfFresh;
    private EnumWarehouse.IF_IS_NEED_OR_NOT jobAveBTrip;


    //岗位跟踪汇总
    private int favoredTotalCount;
    private int favoredAveCount;
    private int oneWayTotalCount;
    private int oneWayAveCount;
    private int twoWayTotalCount;
    private int twoWayAveCount;

}
