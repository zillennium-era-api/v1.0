package com.eracambodia.era.repository.api_agent_favorite_add;

import com.eracambodia.era.model.api_agent_favorite_add.request.AgentAddFavorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Service;

@Service
public interface AgentAddFavoriteRepo {
    @Insert("INSERT INTO favorite (owner_id,table_name,user_id) " +
            "VALUES (#{ownerId},#{tableName},#{userId})")
    Integer addFavorite(AgentAddFavorite agentAddFavorite);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email}")
    int getUserIdByEmail(String email);

    @Select("SELECT id " +
            "FROM building " +
            "WHERE id=#{buildingId}")
    int buildingAvailable(int buildingId);
}
