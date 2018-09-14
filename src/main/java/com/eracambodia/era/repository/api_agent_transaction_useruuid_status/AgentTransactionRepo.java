package com.eracambodia.era.repository.api_agent_transaction_useruuid_status;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_agent_transaction_useruuid_status.response.TransactionResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentTransactionRepo {

    @Select("SELECT transaction.owner_id,transaction.status,transaction.create_date,building.village_code,building.commune_code,building.district_code,building.street_number_or_name,building.country,building.city_or_province " +
            "FROM transaction " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "INNER JOIN building on building.id=transaction.owner_id " +
            "WHERE users.uuid=#{userUUID} AND transaction.status ilike #{status} " +
            "ORDER BY transaction.id DESC " +
            "LIMIT #{pagination.limit} OFFSET #{pagination.offset}")
    @Results({
            @Result(property = "name", column = "owner_id", one = @One(select = "findBuildingName")),
            @Result(property = "totalCost", column = "owner_id", one = @One(select = "findTotalCost")),
            @Result(property = "date", column = "create_date"),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince"))
    })
    List<TransactionResponse> findAgentsTransaction(@Param("userUUID") String userUUID, @Param("status") String status, @Param("pagination") Pagination pagination);

    @Select("SELECT " +
            "name " +
            "FROM building " +
            "WHERE id=#{owner_id}")
    String findBuildingName();

    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{owner_id}")
    double findTotalCost();

    @Select("SELECT COUNT(transaction.id) " +
            "FROM transaction " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.uuid=#{userUUID} AND status ILIKE #{status}")
    Integer countTransaction(@Param("userUUID") String userUUID, @Param("status") String status);

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


    @Select("SELECT transaction.owner_id,transaction.status,transaction.create_date,building.village_code,building.commune_code,building.district_code,building.street_number_or_name,building.country,building.city_or_province " +
            "FROM transaction " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "INNER JOIN building on building.id=transaction.owner_id " +
            "WHERE users.uuid=#{userUUID} " +
            "ORDER BY transaction.id DESC " +
            "LIMIT #{pagination.limit} OFFSET #{pagination.offset}")
    @Results({
            @Result(property = "name", column = "owner_id", one = @One(select = "findBuildingName")),
            @Result(property = "totalCost", column = "owner_id", one = @One(select = "findTotalCost")),
            @Result(property = "date", column = "create_date"),
            @Result(property = "countryName", column = "country"),
            @Result(property = "street", column = "street_number_or_name"),
            @Result(property = "district", column = "district_code", one = @One(select = "getDestrict")),
            @Result(property = "commune", column = "commune_code", one = @One(select = "getCommune")),
            @Result(property = "village", column = "village_code", one = @One(select = "getVillage")),
            @Result(property = "cityOrProvince", column = "city_or_province", one = @One(select = "getCityOrProvince"))
    })
    List<TransactionResponse> findAgentsAllTransaction(@Param("userUUID") String userUUID, @Param("pagination") Pagination pagination);

    @Select("SELECT COUNT(transaction.id) " +
            "FROM transaction " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.uuid=#{userUUID}")
    Integer countAllTransaction(@Param("userUUID") String userUUID);


}
