package com.eracambodia.era.repository.api_user_id;

import com.eracambodia.era.model.api_userid.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdRepo {
    @Select("SELECT * " +
            "FROM users " +
            "WHERE id=#{id}")
    @Results({
            @Result(property = "idCard", column = "id_card"),
            @Result(property = "phone", column = "phonenumber"),
            @Result(property = "name", column = "username"),
            @Result(property = "profilePhoto", column = "image"),
            @Result(property = "role", column = "authority_id",one = @One(select = "getRole"))
    })
    User findUserById(int id);

    @Select("SELECT name " +
            "FROM authority " +
            "WHERE id=#{authority_id}")
    String getRole();
}
