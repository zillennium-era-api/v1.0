package com.eracambodia.era.repository.api_noti_to_favoritor;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface NotiToFavoritorRepo {

    @Select("SELECT DISTINCT ON (onesignal.user_id)onesignal.user_id, onesignal.player_id " +
            "FROM onesignal " +
            "INNER JOIN users ON onesignal.user_id=users.id " +
            "INNER JOIN favorite ON favorite.user_id=users.id " +
            "WHERE favorite.owner_id=#{ownerId} AND favorite.user_id <> #{userId} " +
            "ORDER BY onesignal.user_id,onesignal.created DESC")
    @ConstructorArgs(value = { @Arg(column = "player_id", javaType = String.class)})
    @ResultType(String.class)
    List<String> findPlayerId(@Param("userId")int userId,@Param("ownerId")int ownerId);

    @Select("SELECT image " +
            "FROM users " +
            "WHERE email=#{email}")
    String getImage(String email);

    @Select("SELECT id " +
            "FROM building " +
            "WHERE uuid=#{uuid}")
    Integer getBuildingID(String uuid);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email}")
    Integer getIDByEmail(String email);
}
