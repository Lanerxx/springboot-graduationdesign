package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import lombok.Data;

@Data
public class StudentListStatisticalFormVo {

    private int resumeTotalCount;
    private int resumeAveCount;
    private int resumeTotalHot;
    private int resumeAveHot;

    private EnumWarehouse.C_LEVEL studentAveCLevel;
    private EnumWarehouse.E_HISTORY studentAveHistory;
    private EnumWarehouse.E_LANGUAGE studentAveELanguage;
    private EnumWarehouse.IF_IS_OR_NOT studentAveIfCareer;
    private EnumWarehouse.IF_IS_OR_NOT studentAveIfProject;
    private EnumWarehouse.IF_IS_OR_NOT studentAveIfFresh;
    private int resumeAvePCount;
    private int resumeAveCCount;
    private int resumeAveHCount;
    private int resumeAveSCount;

    private EnumWarehouse.IF_IS_OR_NOT resumeAveBTrip;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveABonus;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveCUp;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveHSubside;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveInsurance;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveOAllowance;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAvePLeave;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveStock;
    private EnumWarehouse.IF_IS_NEED_OR_NOT resumeAveTSubside;

    //简历跟踪汇总
    private int favoredTotalCount;
    private int favoredAveCount;
    private int oneWayTotalCount;
    private int oneWayAveCount;
    private int twoWayTotalCount;
    private int twoWayAveCount;
}
