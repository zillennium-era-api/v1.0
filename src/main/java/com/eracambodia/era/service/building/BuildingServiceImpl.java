package com.eracambodia.era.service.building;

import com.eracambodia.era.model.Building;
import com.eracambodia.era.model.BuildingStatusUpdate;
import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository repository;

    @Override
    public void updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate) {
        repository.updateBuildingStatus(buildingStatusUpdate);
    }

    @Override
    public Building findBuildingByUUID(String uuid) {
        return repository.findBuildingByUUID(uuid);
    }

    @Override
    public int countBuildingRecord() {
        return repository.countBuildingRecord();
    }

    @Override
    public List<Building> findBuildings(Pagination pagination) {
        return repository.findBuildings(pagination);
    }

    @Override
    public Integer findBuildingIdById(int ownerId) {
        return repository.findBuildingIdById(ownerId);
    }
}
