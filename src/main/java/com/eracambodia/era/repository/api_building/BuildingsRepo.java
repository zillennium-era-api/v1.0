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
            "FROM building ORDER BY user_create_date DESC " +
            "LIMIT #{limit} OFFSET #{offset} ")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "village",column = "village_code",one = @One(select = "getVillage")),
            @Result(property = "commune",column = "commune_code",one = @One(select = "getCommune")),
            @Result(property = "countryName",column = "country"),
            @Result(property = "street",column = "street_number_or_name"),
            @Result(property = "district",column = "district_code",one = @One(select = "getDestrict")),
            @Result(property = "agent",column = "id",one = @One(select = "findAgentOfBuildings")),
            @Result(property = "totalCost",column = "id",one = @One(select = "findTotalCostOfBuildings")),
            @Result(property="filePath",column = "id",one = @One(select="findFilePathOfBuildings")),
            @Result(property = "cityOrProvince",column = "city_or_province",one = @One(select = "getCityOrProvince"))
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
            "FROM building")
    int countBuildingsRecord();

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{city_or_province}")
    String getCityOrProvince();
}
