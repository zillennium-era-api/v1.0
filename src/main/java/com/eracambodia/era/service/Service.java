package com.eracambodia.era.service;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_agent_favorite.response.AgentFavorite;
import com.eracambodia.era.model.api_agent_favorite_add.request.AgentAddFavorite;
import com.eracambodia.era.model.api_agent_favorite_delete.request.AgentDeleteFavorite;
import com.eracambodia.era.model.api_agent_member_uuid.response.AgentMember;
import com.eracambodia.era.model.api_agent_members_direct_uuid.response.AgentMemberDirect;
import com.eracambodia.era.model.api_agent_building_status_status.response.Agent;
import com.eracambodia.era.model.api_agent_transaction_useruuid_status.response.TransactionResponse;
import com.eracambodia.era.model.api_agent_transaction_total_commission.response.AgentCommission;
import com.eracambodia.era.model.api_building_status_status.response.Buildings;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_status_update.response.BuildingUpdate;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.model.api_users.Users;

import java.util.List;

public interface Service {

    // api/login
    User findUserByEmailOfLogin(String email);
    String getUserRole(String email);

    String checkLogin(Login login,String playerId);

    // api/building/{uuid}
    BuildingUUID findBuildingByUUID(String uuid, String email);

    void favoriteEnable(BuildingUUID buildingUUID, int userId, int buildingId);

    Integer getIdFromUser(String email);

    // api/building/status/{status}
    List<Buildings> findBuildings(Pagination pagination, String status);

    // api/building/status/update
    BuildingUpdate updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate, String email);

    Integer findBuildingIdByIdOfBuildingStatusUpdate(int ownerId);

    // api/register
    void register(Register register, String jwtToken,String playerId);

    // api/user
    com.eracambodia.era.model.api_user.response.User findUserByUsernameOfUser(String username);

    // api/user?id
    com.eracambodia.era.model.api_userid.User findUserById(int id);

    // api/agent/profile/upload
    String findImageByUsernameOfUploadProfileAgent(String email);

    void updateImageProfileOfUploadProfileAgent(String image, String email);

    // api/agent/account/password
    void updateUserPassword(String password, String email);

    String getUserPasswordByEmail(String email);

    //api/agent/account/update
    void updateUserInformation(UpdateAgentAccount updateAgentAccount, String email);

    // api/agent/transaction/{useruuid}/{status}
    List<TransactionResponse> findAgentsTransaction(String userUUID, String status, Pagination pagination);

    // api/agent/favorite
    List<AgentFavorite> findAgentFavorite(String email, Pagination pagination);

    // api/agent/favorite/add
    void addFavorite(AgentAddFavorite agentAddFavorite, String email);

    // api/agent/favorite/delete
    void deleteAgentFavorite(AgentDeleteFavorite agentDeleteFavorite, String email);

    // api/search
    List<Buildings> search(String keyword, String type, Pagination pagination);

    List<String> projectType();

    // api/agent/members/uuid
    List<AgentMember> findAgentMember(String uuid);

    // api/agent/member/direct/uuid
    List<AgentMemberDirect> findAgentMemberDirect(String uuid, Pagination pagination);

    // api/noti/favorite
    List<String> findPlayerId(String email, int ownerId, Integer agentId);

    // api/agent/status/{status}
    List<Agent> findAgentProcess(String status, String email, Pagination pagination);

    // api/agent/transaction/total_commission
    AgentCommission commissionCalculator(String uuid);

    // api/users
    List<Users> getUsers(String role,String name,Pagination pagination);

    //api/user/upgrade_to_agent
    void upgradeToAgent(int userId,Integer leaderId);
}
