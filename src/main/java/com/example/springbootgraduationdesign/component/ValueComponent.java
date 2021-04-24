package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.entity.Industry;
import com.example.springbootgraduationdesign.entity.Position;
import org.springframework.stereotype.Component;

@Component
public class ValueComponent {
    public final static int VALUE_ONE = 0;
    public final static int VALUE_TWO = 1;
    public final static int VALUE_THREE = 2;

    //------------计算jobJmrBase（与学生简历相比）-------------
    public int jobJmrBaseRanking(EnumWarehouse.RANKING ranking){
        if (ranking.ordinal() <= 1) return VALUE_THREE;
        if (ranking.ordinal() <= 3) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseRCount (int count){
        if (count >= 100) return VALUE_THREE;
        if (count >= 20) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseXCount (int count){
        if (count >= 3) return VALUE_THREE;
        if (count >= 1) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseLocation (String jobLocation, String studentLocation){
        if (jobLocation.equals(studentLocation)) return VALUE_THREE;
        //if ()
        return VALUE_ONE;
    }

    public int jobJmrBaseWelfare (EnumWarehouse.IF_IS_OR_NOT jobEvent, EnumWarehouse.IF_IS_NEED_OR_NOT studentEvent){
        if (studentEvent.ordinal() == 1 && jobEvent.ordinal() == 0) return VALUE_THREE;
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 0) return VALUE_TWO;
//        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
//        if (studentEvent.equals("NO_REQUIREMENT") && jobEvent.equals("YES")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseBTrip (EnumWarehouse.IF_IS_NEED_OR_NOT jobEvent, EnumWarehouse.IF_IS_OR_NOT studentEvent){
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 1) return VALUE_THREE;
        if (studentEvent.ordinal() == 0 && jobEvent.ordinal() == 0) return VALUE_TWO;
//        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
//        if (studentEvent.equals("YES") && jobEvent.equals("NO_REQUIREMENT")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseCLevel (EnumWarehouse.C_LEVEL jobLevel, EnumWarehouse.C_LEVEL studentLevel){
        switch (Math.abs(studentLevel.ordinal() - jobLevel.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseEHistory (EnumWarehouse.E_HISTORY jobHistory, EnumWarehouse.E_HISTORY studentHistory ){
        switch (Math.abs(studentHistory.ordinal() - jobHistory.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseELanguage (EnumWarehouse.E_LANGUAGE jobLanguage, EnumWarehouse.E_LANGUAGE studentLanguage){
        switch (Math.abs(studentLanguage.ordinal() - jobLanguage.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseSRange (EnumWarehouse.S_RANGE jobRange, EnumWarehouse.S_RANGE studentRange){
        switch (Math.abs(studentRange.ordinal() - jobRange.ordinal())){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseCareer (String jobCareer, String studentCareer){
        return VALUE_ONE;
    }

    public int jobJmrBaseExperience (String jobCareer, String studentCareer){
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
        if (jobEvent.equals("YES")) return VALUE_TWO;
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


}
