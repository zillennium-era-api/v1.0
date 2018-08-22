package com.eracambodia.era.repository.api_register;

import com.eracambodia.era.model.api_register.request.Register;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo {
    @Insert("INSERT INTO users(username,email,password,gender,dob,phonenumber,id_card,uuid) " +
            "VALUES (#{name},#{email},#{password},#{gender},#{dateOfBirth},#{phone},#{idCard},#{uuid})")
    void register(Register register);


    @Select("SELECT email " +
            "FROM users " +
            "WHERE email=#{email}")
    String getEmail(String email);
    @Select("SELECT phonenumber " +
            "FROM users " +
            "WHERE phonenumber = #{phone}")
    String getPhone(String phone);
    @Select("SELECT id_card " +
            "FROM users " +
            "WHERE id_card=#{idCard}")
    String getIdCard(String idCard);
}
