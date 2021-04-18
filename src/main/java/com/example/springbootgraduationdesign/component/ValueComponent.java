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

    public int jobJmrBaseRanking(EnumWarehouse.RANKING ranking){
        if (ranking.equals("_0_10") || ranking.equals("_10_20")) return VALUE_THREE;
        if (ranking.equals("_20_30") || ranking.equals("_30_40")) return VALUE_TWO;
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
        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
        if (studentEvent.equals("NO_REQUIREMENT") && jobEvent.equals("YES")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseBTrip (EnumWarehouse.IF_IS_NEED_OR_NOT jobEvent, EnumWarehouse.IF_IS_OR_NOT studentEvent){
        if (studentEvent.equals("YES") && jobEvent.equals("YES")) return VALUE_THREE;
        if (studentEvent.equals("YES") && jobEvent.equals("NO_REQUIREMENT")) return VALUE_TWO;
        return VALUE_ONE;
    }

    public int jobJmrBaseCLevel (EnumWarehouse.C_LEVEL jobLevel, EnumWarehouse.C_LEVEL studentLevel){
        switch (studentLevel.ordinal() - jobLevel.ordinal()){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseEHistory (EnumWarehouse.E_HISTORY jobHistory, EnumWarehouse.E_HISTORY studentHistory ){
        switch (studentHistory.ordinal() - jobHistory.ordinal()){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseELanguage (EnumWarehouse.E_LANGUAGE jobLanguage, EnumWarehouse.E_LANGUAGE studentLanguage){
        switch (studentLanguage.ordinal() - jobLanguage.ordinal()){
            case 0: return VALUE_ONE;
            case 1: return VALUE_TWO;
            default: return VALUE_THREE;
        }
    }

    public int jobJmrBaseSRange (EnumWarehouse.S_RANGE jobRange, EnumWarehouse.S_RANGE studentRange){
        switch (studentRange.ordinal() - jobRange.ordinal()){
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





}
