package com.eracambodia.era.repository.api_building;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_building.response.Agent;
import com.eracambodia.era.model.api_building.response.Buildings;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingsRepo {

    @Select("SELECT * " +
            "FROM building LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "agent",column = "id",one = @One(select = "findAgentOfBuildings")),
            @Result(property = "totalCost",column = "id",one = @One(select = "findTotalCostOfBuildings")),
            @Result(property="filePath",column = "id",one = @One(select="findFilePathOfBuildings"))
    })
    List<Buildings> findBuildings(Pagination pagination);
    @Select("SELECT users.username,users.image,users.id,users.uuid " +
            "FROM users " +
            "INNER JOIN transaction ON transaction.user_id=users.id " +
            "INNER JOIN building ON building.id=transaction.owner_id " +
            "WHERE transaction.owner_id=#{id} AND transaction.table_name = 'Building' AND transaction.status=building.status " +
            "ORDER BY transaction.create_date LIMIT 1")
    @Results({
            @Result(property = "name",column = "username"),
            @Result(property = "profilePhoto",column = "image")
    })
    Agent findAgentOfBuildings();
    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    Double findTotalCostOfBuildings();
    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} " +
            "ORDER BY id DESC LIMIT 1")
    String findFilePathOfBuildings();

    @Select("select count(id) from building")
    int countBuildingsRecord();
}
