package com.eracambodia.era.service.building;

import com.eracambodia.era.model.Building;
import com.eracambodia.era.model.BuildingStatusUpdate;
import com.eracambodia.era.model.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuildingService {
    List<Building> findBuildings(Pagination pagination);
    Building findBuildingByUUID(String uuid);
    Integer findBuildingIdById(int ownerId);
    int countBuildingRecord();
    void updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate);
}
