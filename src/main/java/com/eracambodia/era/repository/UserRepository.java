package com.eracambodia.era.repository;

import com.eracambodia.era.model.user.Authority;
import com.eracambodia.era.model.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository {


    @Select("SELECT * FROM users where email=#{email}")
    User userAvailable(User user);

    @Select("select id from users where id=#{id}")
    Integer findUserIdById(int id);

    @Select("SELECT * FROM users WHERE username=#{username}")
    @Results({
            @Result(property = "authority",column = "authority_id",many = @Many(select="findAuthorityById"))
    })
    User findUserByUsername(String username);
    @Select("SELECT * FROM authority WHERE id=#{id}")
    Authority findAuthorityById(int id);


    @Select("SELECT * FROM users WHERE email=#{email}")
    @Results({
            @Result(property = "authority",column = "authority_id",many = @Many(select="findAuthById"))
    })
    User findUserByEmail(String email);
    @Select("SELECT * FROM authority WHERE id=#{id}")
    Authority findAuthById(int id);

    @Insert("INSERT INTO users(username,email,password,gender,dob,phonenumber,id_card,uuid) values (#{username},#{email},#{password},#{gender},#{dob},#{phonenumber},#{idcard},#{uuid})")
    void register(User user);

    @Update("UPDATE users set image=#{image} where email=#{email}")
    void updateImageProfile(@Param("image") String image,@Param("email") String email);

    @Update("UPDATE users set password=#{password} where email=#{email}")
    void updateUserPassword(User user);

    @Update("UPDATE users set username=#{username},phonenumber=#{phonenumber} where email=#{email}")
    void updateUserInformation(User user);

}
