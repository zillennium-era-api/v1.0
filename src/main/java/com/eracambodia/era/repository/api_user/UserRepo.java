package com.eracambodia.era.repository.api_user;

import com.eracambodia.era.model.api_usesr.response.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {
    @Select("SELECT * FROM users WHERE username=#{username}")
    @Results({
            @Result(property = "role",column = "authority_id")
    })
    User findUserByUsernameOfUser(String username);
}
