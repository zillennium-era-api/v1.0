package com.eracambodia.era.repository.api_agent_building_status_status;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_agent_building_status_status.response.Agent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepo {
    @Select("SELECT DISTINCT ON(building.id) building.id as bid,building.country,building.district_code,building.city_or_province,building.village_code,building.commune_code,building.street_number_or_name,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} AND building.status ILIKE #{status} " +
            "ORDER BY building.id,transaction.create_date DESC " +
            "LIMIT #{pagination.limit} OFFSET #{pagination.offset}")
    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince"))
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

    @Select("SELECT COUNT(*) FROM (SELECT DISTINCT ON(building.id) building.id " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} AND building.status ILIKE #{status}" +
            "ORDER BY building.id) as tb")
    Integer countAgentProcess(@Param("email") String email, @Param("status") String status);

    @Select("SELECT DISTINCT ON(building.id) building.id as bid,building.country,building.district_code,building.city_or_province,building.village_code,building.commune_code,building.street_number_or_name,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users ON users.id=transaction.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.id,transaction.create_date DESC")
    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince"))
    })
    List<Agent> findAgentsAllProcess(@Param("email") String email, @Param("pagination") Pagination pagination);

    @Select("SELECT COUNT(*) FROM (SELECT DISTINCT ON(building.id) building.id " +
            "FROM building " +
            "INNER JOIN transaction ON building.id=transaction.owner_id " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.id) as tb")
    Integer countAgentAllProcess(String email);

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
}
