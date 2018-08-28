package com.eracambodia.era.repository.api_login;

import com.eracambodia.era.model.GrantedAuthorityImpl;
import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_login.request.Login;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface LoginRepo {
    @Select("SELECT * " +
            "FROM users " +
            "WHERE email=#{email} AND enable=true")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "authority",column = "authority_id",many = @Many(select="findAuthorityByIdOfLogin"))
    })
    User findUserByEmailOfLogin(String email);
    @Select("SELECT * " +
            "FROM authority " +
            "WHERE id=#{authority_id}")
    GrantedAuthorityImpl findAuthorityByIdOfLogin();


    @Select("SELECT password " +
            "FROM users " +
            "WHERE email=#{email}")
    String checkLogin(Login login);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email} and enable=true")
    Integer checkEmail(String email);
}
