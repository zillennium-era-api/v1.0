package com.eracambodia.era.repository;

import com.eracambodia.era.model.Building;
import com.eracambodia.era.model.BuildingStatusUpdate;
import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository {
    @Select("select count(id) from building")
    int countBuildingRecord();

    @Select("select * from building limit #{limit} offset #{offset}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "user",column = "id",javaType =User.class,one = @One(select = "findAgent")),
            @Result(property = "totalCost",column = "id",one = @One(select = "findTotalCost")),
            @Result(property="filePath",column = "id",one = @One(select="findFilePath"))
    })
    List<Building> findBuildings(Pagination pagination);
    @Select("select users.username,users.image,users.id,users.uuid from users inner join transaction on transaction.user_id=users.id inner join building on building.id=transaction.owner_id where transaction.owner_id=#{id} and transaction.table_name = 'building' and transaction.status=building.status ORDER BY transaction.create_date LIMIT 1")
    User findAgent();
    @Select("select total_project_cost from building_basic_information where building_id=#{id}")
    Double findTotalCost();
    @Select("select paths from file where owner_id=#{id}")
    String findFilePath();


    @Select("select * from building where uuid=#{uuid}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "user",column = "id",javaType =User.class,one = @One(select = "findAgent")),
            @Result(property = "totalCost",column = "id",one = @One(select = "findTotalCost")),
            @Result(property="filePath",column = "id",one = @One(select="findFilePath"))
    })
    Building findBuildingByUUID(String uuid);

    @Select("select id from building where id=#{ownerId} ")
    Integer findBuildingIdById(@Param("ownerId")int ownerId);

    @Select(value= "{CALL update_building_status(#{ownerId},#{tableName},#{status},#{agentId})}")
    @Options(statementType = StatementType.CALLABLE)
    Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate);


}
