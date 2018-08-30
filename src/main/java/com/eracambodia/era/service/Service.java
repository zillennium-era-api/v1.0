package com.eracambodia.era.service;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_agent_booking.response.AgentBooking;
import com.eracambodia.era.model.api_agent_favorite.response.AgentFavorite;
import com.eracambodia.era.model.api_agent_favorite_add.request.AgentAddFavorite;
import com.eracambodia.era.model.api_agent_favorite_delete.request.AgentDeleteFavorite;
import com.eracambodia.era.model.api_agent_member_uuid.response.AgentMember;
import com.eracambodia.era.model.api_agent_members_direct_uuid.response.AgentMemberDirect;
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

    // api/building/{uuid}
    BuildingUUID findBuildingByUUID(String uuid,String email);
    void favoriteEnable(BuildingUUID buildingUUID,int userId,int buildingId);
    Integer getIdFromUser(String email);

    // api/building/status/{status}
    List<Buildings> findBuildings(Pagination pagination,String status);
    /*int countBuildingsRecord();*/

    // api/building/status/update
    Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate);
    Integer findBuildingIdByIdOfBuildingStatusUpdate(int ownerId);

    // api/building/available
    List<BuildingAvailable> findBuildingAvailable(Pagination pagination);

    // api/building/held
    List<BuildingHeld> findBuildingHeld(Pagination pagination);

    // api/register
    void register(Register register,String jwtToken);

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

    // api/agent/transaction/status/{status}
    List<TransactionResponse> findAgentsTransaction(String email,String status, Pagination pagination);

    // api/agent/booking
    List<AgentBooking> findAgentsBooking(String email,Pagination pagination);


    // api/agent/favorite
    List<AgentFavorite> findAgentFavorite(String email,Pagination pagination);

    // api/agent/favorite/add
    void addFavorite(AgentAddFavorite agentAddFavorite,String email);

    // api/agent/favorite/delete
    void deleteAgentFavorite(AgentDeleteFavorite agentDeleteFavorite,String email);

    // api/search
    List<Buildings> search(String keyword,Pagination pagination);

    // api/agent/members/uuid
    List<AgentMember> findAgentMember(String uuid);

    // api/agent/member/direct/uuid
    List<AgentMemberDirect> findAgentMemberDirect(String uuid,Pagination pagination);
}
