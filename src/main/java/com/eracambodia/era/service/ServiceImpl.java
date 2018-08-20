package com.eracambodia.era.service;

import com.eracambodia.era.exception.CustomException;
import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.User;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_building.response.Buildings;
import com.eracambodia.era.model.api_building_available.response.BuildingAvailable;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.repository.api_agent_account_password.AgentChangePasswordRepo;
import com.eracambodia.era.repository.api_agent_account_update.UpdateAgentAccountRepo;
import com.eracambodia.era.repository.api_agent_profile_upload.UploadProfileAgentRepo;
import com.eracambodia.era.repository.api_building.BuildingsRepo;
import com.eracambodia.era.repository.api_building_available.BuildingAvailableRepo;
import com.eracambodia.era.repository.api_building_status_update.BuildingStatusUpdateRepo;
import com.eracambodia.era.repository.api_building_uuid.BuildingUUIDRepo;
import com.eracambodia.era.repository.api_login.LoginRepo;
import com.eracambodia.era.repository.api_register.RegisterRepo;
import com.eracambodia.era.repository.api_user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    //api/login
    @Autowired
    private LoginRepo loginRepo;
    @Override
    public User findUserByEmailOfLogin(String email) {
        return loginRepo.findUserByEmailOfLogin(email);
    }
    @Override
    public String checkLogin(Login login) {
        String password=loginRepo.checkLogin(login);
        if(password==null){
            throw new CustomException(401,"email not available.");
        }else  if(!BCrypt.checkpw(login.getPassword(),password)){
            throw new CustomException(401,"email or password not correct.");
        }
        return password;
    }

    // api/building/uuid
    @Autowired
    private BuildingUUIDRepo buildingUUIDRepo;
    @Override
    public BuildingUUID findBuildingByUUID(String uuid) {
        return buildingUUIDRepo.findBuildingByUUID(uuid);
    }

    // api/building
    @Autowired
    private BuildingsRepo buildingsRepo;
    @Override
    public List<Buildings> findBuildings(Pagination pagination) {
        if(pagination.getPage()>pagination.getTotalPage()){
            throw new CustomException(404,"Page not Found.");
        }
        return buildingsRepo.findBuildings(pagination);
    }
    @Override
    public int countBuildingsRecord() {
        int countAllBuildings =buildingsRepo.countBuildingsRecord();
        if(countAllBuildings<1){
            throw new CustomException(404,"No record of building ");
        }
        return countAllBuildings;
    }

    // api building/status/update
    @Autowired
    private BuildingStatusUpdateRepo buildingStatusUpdateRepo;
    @Override
    public Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate) {
        return buildingStatusUpdateRepo.updateBuildingStatus(buildingStatusUpdate);
    }
    @Override
    public Integer findBuildingIdByIdOfBuildingStatusUpdate(int ownerId) {
        return buildingStatusUpdateRepo.findBuildingIdByIdOfBuildingStatusUpdate(ownerId);
    }


    @Autowired
    private BuildingAvailableRepo buildingAvailableRepo;
    // api/building/available
    @Override
    public List<BuildingAvailable> findBuildingAvailable(Pagination pagination) {
        return buildingAvailableRepo.findBuildingAvailable(pagination);
    }

    // api/register
    @Autowired
    private RegisterRepo registerRepo;

    @Override
    public void register(Register register) {
        registerRepo.register(register);
    }

    @Override
    public List<Register> getEmail(String email,String idCard,String phonenumber) {
        List<Register> registers=registerRepo.getEmail(email,idCard,phonenumber);
        if(registers!=null){
            String message="";
            for(int i=0;i<registers.size();i++){
                if(email.equals(registers.get(i).getEmail())) {
                    message = "email already exist ";
                    throw new CustomException(401,message);
                }
                if(phonenumber.equals(registers.get(i).getPhone())) {
                    message = "phone already exist";
                    throw new CustomException(401,message);
                }
                if(idCard.equals(registers.get(i).getIdCard())) {
                    message = "id-card already exist";
                    throw new CustomException(401,message);
                }
            }
        }
       return registers;
    }

    // api/user
    @Autowired
    private UserRepo userRepo;
    @Override
    public com.eracambodia.era.model.api_usesr.response.User findUserByUsernameOfUser(String username) {
        return userRepo.findUserByUsernameOfUser(username);
    }

    //api/agent/profile/upload
    private UploadProfileAgentRepo uploadProfileAgentRepo;
    @Override
    public String findImageByUsernameOfUploadProfileAgent(String username) {
        return uploadProfileAgentRepo.findImageByUsernameOfUploadProfileAgent(username);
    }

    @Override
    public void updateImageProfileOfUploadProfileAgent(String image, String email) {
        uploadProfileAgentRepo.updateImageProfileOfUploadProfileAgent(image,email);
    }

    // api/agent/account/password
    private AgentChangePasswordRepo agentChangePasswordRepo;
    @Override
    public void updateUserPassword(String password, String email) {
        agentChangePasswordRepo.updateUserPassword(password,email);
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
        updateAgentAccountRepo.updateUserInformation(updateAgentAccount,email);
    }
}

