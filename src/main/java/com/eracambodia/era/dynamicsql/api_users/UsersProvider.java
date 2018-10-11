package com.eracambodia.era.dynamicsql.api_users;

import com.eracambodia.era.model.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UsersProvider {
    public String getUsers(@Param("role")String role, @Param("name")String name, @Param("pagination")Pagination pagination){
        String s="%"+name+"%";
        String searchName=s.replace(" ","%");

        return new SQL(){{
            SELECT("users.uuid,users.image,users.username,users.email,users.phonenumber,users.id,users.create_at,users.enable");
            FROM("users");
            INNER_JOIN("authority on authority.id=users.authority_id");
            if(name!=null)
                WHERE("authority.name ILIKE #{role} AND users.username ILIKE '"+searchName+"'");
            else
                WHERE("authority.name ILIKE #{role} ");
            ORDER_BY("users.id DESC LIMIT #{pagination.limit} OFFSET #{pagination.offset}");
        }}.toString();
    }
}
