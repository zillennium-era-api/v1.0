package com.eracambodia.era.repository.api_register;

import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_register.request.Register;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo {
    @Insert("INSERT INTO users(username,email,password,gender,dob,phonenumber,id_card,uuid) " +
            "VALUES (#{name},#{email},#{password},#{gender},#{dateOfBirth},#{phone},#{idCard},#{uuid})")
    void register(Register register);

    @Select("SELECT * " +
            "FROM users " +
            "WHERE email=#{email}")
    String getEmail(String email);
}
