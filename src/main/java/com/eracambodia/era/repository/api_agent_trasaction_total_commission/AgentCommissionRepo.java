package com.eracambodia.era.repository.api_agent_trasaction_total_commission;

import com.eracambodia.era.model.api_agent_transaction_total_commission.response.AgentCommission;
import com.eracambodia.era.model.api_agent_transaction_total_commission.response.AgentGot;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentCommissionRepo {
    @Select(value= "{CALL transaction_calculator(#{email})}")
    @Results({
            @Result(property = "buildingCompleted",column = "total_price"),
            @Result(property = "fromYear",column = "from_year"),
            @Result(property = "toYear",column = "to_year")
    })
    @Options(statementType = StatementType.CALLABLE)
    AgentCommission commissionCalculator(String email);

    @Select(value= "{CALL agent_got(#{email})}")
    @Results({
            @Result(property = "buildingId",column = "building_id"),
            @Result(property = "isIncludeVat",column = "is_include_vat"),
            @Result(property = "projectPrice",column = "project_price"),
            @Result(property = "commission",column = "building_id",one = @One(select = "getCommission"))
    })
    @Options(statementType = StatementType.CALLABLE)
    List<AgentGot> getAgentCommissionAmount(String email);

    @Select("SELECT value " +
            "FROM value_reference " +
            "WHERE type ILIKE #{type} " +
            "ORDER BY id DESC LIMIT 1")
    Double getBusinessValue(String type);

    @Select("SELECT value " +
            "FROM building_commission " +
            "WHERE building_id=#{building_id}")
    Double getCommission();

}
