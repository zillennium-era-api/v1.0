package com.eracambodia.era.repository.api_agent_favorite;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_agent_favorite.response.Agent;
import com.eracambodia.era.model.api_agent_favorite.response.AgentFavorite;
import com.google.j2objc.annotations.ObjectiveCName;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentFavoriteRepo {
    @Select("SELECT building.id as bid,building.name,users.id as uid,building.uuid,building.status,building.type,building.country,building.street_number_or_name,building.district_code,building.village_code,building.commune_code,building.city_or_province " +
            "FROM building " +
            "INNER JOIN favorite ON building.id=favorite.owner_id " +
            "INNER JOIN users ON users.id=favorite.user_id " +
            "WHERE users.email=#{email} " +
            "ORDER BY building.user_create_date DESC LIMIT #{pagination.limit} OFFSET #{pagination.offset}")
    @Results({
            @Result(property = "id",column = "bid"),
            @Result(property = "totalCost",column = "bid",one = @One(select="getTotalCost")),
            @Result(property = "filePath",column = "bid",one=@One(select = "getFilePath")),
            @Result(property = "agent",column = "uid",one=@One(select = "getAgent")),
            @Result(property = "countryName",column = "country"),
            @Result(property = "street",column = "street_number_or_name"),
            @Result(property = "district",column = "district_code",one = @One(select = "getDestrict")),
            @Result(property = "commune",column = "commune_code",one = @One(select = "getCommune")),
            @Result(property = "village",column = "village_code",one = @One(select = "getVillage")),
            @Result(property = "cityOrProvince",column = "city_or_province",one = @One(select = "getCityOrProvince"))
    })
    List<AgentFavorite> findAgentFavorite(@Param("email")String email, @Param("pagination") Pagination pagination);
    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{bid}")
    double getTotalCost();
    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} AND type='image' " +
            "ORDER BY id DESC LIMIT 1")
    String getFilePath();
    @Select("SELECT * " +
            "FROM users " +
            "WHERE id=#{uid}")
    @Results({
            @Result(property = "name",column = "username"),
            @Result(property = "profilePhoto",column = "image")
    })
    Agent getAgent();

    @Select("SELECT COUNT(building.id) " +
            "FROM building " +
            "INNER JOIN favorite ON building.id=favorite.owner_id " +
            "INNER JOIN users ON users.id=favorite.user_id " +
            "WHERE users.email=#{email} ")
    Integer countAgentFavorite(String email);

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
            "WHERE id=#{district_code}")
    String getDestrict();

    @Select("SELECT latin_name " +
            "FROM address " +
            "WHERE id=#{city_or_province}")
    String getCityOrProvince();
}
