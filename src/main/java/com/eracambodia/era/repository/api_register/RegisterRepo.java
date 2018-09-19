package com.eracambodia.era.repository.api_register;

import com.eracambodia.era.model.api_register.request.Register;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo {
    @Select(value = "{CALL register(#{register.name},#{register.email},#{register.password},#{register.gender},#{register.dateOfBirth},#{register.phone},#{register.idCard},#{register.uuid},#{parentId})}")
    @Options(statementType = StatementType.CALLABLE)
    Integer register(@Param("register") Register register, @Param("parentId") Integer parentId);

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
            "SET enable=true,authority_id=3 " +
            "WHERE email=#{email} ")
    Integer enable(String email);

    @Insert("INSERT INTO onesignal(user_id,player_id) " +
            "VALUES (#{userId},#{playerId})" )
    int savePlayerId(@Param("userId")int userId,@Param("playerId")String playerId);
}
