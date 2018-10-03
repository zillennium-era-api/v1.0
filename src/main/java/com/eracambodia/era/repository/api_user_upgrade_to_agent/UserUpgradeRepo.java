package com.eracambodia.era.repository.api_user_upgrade_to_agent;

import com.eracambodia.era.dynamicsql.api_user_upgrade_to_agent.UserUpgradeProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUpgradeRepo {
    @UpdateProvider(type = UserUpgradeProvider.class,method = "upgradeToAgent")
    Integer upgradeToAgent(@Param("userId") int userId,@Param("leaderId") Integer leaderId);
}
