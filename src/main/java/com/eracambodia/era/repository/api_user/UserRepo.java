package com.eracambodia.era.repository.api_user;

import com.eracambodia.era.model.api_user.response.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {
    @Select("SELECT * FROM users WHERE email=#{username}")
    @Results({
            @Result(property = "idCard", column = "id_card"),
            @Result(property = "phone", column = "phonenumber"),
            @Result(property = "name", column = "username"),
            @Result(property = "profilePhoto", column = "image"),
            @Result(property = "created", column = "create_at"),
            @Result(property = "updated", column = "update_at"),
            @Result(property = "role", column = "authority_id")
    })
    User findUserByUsernameOfUser(String username);
}
