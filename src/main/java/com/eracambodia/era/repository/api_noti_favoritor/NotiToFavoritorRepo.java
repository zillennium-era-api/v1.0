package com.eracambodia.era.repository.api_noti_favoritor;

import com.eracambodia.era.model.api_noti_favoritor.Transaction;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface NotiToFavoritorRepo {

   /* @Select("SELECT DISTINCT ON (onesignal.user_id)onesignal.user_id, onesignal.player_id " +
            "FROM onesignal " +
            "INNER JOIN users ON onesignal.user_id=users.id " +
            "INNER JOIN favorite ON favorite.user_id=users.id " +
            "WHERE favorite.owner_id=#{ownerId} AND favorite.user_id <> #{userId} " +
            "ORDER BY onesignal.user_id,onesignal.created DESC")
    @ConstructorArgs(value = {@Arg(column = "player_id", javaType = String.class)})
    @ResultType(String.class)
    List<String> findPlayerId(@Param("userId") int userId, @Param("ownerId") int ownerId);*/

    @Select("SELECT player_id " +
            "FROM onesignal " +
            "INNER JOIN favorite ON onesignal.user_id=favorite.user_id " +
            "WHERE favorite.owner_id = #{buildingId} " +
            "AND onesignal.user_id <> #{userId}")
    List<String> findPlayerId(@Param("userId")Integer userId,@Param("buildingId")Integer buildingId);

    @Select("SELECT image " +
            "FROM users " +
            "WHERE email=#{email}")
    String getImage(String email);

    @Select("SELECT id " +
            "FROM building " +
            "WHERE uuid=#{uuid}")
    Integer getBuildingID(String uuid);

    @Select("SELECT user_id,status " +
            "FROM transaction " +
            "WHERE owner_id=#{ownerId} " +
            "ORDER BY id DESC LIMIT 1")
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "status",column = "status")
    })
    Transaction getUserIdFromTransaction(int ownerId);

    @Select("SELECT name " +
            "FROM building where id=#{ownerId}")
    String buildingName(int ownerId);
    @Select("SELECT username " +
            "FROM users " +
            "WHERE email=#{email}")
    String agentName(String email);
}
