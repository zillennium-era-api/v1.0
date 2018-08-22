package com.eracambodia.era.repository.api_building_available;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_building_available.response.BuildingAvailable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingAvailableRepo {
    @Select("SELECT id,name,uuid,type,status " +
            "FROM building " +
            "WHERE status IS NULL LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "totalCost",column = "id",one=@One(select = "getTotalCostOfBuildingAvailable")),
            @Result(property = "filePath",column = "id",one = @One(select = "getPathOfBuildingAvailable"))
    })
    List<BuildingAvailable> findBuildingAvailable(Pagination pagination);
    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    double getTotalCostOfBuildingAvailable();
    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} " +
            "ORDER BY id DESC LIMIT 1")
    String getPathOfBuildingAvailable();

    @Select("SELECT COUNT(id) " +
            "FROM building " +
            "WHERE status IS NULL")
    Integer countBuildingAvailable();
}
