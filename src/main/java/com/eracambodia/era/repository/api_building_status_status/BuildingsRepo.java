package com.eracambodia.era.repository.api_building_status_status;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_building_status_status.response.Agent;
import com.eracambodia.era.model.api_building_status_status.response.Buildings;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingsRepo {

    @Select("SELECT * " +
            "FROM building " +
            "WHERE building.status ILIKE #{status} " +
            "ORDER BY building.id DESC " +
            "LIMIT #{pagination.limit} OFFSET #{pagination.offset} ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "agent", column = "id", one = @One(select = "findAgentOfBuildings")),
            @Result(property = "totalCost", column = "id", one = @One(select = "findTotalCostOfBuildings")),
            @Result(property = "filePath", column = "id", one = @One(select = "findFilePathOfBuildings")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince")),
            @Result(property = "countFavorite", column = "id", one = @One(select = "countFavoriteOfBuilding"))
    })
    List<Buildings> findBuildings(@Param("pagination") Pagination pagination, @Param("status") String status);

    @Select("SELECT users.username,users.image,users.id,users.uuid " +
            "FROM users " +
            "INNER JOIN transaction ON transaction.user_id=users.id " +
            "INNER JOIN building ON building.id=transaction.owner_id " +
            "WHERE transaction.owner_id=#{id} AND transaction.table_name = 'Building' AND transaction.status=building.status AND building.status NOT ILIKE 'available' " +
            "ORDER BY transaction.id DESC LIMIT 1")
    @Results({
            @Result(property = "name", column = "username"),
            @Result(property = "profilePhoto", column = "image")
    })
    Agent findAgentOfBuildings();

    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    Double findTotalCostOfBuildings();

    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} AND type='image' " +
            "ORDER BY id DESC LIMIT 1")
    String findFilePathOfBuildings();

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{district_code}")
    String getDestrict();

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{village_code}")
    String getVillage();

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{commune_code}")
    String getCommune();

    @Select("SELECT count(id) " +
            "FROM building " +
            "WHERE status ILIKE #{status}")
    int countBuildingsRecord(String status);

    @Select("SELECT count(id) " +
            "FROM building ")
    int countAllBuildingsRecord();

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{city_or_province}")
    String getCityOrProvince();

    @Select("SELECT COUNT(id) " +
            "FROM favorite " +
            "WHERE owner_id=#{id}")
    int countFavoriteOfBuilding();

    @Select("SELECT * " +
            "FROM building " +
            "ORDER BY building.id DESC " +
            "LIMIT #{limit} OFFSET #{offset} ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "agent", column = "id", one = @One(select = "findAgentOfBuildings")),
            @Result(property = "totalCost", column = "id", one = @One(select = "findTotalCostOfBuildings")),
            @Result(property = "filePath", column = "id", one = @One(select = "findFilePathOfBuildings")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince")),
            @Result(property = "countFavorite", column = "id", one = @One(select = "countFavoriteOfBuilding"))
    })
    List<Buildings> findAllBuildings(Pagination pagination);

}
