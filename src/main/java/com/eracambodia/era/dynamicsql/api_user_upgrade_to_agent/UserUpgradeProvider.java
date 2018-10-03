package com.eracambodia.era.dynamicsql.api_user_upgrade_to_agent;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserUpgradeProvider {
    public String upgradeToAgent(@Param("userId")int userId,@Param("leaderId") Integer leaderId){
        return new SQL(){{
            UPDATE("users");
            SET("authority_id=3,enable=true,parent_id=#{leaderId},update_at=now()");
            WHERE("id=#{userId}");
        }}.toString();
    }
}
