package com.eracambodia.era.service;

import com.eracambodia.era.exception.CustomException;
import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_agent_booking.response.AgentBooking;
import com.eracambodia.era.model.api_agent_favorite.response.AgentFavorite;
import com.eracambodia.era.model.api_agent_favorite_add.request.AgentAddFavorite;
import com.eracambodia.era.model.api_agent_favorite_delete.request.AgentDeleteFavorite;
import com.eracambodia.era.model.api_agent_transaction.response.TransactionResponse;
import com.eracambodia.era.model.api_building.response.Buildings;
import com.eracambodia.era.model.api_building_available.response.BuildingAvailable;
import com.eracambodia.era.model.api_building_held.response.BuildingHeld;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_register.RegisterUniqueFields;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.repository.api_agent_account_password.AgentChangePasswordRepo;
import com.eracambodia.era.repository.api_agent_account_update.UpdateAgentAccountRepo;
import com.eracambodia.era.repository.api_agent_booking.response.AgentBookingRepo;
import com.eracambodia.era.repository.api_agent_favorite.AgentFavoriteRepo;
import com.eracambodia.era.repository.api_agent_favorite_add.AgentAddFavoriteRepo;
import com.eracambodia.era.repository.api_agent_favorite_delete.AgentDeleteFavoriteRepo;
import com.eracambodia.era.repository.api_agent_profile_upload.UploadProfileAgentRepo;
import com.eracambodia.era.repository.api_agent_transaction.AgentTransactionRepo;
import com.eracambodia.era.repository.api_building.BuildingsRepo;
import com.eracambodia.era.repository.api_building_available.BuildingAvailableRepo;
import com.eracambodia.era.repository.api_building_held.BuildingHeldRepo;
import com.eracambodia.era.repository.api_building_status_update.BuildingStatusUpdateRepo;
import com.eracambodia.era.repository.api_building_uuid.BuildingUUIDRepo;
import com.eracambodia.era.repository.api_login.LoginRepo;
import com.eracambodia.era.repository.api_register.RegisterRepo;
import com.eracambodia.era.repository.api_user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Autowired
    private PasswordEncoder passwordEncoder;
    //api/login
    @Autowired
    private LoginRepo loginRepo;

    @Override
    public User findUserByEmailOfLogin(String email) {
        return loginRepo.findUserByEmailOfLogin(email);
    }

    @Override
    public String checkLogin(Login login) {
        String password = loginRepo.checkLogin(login);
        if (password == null) {
            throw new CustomException(401, "email or password not available.");
        } else if (!BCrypt.checkpw(login.getPassword(), password)) {
            throw new CustomException(401, "email or password not correct.");
        }
        return password;
    }

    // api/building/uuid
    @Autowired
    private BuildingUUIDRepo buildingUUIDRepo;

    @Override
    public BuildingUUID findBuildingByUUID(String uuid, String email) {
        BuildingUUID buildingUUID = buildingUUIDRepo.findBuildingByUUID(uuid);
        if (buildingUUID == null) {
            throw new CustomException(404, "Can not found this Building");
        }
        favoriteEnable(buildingUUID, getIdFromUser(email), buildingUUID.getId());
        return buildingUUID;
    }

    @Override
    public void favoriteEnable(BuildingUUID buildingUUID, int userId, int buildingId) {
        Integer count = buildingUUIDRepo.favoriteEnable(userId, buildingId);
        if (count > 0) {
            buildingUUID.setIfFavorite(true);
        } else {
            buildingUUID.setIfFavorite(false);
        }
    }

    @Override
    public Integer getIdFromUser(String email) {
        return buildingUUIDRepo.getIdFromUser(email);
    }

    // api/building
    @Autowired
    private BuildingsRepo buildingsRepo;

    @Override
    public List<Buildings> findBuildings(Pagination pagination) {
        List<Buildings> buildings = buildingsRepo.findBuildings(pagination);
        if (buildings.size() < 1) {
            throw new CustomException(404, "Page not Found.");
        }
        pagination.setTotalItem(buildingsRepo.countBuildingsRecord());
        return buildings;
    }

    @Override
    public int countBuildingsRecord() {
        int countAllBuildings = buildingsRepo.countBuildingsRecord();
        if (countAllBuildings < 1) {
            throw new CustomException(404, "No record of building ");
        }
        return countAllBuildings;
    }

    // api building/status/update
    @Autowired
    private BuildingStatusUpdateRepo buildingStatusUpdateRepo;

    @Override
    public Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate) {
        if (buildingStatusUpdateRepo.findBuildingIdByIdOfBuildingStatusUpdate(buildingStatusUpdate.getOwnerId()) == null) {
            throw new CustomException(404, "Building not found");
        }
        return buildingStatusUpdateRepo.updateBuildingStatus(buildingStatusUpdate);
    }

    @Override
    public Integer findBuildingIdByIdOfBuildingStatusUpdate(int ownerId) {
        return buildingStatusUpdateRepo.findBuildingIdByIdOfBuildingStatusUpdate(ownerId);
    }


    // api/building/available
    @Autowired
    private BuildingAvailableRepo buildingAvailableRepo;

    @Override
    public List<BuildingAvailable> findBuildingAvailable(Pagination pagination) {
        List<BuildingAvailable> buildingAvailables = buildingAvailableRepo.findBuildingAvailable(pagination);
        if (buildingAvailables.size() < 1) {
            throw new CustomException(404, "no record");
        }
        pagination.setTotalItem(buildingAvailableRepo.countBuildingAvailable());
        return buildingAvailables;
    }

    // api/building/held
    @Autowired
    private BuildingHeldRepo buildingHeldRepo;

    @Override
    public List<BuildingHeld> findBuildingHeld(Pagination pagination) {
        List<BuildingHeld> buildingHelds = buildingHeldRepo.findBuildingHeld(pagination);
        if (buildingHelds.size() < 1) {
            throw new CustomException(404, "Not found");
        }
        pagination.setTotalItem(buildingHeldRepo.countBuildingHeld());
        return buildingHelds;
    }

    // api/register
    @Autowired
    private RegisterRepo registerRepo;

    @Override
    public void register(Register register) {
        String message = "";
        RegisterUniqueFields registerUniqueFields = new RegisterUniqueFields();
        if (registerRepo.getIdCard(register.getIdCard()) != null) {
            registerUniqueFields.setIdCardExist(true);
            message += "id-card already exist.";
        }
        if (registerRepo.getPhone(register.getPhone()) != null) {
            registerUniqueFields.setPhoneExist(true);
            message += "phone already exist.";
        }
        if (registerRepo.getEmail(register.getEmail()) != null) {
            registerUniqueFields.setEmailExist(true);
            message += "email already exist.";
        }
        if (message.length() > 1)
            throw new CustomException(409, message, registerUniqueFields);
        registerRepo.register(register);
    }


    // api/user
    @Autowired
    private UserRepo userRepo;

    @Override
    public com.eracambodia.era.model.api_user.response.User findUserByUsernameOfUser(String username) {
        com.eracambodia.era.model.api_user.response.User user = userRepo.findUserByUsernameOfUser(username);
        if (user == null) {
            throw new CustomException(404, "User not found.");
        }
        return user;
    }

    //api/agent/profile/upload
    @Autowired
    private UploadProfileAgentRepo uploadProfileAgentRepo;

    @Override
    public String findImageByUsernameOfUploadProfileAgent(String username) {
        return uploadProfileAgentRepo.findImageByUsernameOfUploadProfileAgent(username);
    }

    @Override
    public void updateImageProfileOfUploadProfileAgent(String image, String email) {
        uploadProfileAgentRepo.updateImageProfileOfUploadProfileAgent(image, email);
    }

    // api/agent/account/password
    @Autowired
    private AgentChangePasswordRepo agentChangePasswordRepo;

    @Override
    public void updateUserPassword(String password, String email) {
        agentChangePasswordRepo.updateUserPassword(password, email);
    }

    @Override
    public String getUserPasswordByEmail(String email) {
        return agentChangePasswordRepo.getUserPasswordByEmail(email);
    }

    // api/agent/account/update
    @Autowired
    private UpdateAgentAccountRepo updateAgentAccountRepo;

    @Override
    public void updateUserInformation(UpdateAgentAccount updateAgentAccount, String email) {
        String phone = registerRepo.getPhone(updateAgentAccount.getPhone());
        if (phone != null) {
            throw new CustomException(409, "Phose already exist");
        }
        String password = updateAgentAccountRepo.getUserPassword(email);
        if (password == null) {
            throw new CustomException(404, "Account not found");
        }
        boolean checkPassword = passwordEncoder.matches(updateAgentAccount.getConfirmPassword(), password);
        if (!checkPassword) {
            throw new CustomException(401, "Your password not match.");
        }
        updateAgentAccountRepo.updateUserInformation(updateAgentAccount, email);
    }

    // api/agent/transaction
    @Autowired
    private AgentTransactionRepo agentTransactionRepo;

    @Override
    public List<TransactionResponse> findAgentsTransaction(String email, Pagination pagination) {
        List<TransactionResponse> transactionResponses = agentTransactionRepo.findAgentsTransaction(email, pagination);
        if (transactionResponses.size() < 1)
            throw new CustomException(404, "No record");
        pagination.setTotalItem(agentTransactionRepo.countTransaction(email));
        return transactionResponses;
    }

    // api/agent/booking
    @Autowired
    private AgentBookingRepo agentBookingRepo;

    @Override
    public List<AgentBooking> findAgentsBooking(String email, Pagination pagination) {
        List<AgentBooking> agentBookings = agentBookingRepo.findAgentsBooking(email, pagination);
        if (agentBookings.size() < 1) {
            throw new CustomException(404, "No record");
        }
        pagination.setTotalItem(agentBookingRepo.countAgentBooking());
        return agentBookings;
    }

    // api/agent/favorite
    @Autowired
    private AgentFavoriteRepo agentFavoriteRepo;

    @Override
    public List<AgentFavorite> findAgentFavorite(String email, Pagination pagination) {
        List<AgentFavorite> agentFavorites = agentFavoriteRepo.findAgentFavorite(email, pagination);
        if (agentFavorites.size() < 1) {
            throw new CustomException(404, "No record.");
        }
        pagination.setTotalItem(agentFavoriteRepo.countAgentFavorite(email));
        return agentFavorites;
    }

    // api/agent/favorite/add
    @Autowired
    private AgentAddFavoriteRepo agentAddFavoriteRepo;

    @Override
    public void addFavorite(AgentAddFavorite agentAddFavorite, String email) {
        agentAddFavorite.setUserId(agentAddFavoriteRepo.getUserIdByEmail(email));
        if(agentAddFavoriteRepo.addFavorite(agentAddFavorite)<1){
            throw new CustomException(403,"No record has deleted");
        }
    }

    // api/agent/favorite/delete
    @Autowired
    private AgentDeleteFavoriteRepo agentDeleteFavoriteRepo;

    @Override
    public void deleteAgentFavorite(AgentDeleteFavorite agentDeleteFavorite,String email) {
        int id=agentDeleteFavoriteRepo.findUserByEmail(email);
        agentDeleteFavorite.setUserId(id);
        if(agentDeleteFavoriteRepo.deleteAgentFavorite(agentDeleteFavorite)<1){
            throw new CustomException(403,"No record have deleted");
        }
    }
}

