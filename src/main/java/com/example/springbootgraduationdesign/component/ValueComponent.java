package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.*;
import javafx.geometry.Pos;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class ValueComponent {
    public final static int VALUE_ONE = 0;
    public final static int VALUE_TWO = 1;
    public final static int VALUE_THREE = 2;

    //------------计算jobSmrBase（与学生简历相比）-------------
    public int jobSmrBaseRanking(EnumWarehouse.RANKING ranking){
        if (ranking.ordinal() <= 1) return VALUE_THREE;
        if (ranking.ordinal() <= 3) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobSmrBaseRCount (int count){
        if (count >= 100) return VALUE_THREE;
        if (count >= 20) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobSmrBaseXCount (int count){
        if (count >= 3) return VALUE_THREE;
        if (count >= 1) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobSmrBaseLocation (String jobLocation, String studentLocation){
        if (jobLocation.equals(studentLocation)) return VALUE_THREE;
        //if ()
        return VALUE_ONE;
    }

    public int jobSmrBaseWelfare (EnumWarehouse.IF_IS_OR_NOT jobEvent, EnumWarehouse.IF_IS_NEED_OR_NOT studentEvent){
        if (studentEvent.ordinal() == 1 && jobEvent.ordinal() == 0) return VALUE_THREE;
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 0) return VALUE_TWO;
//        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
//        if (studentEvent.equals("NO_REQUIREMENT") && jobEvent.equals("YES")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobSmrBaseBTrip (EnumWarehouse.IF_IS_NEED_OR_NOT jobEvent, EnumWarehouse.IF_IS_OR_NOT studentEvent){
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 1) return VALUE_THREE;
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 0) return VALUE_TWO;
//        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
//        if (studentEvent.equals("YES") && jobEvent.equals("NO_REQUIREMENT")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobSmrBaseCLevel (EnumWarehouse.C_LEVEL jobLevel, EnumWarehouse.C_LEVEL studentLevel){
        switch (Math.abs(studentLevel.ordinal() - jobLevel.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobSmrBaseEHistory (EnumWarehouse.E_HISTORY jobHistory, EnumWarehouse.E_HISTORY studentHistory ){
        switch (Math.abs(studentHistory.ordinal() - jobHistory.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobSmrBaseELanguage (EnumWarehouse.E_LANGUAGE jobLanguage, EnumWarehouse.E_LANGUAGE studentLanguage){
        switch (Math.abs(studentLanguage.ordinal() - jobLanguage.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobSmrBaseSRange (EnumWarehouse.S_RANGE jobRange, EnumWarehouse.S_RANGE studentRange){
        switch (Math.abs(studentRange.ordinal() - jobRange.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobSmrBaseCareer (String jobCareer, String studentCareer){
        return VALUE_ONE;
    }

    public int jobSmrBaseExperience (String jobCareer, String studentCareer){
        return VALUE_ONE;
    }

    //jobSmr_Position
    public int jobSmrBasePosition(Position jobPosition, List<Position> studentPositions){
        for (Position studentPosition : studentPositions){
            if (jobPosition == studentPosition) return VALUE_THREE;
        }
        return VALUE_ONE;
    }

    //------------计算resumeJmrBase（与公司岗位相比）-------------

    public int resumeJmrBaseCScale(int count){
        if (count >= 1000) return VALUE_THREE;
        if (count >= 500) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeJmrBaseCFStage(EnumWarehouse.FINANCING_STAGE stage){
        if (stage.ordinal() <= 1) return VALUE_THREE;
        if (stage.ordinal() <= 4) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeJmrBaseCLevel(EnumWarehouse.C_LEVEL jobLevel, EnumWarehouse.C_LEVEL studentLevel){
        switch (Math.abs(studentLevel.ordinal() - jobLevel.ordinal())){
            case 0: return VALUE_THREE;
            case 1: return VALUE_TWO;
            default: return VALUE_ONE;
        }
    }

    public int resumeJmrBaseEHistory(EnumWarehouse.E_HISTORY jobHistory, EnumWarehouse.E_HISTORY studentHistory){
        switch (Math.abs(studentHistory.ordinal() - jobHistory.ordinal())){
            case 0: return VALUE_THREE;
            case 1: return VALUE_TWO;
            default: return VALUE_ONE;
        }
    }

    public int resumeJmrBaseELanguage(EnumWarehouse.E_LANGUAGE jobLanguage, EnumWarehouse.E_LANGUAGE studentLanguage){
        switch (Math.abs(studentLanguage.ordinal() - jobLanguage.ordinal())){
            case 0: return VALUE_THREE;
            case 1: return VALUE_TWO;
            default: return VALUE_ONE;
        }
    }

    public int resumeJmrBaseJCount (int count){
        if (count >= 100) return VALUE_THREE;
        if (count >= 20) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeJmrBaseWelfare (EnumWarehouse.IF_IS_OR_NOT jobEvent, EnumWarehouse.IF_IS_NEED_OR_NOT studentEvent){
        if (studentEvent.ordinal() == 1 && jobEvent.ordinal() == 0) return VALUE_THREE;
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 0) return VALUE_TWO;
//        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
//        if (studentEvent.equals("NO_REQUIREMENT") && jobEvent.equals("YES")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeJmrBaseBTrip (EnumWarehouse.IF_IS_NEED_OR_NOT jobEvent, EnumWarehouse.IF_IS_OR_NOT studentEvent){
        if (studentEvent.ordinal() == 1 && jobEvent.ordinal() == 2) return VALUE_THREE;
        if (studentEvent.ordinal() == 1 && jobEvent.ordinal() == 0) return VALUE_TWO;
//        if (studentEvent.equals("No") && jobEvent.equals("NO")) return VALUE_THREE;
//        if (studentEvent.equals("No") && jobEvent.equals("NO_REQUIREMENT")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeJmrBaseSRange (EnumWarehouse.S_RANGE jobRange, EnumWarehouse.S_RANGE studentRange){
        switch (Math.abs(studentRange.ordinal() - jobRange.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    //jmr_b_position;
    public int resumeJmrBasePosition(Position jobPosition, List<Position> studentPositions){
        for (Position studentPosition : studentPositions){
            if (jobPosition == studentPosition) return VALUE_THREE;
        }
        return VALUE_ONE;
    }

    //jmr_b_location;
    public int resumeJmrBaseLocation(String jobLocation, String studentLocation){
        if (jobLocation.equals(studentLocation)) return VALUE_THREE;
        return VALUE_ONE;
    }


    //------------计算job（与其他job相比）-------------
    public int jobCount(int jobCount){
        if (jobCount > 100) return VALUE_THREE;
        if (jobCount > 20) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobSalaryRange(EnumWarehouse.S_RANGE jobRange){
        if (jobRange.ordinal() >= 4) return VALUE_THREE;
        if (jobRange.ordinal() >= 2) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobWelfare (EnumWarehouse.IF_IS_OR_NOT jobEvent){
        if (jobEvent.ordinal() == 0) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobScare(int jobScare){
        if (jobScare >= 1000) return VALUE_THREE;
        if (jobScare >= 500) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobFRange(EnumWarehouse.FINANCING_STAGE jobFRange){
        if (jobFRange.ordinal() <= 1) return VALUE_THREE;
        if (jobFRange.ordinal() <=4) return VALUE_TWO;
        return VALUE_ONE;
    }

    //------------计算resume（与其他resume相比）-------------
    public int resumeCLevel(EnumWarehouse.C_LEVEL resumeCLevel){
        if (resumeCLevel.ordinal() <= 1) return VALUE_THREE;
        if (resumeCLevel.ordinal() <= 3) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeEHistory(EnumWarehouse.E_HISTORY resumeHistory){
        if (resumeHistory.ordinal() == 0) return VALUE_THREE;
        if (resumeHistory.ordinal() <= 2) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeELanguage(EnumWarehouse.E_LANGUAGE resumeELanguage){
        if (resumeELanguage.ordinal() <= 3) return VALUE_THREE;
        if (resumeELanguage.ordinal() <= 6) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeFLanguage(EnumWarehouse.F_LANGUAGE resumeFLanguage){
        if (resumeFLanguage.ordinal() <= 1) return VALUE_THREE;
        if (resumeFLanguage.ordinal() <= 3) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeRanking(EnumWarehouse.RANKING resumeRanking){
        if (resumeRanking.ordinal() <= 1) return VALUE_THREE;
        if (resumeRanking.ordinal() <= 3) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeRCount (int count){
        if (count >= 100) return VALUE_THREE;
        if (count >= 20) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeXCount (int count){
        if (count >= 3) return VALUE_THREE;
        if (count >= 1) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int resumeTempt (){
        Random random = new Random();
        return random.nextInt(20);
    }

    //-----------辅助计算--------------Ave
    public double getAverage(double x, double y){
        if ((x + y) == 0) return 0;
        return 2*(x*y)/(x+y);
    }

    //-----------枚举计算--------------
    public EnumWarehouse.C_LEVEL getEWCLevel(int i){
        switch (i){
            case 0:
                return EnumWarehouse.C_LEVEL._985;
            case 1:
                return EnumWarehouse.C_LEVEL._211;
            case 2:
                return EnumWarehouse.C_LEVEL.一批本科;
            case 3:
                return EnumWarehouse.C_LEVEL.二批本科;
            case 4:
                return EnumWarehouse.C_LEVEL.专科;
            default:
                return EnumWarehouse.C_LEVEL.职高;
        }
    }

    public EnumWarehouse.E_HISTORY getEWEHistory(int i){
        switch (i){
            case 0:
                return EnumWarehouse.E_HISTORY.博士;
            case 1:
                return EnumWarehouse.E_HISTORY.硕士;
            case 2:
                return EnumWarehouse.E_HISTORY.本科;
            default:
                return EnumWarehouse.E_HISTORY.专科;
        }
    }

    public EnumWarehouse.E_LANGUAGE getEWELanguage(int i){
        switch (i){
            case 0:
                return EnumWarehouse.E_LANGUAGE.不需要;
            case 1:
                return EnumWarehouse.E_LANGUAGE.国外留学;
            case 2:
                return EnumWarehouse.E_LANGUAGE.托福雅思;
            case 3:
                return EnumWarehouse.E_LANGUAGE.TEM8;
            case 4:
                return EnumWarehouse.E_LANGUAGE.TEM4;
            case 5:
                return EnumWarehouse.E_LANGUAGE.CET6;
            case 6:
                return EnumWarehouse.E_LANGUAGE.CET4;
            default:
                return EnumWarehouse.E_LANGUAGE.无;
        }
    }

    public EnumWarehouse.IF_IS_OR_NOT getEWIsOrNot(int i){
        switch (i){
            case 0:
                return EnumWarehouse.IF_IS_OR_NOT.是;
            default:
                return EnumWarehouse.IF_IS_OR_NOT.否;
        }
    }

    public EnumWarehouse.IF_IS_NEED_OR_NOT getEWIsNeedOrNot(int i){
        switch (i){
            case 0:
                return EnumWarehouse.IF_IS_NEED_OR_NOT.不需要;
            case 1:
                return EnumWarehouse.IF_IS_NEED_OR_NOT.是;
            default:
                return EnumWarehouse.IF_IS_NEED_OR_NOT.否;
        }
    }

    public EnumWarehouse.FINANCING_STAGE getEWFStage(int i){
        switch (i){
            case 0:
                return EnumWarehouse.FINANCING_STAGE.已上市;
            case 1:
                return EnumWarehouse.FINANCING_STAGE.D轮;
            case 2:
                return EnumWarehouse.FINANCING_STAGE.C轮;
            case 3:
                return EnumWarehouse.FINANCING_STAGE.B轮;
            case 4:
                return EnumWarehouse.FINANCING_STAGE.A轮;
            case 5:
                return EnumWarehouse.FINANCING_STAGE.天使轮;
            case 6:
                return EnumWarehouse.FINANCING_STAGE.种子;
            default:
                return EnumWarehouse.FINANCING_STAGE.不需要融资;
        }
    }

    public EnumWarehouse.S_RANGE getEWSRange(int i){
        switch (i){
            case 0:
                return EnumWarehouse.S_RANGE.四千及以下;
            case 1:
                return EnumWarehouse.S_RANGE.四至六千;
            case 2:
                return EnumWarehouse.S_RANGE.六至八千;
            case 3:
                return EnumWarehouse.S_RANGE.八至一万;
            default:
                return EnumWarehouse.S_RANGE.一万及以上;
        }
    }





}
