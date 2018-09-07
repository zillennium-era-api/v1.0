package com.eracambodia.era.repository.api_register;

import com.eracambodia.era.model.api_register.request.Register;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo {
    @Insert("INSERT INTO users(username,email,password,gender,dob,phonenumber,id_card,uuid,parent_id) " +
            "VALUES (#{register.name},#{register.email},#{register.password},#{register.gender},#{register.dateOfBirth},#{register.phone},#{register.idCard},#{register.uuid},#{parentId})")
    Integer register(@Param("register")Register register,@Param("parentId")Integer parentId);

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

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email} AND (authority_id=1 OR authority_id=3)")
    Integer getIdByEmail(String email);

    @Update("UPDATE users " +
            "SET enable=true " +
            "WHERE email=#{email} ")
    Integer enable(String email);
}
