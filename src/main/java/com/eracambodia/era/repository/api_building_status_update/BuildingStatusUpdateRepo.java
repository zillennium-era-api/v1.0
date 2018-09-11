package com.eracambodia.era.repository.api_building_status_update;

import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingStatusUpdateRepo {

    @Select(value = "{CALL update_building_status(#{ownerId},#{tableName},#{status},#{userId},#{bookingPrice})}")
    @Options(statementType = StatementType.CALLABLE)
    Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate);

    @Select("SELECT id " +
            "FROM building " +
            "WHERE id=#{ownerId} ")
    Integer findBuildingIdByIdOfBuildingStatusUpdate(@Param("ownerId") int ownerId);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email=#{email}")
    int getUserEmail(String email);
}
