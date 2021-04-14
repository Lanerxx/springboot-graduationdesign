package com.example.springbootgraduationdesign.component.vo;

import lombok.Data;

@Data
public class UserVo {
    String userPhoneNumber;
    String userPassword;
    String userName;
    boolean admin;
    boolean student;
    boolean company;
    boolean jobDirector;
}
