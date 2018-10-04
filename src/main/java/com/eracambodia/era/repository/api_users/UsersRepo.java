package com.eracambodia.era.repository.api_users;

import com.eracambodia.era.dynamicsql.api_user_upgrade_to_agent.UserUpgradeProvider;
import com.eracambodia.era.dynamicsql.api_users.UsersProvider;
import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.api_users.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo {
    @SelectProvider(type = UsersProvider.class,method = "getUsers")
    @Results({
            @Result(property = "name", column = "username"),
            @Result(property = "registerDate", column = "create_at"),
            @Result(property = "phone", column = "phonenumber")
    })
    List<Users> getUsers(@Param("role") String role,@Param("name")String name, @Param("pagination")Pagination pagination);

    @Select({"SELECT count(users.id) " +
            "FROM users " +
            "INNER JOIN authority on authority.id=users.authority_id " +
            "WHERE authority.name ILIKE #{role} "})
    Integer countUsers(String role);
}
