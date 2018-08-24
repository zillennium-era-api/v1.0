package com.eracambodia.era.repository.api_agent_favorite_delete;

import com.eracambodia.era.model.api_agent_favorite_delete.request.AgentDeleteFavorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface AgentDeleteFavoriteRepo {
    @Delete("DELETE FROM favorite " +
            "WHERE user_id=#{userId} AND owner_id=#{ownerId} AND table_name=#{tableName}")
    int deleteAgentFavorite(AgentDeleteFavorite agentDeleteFavorite);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email}")
    Integer findUserByEmail(String email);
}
