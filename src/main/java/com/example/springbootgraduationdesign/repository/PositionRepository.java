package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends BaseRepository<Position, Integer>{
    @Query("SELECT po FROM Position  po WHERE po.po_name=:name")
    Optional<Position> getPositionByName (@Param("name")String name);

}
