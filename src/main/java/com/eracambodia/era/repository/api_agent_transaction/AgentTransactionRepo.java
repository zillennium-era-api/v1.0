package com.eracambodia.era.repository.api_agent_transaction;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_agent_transaction.response.TransactionResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentTransactionRepo {

    @Select("SELECT transaction.owner_id,status,create_date " +
            "FROM transaction " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email} LIMIT #{pagination.limit} OFFSET #{pagination.offset}")
    @Results({
            @Result(property = "buildingName",column = "owner_id",one=@One(select = "findBuildingName")),
            @Result(property = "totalCost",column = "owner_id",one=@One(select="findTotalCost")),
            @Result(property = "address",column = "owner_id",one=@One(select = "findCity")),
            @Result(property = "date",column = "create_date")
    })
    List<TransactionResponse> findAgentsTransaction(@Param("email") String email, @Param("pagination")Pagination pagination);
    @Select("SELECT " +
            "name " +
            "FROM building " +
            "WHERE id=#{owner_id}")
    String findBuildingName();

    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{owner_id}")
    double findTotalCost();

    @Select("SELECT city_or_province " +
            "FROM building " +
            "WHERE id=#{owner_iid}")
    String findCity();

    @Select("SELECT COUNT(transaction.id) " +
            "FROM transaction " +
            "INNER JOIN users on users.id=transaction.user_id " +
            "WHERE users.email=#{email}")
    Integer countTransaction(String email);

}
