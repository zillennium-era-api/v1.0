package com.eracambodia.era.repository.api_building_status_update;

import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_status_update.request.TransactionOwner;
import com.eracambodia.era.model.api_building_status_update.response.Agent;
import com.eracambodia.era.model.api_building_status_update.response.BuildingUpdate;
import com.eracambodia.era.model.api_noti_favoritor.Transaction;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingStatusUpdateRepo {

    @Select(value = "{CALL update_building_status(#{ownerId},#{tableName},#{status},#{userId},#{bookingPrice})}")
    @Results({
            @Result(property = "id",column = "rbid"),
            @Result(property = "status",column = "rstatus"),
            @Result(property = "agent.name",column = "rname"),
            @Result(property = "agent.id",column = "rid"),
            @Result(property = "agent.uuid",column = "ruuid"),
            @Result(property = "agent.profilePhoto",column = "rimage")
    })
    @Options(statementType = StatementType.CALLABLE)
    BuildingUpdate updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate);

    @Select("SELECT id " +
            "FROM building " +
            "WHERE id=#{ownerId} ")
    Integer findBuildingIdByIdOfBuildingStatusUpdate(@Param("ownerId") int ownerId);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email}")
    int getUserEmail(String email);

    @Select("SELECT id " +
            "FROM transaction " +
            "WHERE owner_id=#{buildingId} ")
    Integer changeStatusAuthority();

    @Select("SELECT id,status " +
            "FROM building " +
            "WHERE id=#{buildingId}")
    @Results({
            @Result(property = "agent",column = "id",one = @One(select="getAgent"))
    })
    BuildingUpdate getBuildingUpdate(int buildingId);

    @Select("SELECT users.username,users.image,users.id,users.uuid " +
            "FROM users " +
            "INNER JOIN transaction ON transaction.user_id=users.id " +
            "INNER JOIN building ON building.id=transaction.owner_id " +
            "WHERE transaction.owner_id=#{id} " +
            "ORDER BY transaction.id DESC LIMIT 1")
    @Results({
            @Result(property = "name", column = "username"),
            @Result(property = "profilePhoto", column = "image")
    })
    Agent getAgent();

    @Select("SELECT status,owner_id,user_id " +
            "FROM transaction " +
            "WHERE owner_id=#{buildingId} " +
            "ORDER BY id DESC LIMIT 1")
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "buildingId",column = "owner_id")
    })
    TransactionOwner checkTransactionOwner(Integer buildingId);
}
