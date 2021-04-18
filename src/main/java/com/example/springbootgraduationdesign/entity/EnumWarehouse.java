package com.example.springbootgraduationdesign.entity;

public class EnumWarehouse {
    public enum GENDER{
        NO_REQUIREMENT, MALE, FEMALE
    }

    public enum C_LEVEL{
        _985, _211, FIRST_UNDERGRADUATE, SECOND_UNDERGRADUATE,
        JUNIOR_COLLEGE, VOCATIONAL_AND_TECHNICAL_COLLEGE
    }
    public enum E_HISTORY{
        DOCTOR, MASTER, BACHELOR, JUNIOR_COLLEGE
    }
    public enum S_RANGE{
        _4, _4_6, _6_8, _8_10, _10
    }
    public enum IF_IS_OR_NOT{
        YES, NO
    }
    public enum IF_IS_NEED_OR_NOT{
        NO_REQUIREMENT, YES, NO
    }
    public enum E_LANGUAGE{
        NO_REQUIREMENT, FOREIGN_EXPERIENCE, TOEFL_IELTS, TEM8, TEM4, CET6, CET4, NONE
    }
    public enum F_LANGUAGE{
        NO_REQUIREMENT, FOREIGN_EXPERIENCE, N2, N1, NONE
    }
    public enum RANKING{
        _0_10, _10_20, _20_30, _30_40, _40
    }
}
