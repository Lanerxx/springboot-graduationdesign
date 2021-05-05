package com.example.springbootgraduationdesign.entity;

public class EnumWarehouse {
    public enum GENDER{
        无要求, 女, 男
    }

    public enum C_LEVEL{
        _985, _211, 一批本科, 二批本科,
        专科, 职高
    }
    public enum E_HISTORY{
        博士, 硕士, 本科, 专科
    }
    public enum S_RANGE{
        四千及以下, 四至六千, 六至八千, 八至一万, 一万及以上
    }
    public enum IF_IS_OR_NOT{
        是, 否
    }
    public enum IF_IS_NEED_OR_NOT{
        不需要, 是, 否
    }
    public enum E_LANGUAGE{
        不需要, 国外留学, 托福雅思, TEM8, TEM4, CET6, CET4, 无
    }
    public enum F_LANGUAGE{
        不需要, 国外留学, N2, N1, 无
    }
    public enum RANKING{
        前百分之10, 百分之10到百分之20, 百分之20到百分之30, 百分之30到百分之40, 百分之40以后
    }
    public enum SUCCESS_DEGREE{
        高, 中, 低
    }

    public enum FINANCING_STAGE{
        已上市, D轮, C轮, B轮, A轮, 天使轮, 种子, 不需要融资
    }

}

