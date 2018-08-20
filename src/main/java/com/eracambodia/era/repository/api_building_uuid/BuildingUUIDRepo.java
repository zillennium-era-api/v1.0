package com.eracambodia.era.repository.api_building_uuid;

import com.eracambodia.era.model.api_building_uuid.response.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BuildingUUIDRepo {

    @Select("SELECT * " +
            "FROM building " +
            "WHERE uuid=#{uuid}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "countryCode",column = "country_code"),
            @Result(property = "countryName",column = "country_name"),
            @Result(property = "cityOrProvince",column = "city_or_province"),
            @Result(property = "village",column = "village_code"),
            @Result(property = "commune",column = "commune_code"),
            @Result(property = "district",column = "district_code"),
            @Result(property = "streetNameOrNumber",column = "street_number_or_name"),
            @Result(property = "numberOfFloor",column = "number_of_floor"),
            @Result(property = "countryName",column = "country"),
            @Result(property = "buildingHeight",column = "building_height"),
            @Result(property = "totalCost",column = "id",one = @One(select = "findTotalCostOfBuildingUUID")),
            @Result(property = "agent",column = "id",one = @One(select = "findAgentOfBuildingUUID")),
            @Result(property = "files" ,column = "id",many = @Many(select = "findFilesOfBuildingUUID")),
            @Result(property = "feature",column = "id",many = @Many(select = "findFeatureOfBuildingUUID")),
            @Result(property = "neighborhood",column = "id",many = @Many(select = "findNeighborhoodOfBuildingUUID"))
    })
    BuildingUUID findBuildingByUUID(String uuid);
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
    Agent findAgentOfBuildingUUID();
    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    Double findTotalCostOfBuildingUUID();
    @Select("SELECT owner_id ,paths,type " +
            "FROM file " +
            "WHERE owner_id=#{id}")
    @Results({
            @Result(property = "filePath" ,column = "paths"),
            @Result(property = "ownerId" ,column = "owner_id")
    })
    List<File> findFilesOfBuildingUUID();
    @Select("SELECT * FROM feature WHERE building_id=#{id}")
    @Results({
            @Result(property = "ownerId" ,column = "building_id"),
            @Result(property = "amenities",column = "amenity"),
            @Result(property = "includeService",column = "include_service"),
            @Result(property = "excludeService",column = "exclude_service")
    })
    Feature findFeatureOfBuildingUUID();
    @Select("SELECT * FROM neighborhood WHERE owner_id=#{id}")
    @Results({
            @Result(property = "ownerId",column = "owner_id")
    })
    List<Neighborhood> findNeighborhoodOfBuildingUUID();
}
