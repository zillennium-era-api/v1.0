package com.eracambodia.era.repository.api_noti_to_favoritor;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface NotiToFavoritorRepo {
    @Select("SELECT users.player_id " +
            "FROM users " +
            "INNER JOIN favorite ON favorite.user_id=users.id " +
            "WHERE favorite.owner_id=#{ownerId} AND favorite.user_id <> #{userId}")
    List<String> findPlayerId(@Param("userId")int userId,@Param("ownerId")int ownerId);
}
