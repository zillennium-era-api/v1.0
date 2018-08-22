package com.eracambodia.era.repository.api_building_held;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_building_held.response.Agent;
import com.eracambodia.era.model.api_building_held.response.BuildingHeld;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingHeldRepo {
    @Select("SELECT * " +
            "FROM building " +
            "WHERE status = 'HELD' LIMIT #{limit} OFFSET #{offset} ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "filePath",column = "id",one = @One(select="findFilePath")),
            @Result(property = "totalCost",column = "id",one = @One(select = "findTotalCost")),
            @Result(property = "agent", column = "id", one = @One(select = "findAgentHeld"))
    })
    List<BuildingHeld> findBuildingHeld(Pagination pagination);
    @Select("SELECT users.id,users.uuid,users.username,users.image " +
            "FROM users " +
            "INNER JOIN transaction ON transaction.user_id=users.id " +
            "INNER JOIN building ON building.id=transaction.owner_id " +
            "ORDER BY transaction.create_date DESC LIMIT 1 ")
    @Results({
            @Result(property = "name",column = "username"),
            @Result(property = "profilePhoto",column = "image")
    })
    Agent findAgentHeld();

    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    double findTotalCost();

    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} " +
            "ORDER BY id DESC LIMIT 1")
    String findFilePath();

    @Select("SELECT COUNT(id) " +
            "FROM building WHERE status = 'HELD'")
    int countBuildingHeld();
}
