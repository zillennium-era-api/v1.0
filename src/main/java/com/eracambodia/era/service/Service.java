package com.eracambodia.era.service;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_agent_transaction.response.TransactionResponse;
import com.eracambodia.era.model.api_building.response.Buildings;
import com.eracambodia.era.model.api_building_available.response.BuildingAvailable;
import com.eracambodia.era.model.api_building_held.response.BuildingHeld;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.repository.api_agent_transaction.AgentTransactionRepo;

import java.awt.print.Pageable;
import java.util.List;

public interface Service {

    // api/login
    User findUserByEmailOfLogin(String email);
    String checkLogin(Login login);

    // api/building/uuid
    BuildingUUID findBuildingByUUID(String uuid,String email);
    void favoriteEnable(BuildingUUID buildingUUID,int userId,int buildingId);
    Integer getIdFromUser(String email);

    // api/building
    List<Buildings> findBuildings(Pagination pagination);
    int countBuildingsRecord();

    // api/building/status/update
    Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate);
    Integer findBuildingIdByIdOfBuildingStatusUpdate(int ownerId);

    // api/building/available
    List<BuildingAvailable> findBuildingAvailable(Pagination pagination);

    // api/building/held
    List<BuildingHeld> findBuildingHeld(Pagination pagination);

    // api/register
    void register(Register register);

    // api/user
    com.eracambodia.era.model.api_user.response.User findUserByUsernameOfUser(String username);

    // api/agent/profile/upload
    String findImageByUsernameOfUploadProfileAgent(String email);
    void updateImageProfileOfUploadProfileAgent(String image,String email);

    // api/agent/account/password
    void updateUserPassword(String password,String email);
    String getUserPasswordByEmail(String email);

    //api/agent/account/update
    void updateUserInformation(UpdateAgentAccount updateAgentAccount, String email);


    // api/agent/transaction
    List<TransactionResponse> findAgentsTransaction(String email, Pagination pagination);

}
