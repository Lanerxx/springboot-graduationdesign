package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.entity.Position;
import com.example.springbootgraduationdesign.repository.PositionRepository;
import com.example.springbootgraduationdesign.repository.StudentPositionRepository;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private StudentPositionRepository studentPositionRepository;

    /*---------------岗位信息（Position）----------------
    -------检索：管理员
    -------更新：管理员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public Position addPosition(Position position){
        positionRepository.save(position);
        return position;
    }
    public List<Position> addPositions(List<Position> positions){
        return null;
    }

    public void deletePosition(int pid){
        positionRepository.deleteById(pid);
    }
    public void deleteAllPositions(){
        positionRepository.deleteAll();
    }

    public Position updatePosition(Position position){
        positionRepository.save(position);
        return position;
    }

    public List<Position> getAllPositions(){
        return positionRepository.findAll();
    }
    public Position getPosition(int pid){
        return positionRepository.findById(pid).orElse(null);
    }
    public Position getPosition(String name){
        return positionRepository.getPositionByName(name).orElse(null);
    }
    public List<String> listPositionsName(){
        List<Position> positions = positionRepository.findAll();
        List<String> positionsName = new ArrayList<>();
        positions.forEach(position -> {
            positionsName.add(position.getPo_name());
        });
        return positionsName;
    }
    public List<String> listPositionNameByStudent(int sid){
        return studentPositionRepository.listPositionNameByStudent(sid).orElse(new ArrayList<>());
    }
    public List<Position> getPositionsByStudent(int sid){
        return studentPositionRepository.getPositionsByStudent(sid).orElse(new ArrayList<>());
    }


}
