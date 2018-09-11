package com.eracambodia.era.repository.api_agent_account_update;

import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateAgentAccountRepo {
    @Update("UPDATE users " +
            "SET username=#{updateAgentAccount.name},phonenumber=#{updateAgentAccount.phone} " +
            "WHERE email=#{email}")
    void updateUserInformation(@Param("updateAgentAccount") UpdateAgentAccount updateAgentAccount, @Param("email") String email);

    @Select("SELECT password " +
            "FROM users " +
            "WHERE email=#{email}")
    String getUserPassword(String email);

    @Update("UPDATE users " +
            "SET username=#{updateAgentAccount.name} " +
            "WHERE email=#{email}")
    void updateUsername(@Param("updateAgentAccount") UpdateAgentAccount updateAgentAccount, @Param("email") String email);

}
