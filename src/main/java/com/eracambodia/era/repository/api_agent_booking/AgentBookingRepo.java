package com.eracambodia.era.repository.api_agent_booking;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_agent_booking.response.AgentBooking;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentBookingRepo {

    @Select("SELECT building.id,building.name,building.uuid,building.status,building.type " +
            "FROM building " +
            "INNER JOIN transaction ON transaction.owner_id = building.id " +
            "INNER JOIN users ON users.id=transaction.user_id " +
            "WHERE building.status = 'BOOKED' AND users.email=#{email} LIMIT #{pagination.limit} OFFSET #{pagination.offset}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "totalCost",column = "id",one = @One(select = "getTotalCost")),
            @Result(property = "filePath",column = "id",one = @One(select = "getFilePath"))
    })
    List<AgentBooking> findAgentsBooking(@Param("email") String email, @Param("pagination") Pagination pagination);
    @Select("SELECT total_project_cost " +
            "FROM building_basic_information " +
            "WHERE building_id=#{id}")
    double getTotalCost();
    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{id} " +
            "ORDER BY id DESC LIMIT 1")
    String getFilePath();

    @Select("SELECT COUNT(id) " +
            "FROM building " +
            "WHERE status='BOOKED'")
    Integer countAgentBooking();
}
