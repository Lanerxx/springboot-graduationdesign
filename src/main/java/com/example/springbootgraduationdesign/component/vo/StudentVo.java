package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.Industry;
import com.example.springbootgraduationdesign.entity.Position;
import com.example.springbootgraduationdesign.entity.Student;
import lombok.Data;

import java.util.List;

@Data
public class StudentVo {
    private Student student;
    private List<Position> positions;
    private List<String> positionsName;
    private List<Industry> industries;
    private List<String> industriesName;

}
