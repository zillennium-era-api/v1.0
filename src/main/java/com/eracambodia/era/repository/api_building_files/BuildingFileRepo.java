package com.eracambodia.era.repository.api_building_files;

import com.eracambodia.era.model.api_building_files.File;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingFileRepo {
    @Select("SELECT paths " +
            "FROM file " +
            "WHERE owner_id=#{buildingId} AND type='pdf' ")
    @Results({
            @Result(property ="path",column = "paths")
    })
    List<File> getBuildingFiles(int buildingId);

    @Select("SELECT id " +
            "FROM building " +
            "WHERE uuid = #{uuid}")
    int buildingIdToUUID(String uuid);
}
