package com.eracambodia.era.repository.api_agent_member_uuid;

import com.eracambodia.era.model.api_agent_member_uuid.response.AgentMember;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentMemberUUIDRepo {
    @Select(value = "{CALL agent_member(#{userId})}")
    @Results({
            @Result(property = "uuid", column = "user_uuid"),
            @Result(property = "name", column = "user_name"),
            @Result(property = "phone", column = "user_phone"),
            @Result(property = "email", column = "user_email"),
            @Result(property = "level", column = "user_level"),
            @Result(property = "image",column = "user_image"),
            @Result(property = "memberCount", column = "user_id", one = @One(select = "countMember")),
            @Result(property = "parent", column = "user_parent_id", one = @One(select = "getParent"))
    })
    @Options(statementType = StatementType.CALLABLE)
    List<AgentMember> findAgentMember(int userId);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE uuid=#{uuid}")
    Integer getIdFromAgent(String uuid);

    @Select("SELECT username " +
            "FROM users " +
            "WHERE id=#{user_parent_id}")
    String getParent();

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE parent_id=#{user_id}")
    Integer countMember();
}
