package com.eracambodia.era.repository.api_register;

import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_register.request.Register;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepo {
    @Insert("INSERT INTO users(username,email,password,gender,dob,phonenumber,id_card,uuid) " +
            "VALUES (#{name},#{email},#{password},#{gender},#{dateOfBirth},#{phone},#{idCard},#{uuid})")
    void register(Register register);

    @Select("SELECT email,id_card,phonenumber " +
            "FROM users " +
            "WHERE email=#{email} OR phonenumber=#{phone} OR id_card=#{idCard}")
    @Results({
            @Result(property = "phone",column = "phonenumber"),
            @Result(property = "idCard",column = "id_card")
    })
    List<Register> getEmail(@Param("email") String email, @Param("idCard")String idCard, @Param("phone")String phone);
}
