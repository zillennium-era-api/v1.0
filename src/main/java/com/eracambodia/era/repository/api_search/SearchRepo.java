package com.eracambodia.era.repository.api_search;

import com.eracambodia.era.model.api_building.response.Agent;
import com.eracambodia.era.model.api_building.response.Buildings;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface SearchRepo {
    @Select(value = "{CALL search(#{keyword},#{type},#{limit},#{offset})}")
    @Results({
            @Result(property = "id", column = "bid"),
            @Result(property = "name", column = "bname"),
            @Result(property = "uuid", column = "buuid"),
            @Result(property = "status", column = "bstatus"),
            @Result(property = "type", column = "btype"),
            @Result(property = "countryName", column = "bcountry"),
            @Result(property = "agent.id", column = "uid"),
            @Result(property = "agent.uuid", column = "uuuid"),
            @Result(property = "agent.name", column = "uname"),
            @Result(property = "district", column = "bdistrict", one = @One(select = "getDestrict")),
            @Result(property = "village", column = "bvillage", one = @One(select = "getVillage")),
            @Result(property = "commune", column = "bcommune", one = @One(select = "getCommune")),
            @Result(property = "agent.profilePhoto", column = "uimage"),
            @Result(property = "totalCost", column = "bid", one = @One(select = "getTotalCost")),
            @Result(property = "filePath", column = "bid", one = @One(select = "getFilePath"))
    })
    @Options(statementType = StatementType.CALLABLE)
    List<Buildings> search(@Param("keyword") String keyword, @Param("type") String type, @Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} AND type='image' " +
            "ORDER BY id DESC LIMIT 1")
    String getFilePath();

    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    Double getTotalCost();

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

    @Select(value = "{CALL countsearch(#{keyword})}")
    @Options(statementType = StatementType.CALLABLE)
    Integer countSearch(String keyword);

    @Select("SELECT distinct type " +
            "FROM building")
    List<String> projectType();
}
