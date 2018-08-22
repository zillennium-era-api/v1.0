package com.eracambodia.era.repository.api_agent_account_password;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentChangePasswordRepo {
    @Update("UPDATE users " +
            "SET password=#{password} " +
            "WHERE email=#{email}")
    void updateUserPassword(@Param("password")String password,@Param("email")String email);

    @Select("SELECT password " +
            "FROM users " +
            "WHERE email=#{email}")
    String getUserPasswordByEmail(String email);
}
