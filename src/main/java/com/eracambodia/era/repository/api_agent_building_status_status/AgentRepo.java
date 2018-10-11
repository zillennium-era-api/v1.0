package com.eracambodia.era.repository.api_agent_building_status_status;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_agent_building_status_status.response.Agent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepo {
    @Select("SELECT * FROM (SELECT DISTINCT ON(building.id) building.id as bid,transaction.user_id,building.country,building.district_code,building.city_or_province,building.village_code,building.commune_code,building.street_number_or_name,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.id,transaction.id DESC " +
            "LIMIT #{pagination.limit} OFFSET #{pagination.offset})building WHERE status ilike #{status}")
    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince")),
            @Result(property = "userId",column = "user_id")
    })
    List<Agent> findAgentsProcess(@Param("status") String status, @Param("email") String email, @Param("pagination") Pagination pagination);

    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{bid}")
    double getTotalCost();

    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{bid} " +
            "ORDER BY id DESC LIMIT 1")
    String getFilePath();

    @Select("SELECT * FROM (SELECT DISTINCT ON(building.id) building.id as bid,transaction.user_id,building.country,building.district_code,building.city_or_province,building.village_code,building.commune_code,building.street_number_or_name,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.id,transaction.id DESC)building " +
            "WHERE status ilike #{status}")
    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince")),
            @Result(property = "userId",column = "user_id")
    })
    List<Agent> countAgentProcess(@Param("email") String email, @Param("status") String status);

    @Select("SELECT * FROM (SELECT DISTINCT ON(building.id) building.id as bid,transaction.user_id,building.country,building.district_code,building.city_or_province,building.village_code,building.commune_code,building.street_number_or_name,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.id,transaction.id DESC " +
            "LIMIT #{pagination.limit} OFFSET #{pagination.offset})building ")

    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince")),
            @Result(property = "userId",column = "user_id")
    })
    List<Agent> findAgentsAllProcess(@Param("email") String email, @Param("pagination") Pagination pagination);

    @Select("SELECT * FROM (SELECT DISTINCT ON(building.id) building.id as bid,transaction.user_id,building.country,building.district_code,building.city_or_province,building.village_code,building.commune_code,building.street_number_or_name,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.id,transaction.id DESC)building")
    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince")),
            @Result(property = "userId",column = "user_id")
    })
    List<Agent> countAgentAllProcess(String email);

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

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{city_or_province}")
    String getCityOrProvince();

    @Select("SELECT user_id " +
            "FROM transaction " +
            "WHERE owner_id=#{buildingId} " +
            "ORDER BY id DESC " +
            "LIMIT 1")
    Integer checkAgentTransaction(int buildingId);
}
