package com.eracambodia.era.repository.api_agent_profile_upload;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadProfileAgentRepo {
    @Select("SELECT image " +
            "FROM users " +
            "WHERE email=#{email}")
    String findImageByUsernameOfUploadProfileAgent(String email);

    @Update("UPDATE users " +
            "SET image=#{image} " +
            "WHERE email=#{email}")
    void updateImageProfileOfUploadProfileAgent(@Param("image") String image    , @Param("email") String email);
}
