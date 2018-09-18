package com.eracambodia.era.service;

import com.eracambodia.era.exception.CustomException;
import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.Response;
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
import com.eracambodia.era.model.api_agent_transaction_total_commission.response.AgentGot;
import com.eracambodia.era.model.api_building.response.Buildings;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_noti_favoritor.Notification;
import com.eracambodia.era.model.api_noti_favoritor.Transaction;
import com.eracambodia.era.model.api_register.RegisterUniqueFields;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.repository.api_agent_account_password.AgentChangePasswordRepo;
import com.eracambodia.era.repository.api_agent_account_update.UpdateAgentAccountRepo;
import com.eracambodia.era.repository.api_agent_favorite.AgentFavoriteRepo;
import com.eracambodia.era.repository.api_agent_favorite_add.AgentAddFavoriteRepo;
import com.eracambodia.era.repository.api_agent_favorite_delete.AgentDeleteFavoriteRepo;
import com.eracambodia.era.repository.api_agent_member_uuid.AgentMemberUUIDRepo;
import com.eracambodia.era.repository.api_agent_members_direct_uuid.AgentMembersDirectRepo;
import com.eracambodia.era.repository.api_agent_profile_upload.UploadProfileAgentRepo;
import com.eracambodia.era.repository.api_agent_building_status_status.AgentRepo;
import com.eracambodia.era.repository.api_agent_transaction_useruuid_status.AgentTransactionRepo;
import com.eracambodia.era.repository.api_agent_trasaction_total_commission.AgentCommissionRepo;
import com.eracambodia.era.repository.api_building.BuildingsRepo;
import com.eracambodia.era.repository.api_building_status_update.BuildingStatusUpdateRepo;
import com.eracambodia.era.repository.api_building_uuid.BuildingUUIDRepo;
import com.eracambodia.era.repository.api_login.LoginRepo;
import com.eracambodia.era.repository.api_noti_favoritor.NotiToFavoritorRepo;
import com.eracambodia.era.repository.api_register.RegisterRepo;
import com.eracambodia.era.repository.api_search.SearchRepo;
import com.eracambodia.era.repository.api_user.UserRepo;
import com.eracambodia.era.setting.Default;
import com.eracambodia.era.utils.DecodeJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    public String checkLogin(Login login,String playerId) {
        String password = loginRepo.checkLogin(login);
        if (password == null) {
            throw new CustomException(404, "Email Not Found.");
        }
        if (!BCrypt.checkpw(login.getPassword(), password)) {
            throw new CustomException(404, "email or password not correct.");
        }
        if (BCrypt.checkpw(login.getPassword(), password)) {
            Integer userId=loginRepo.checkEmail(login.getEmail());
            if (userId == null) {
                throw new CustomException(401, "account need to approve from admin.");
            }
            if(playerId!=null) {
                List<String> pid = loginRepo.getPlayerId(userId);
                for (int i = 0; i < pid.size(); i++) {
                    if (!playerId.equalsIgnoreCase(playerId)) {
                        loginRepo.savePlayerId(userId, playerId);
                    }
                }
            }
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

    // api/building/status/{status}
    @Autowired
    private BuildingsRepo buildingsRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<Buildings> findBuildings(Pagination pagination, String status) {
        if (!status.equalsIgnoreCase("all")) {
            List<Buildings> buildings = buildingsRepo.findBuildings(pagination, status);
            if (buildings.size() < 1) {
                throw new CustomException(404, "Page not Found.");
            }
            pagination.setTotalItem(buildingsRepo.countBuildingsRecord(status));
            return buildings;
        }
        List<Buildings> buildings = buildingsRepo.findAllBuildings(pagination);
        if (buildings.size() < 1) {
            throw new CustomException(404, "Page not Found.");
        }
        pagination.setTotalItem(buildingsRepo.countAllBuildingsRecord());
        return buildings;
    }

    // api building/status/update
    @Autowired
    private BuildingStatusUpdateRepo buildingStatusUpdateRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public Object updateBuildingStatus(BuildingStatusUpdate buildingStatusUpdate, String email) {
        int id = buildingStatusUpdateRepo.getUserEmail(email);
        if (buildingStatusUpdateRepo.findBuildingIdByIdOfBuildingStatusUpdate(buildingStatusUpdate.getOwnerId()) == null) {
            throw new CustomException(404, "Building not found");
        }
        buildingStatusUpdate.setUserId(id);
        Object result=buildingStatusUpdateRepo.updateBuildingStatus(buildingStatusUpdate);
        Notification notification=new Notification();
        notification.setBuildingID(buildingStatusUpdate.getOwnerId());
        this.pushFavorite(notification,buildingStatusUpdate.getStatus(),email,id);
        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public Integer findBuildingIdByIdOfBuildingStatusUpdate(int ownerId) {
        return buildingStatusUpdateRepo.findBuildingIdByIdOfBuildingStatusUpdate(ownerId);
    }

    // api/register
    @Autowired
    private RegisterRepo registerRepo;

    @Override
    public void register(Register register, String jwtToken,String playerId) {
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

        String email = DecodeJWT.getEmailFromJwt(jwtToken);
        Integer userId = registerRepo.getIdByEmail(email);

        if (registerRepo.register(register, userId) > 0) {
            if (userId != null) {
                registerRepo.enable(register.getEmail());
            }
            if(playerId!=null) {
                Integer uid = registerRepo.getIdByEmail(register.getEmail());
                registerRepo.savePlayerId(uid, playerId);
            }
        }
    }

    // api/user
    @Autowired
    private UserRepo userRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public String findImageByUsernameOfUploadProfileAgent(String username) {
        return uploadProfileAgentRepo.findImageByUsernameOfUploadProfileAgent(username);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public void updateImageProfileOfUploadProfileAgent(String image, String email) {
        uploadProfileAgentRepo.updateImageProfileOfUploadProfileAgent(image, email);
    }

    // api/agent/account/password
    @Autowired
    private AgentChangePasswordRepo agentChangePasswordRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public void updateUserPassword(String password, String email) {
        agentChangePasswordRepo.updateUserPassword(password, email);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public String getUserPasswordByEmail(String email) {
        return agentChangePasswordRepo.getUserPasswordByEmail(email);
    }

    // api/agent/account/update
    @Autowired
    private UpdateAgentAccountRepo updateAgentAccountRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public void updateUserInformation(UpdateAgentAccount updateAgentAccount, String email) {
        String password = updateAgentAccountRepo.getUserPassword(email);
        boolean checkPassword = passwordEncoder.matches(updateAgentAccount.getConfirmPassword(), password);
        if (!checkPassword) {
            throw new CustomException(404, "Password not match.");
        }
        if (updateAgentAccount.getPhone().length() < 1) {
            updateAgentAccountRepo.updateUsername(updateAgentAccount, email);
        } else {
            String phone = registerRepo.getPhone(updateAgentAccount.getPhone());
            if (phone != null) {
                throw new CustomException(409, "Phone already exist");
            }
            updateAgentAccountRepo.updateUserInformation(updateAgentAccount, email);
        }
    }

    // api/agent/transaction/{useruuid}/{status}
    @Autowired
    private AgentTransactionRepo agentTransactionRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<TransactionResponse> findAgentsTransaction(String userUUID, String status, Pagination pagination) {
        if (!status.equalsIgnoreCase("all")) {
            List<TransactionResponse> transactionResponses = agentTransactionRepo.findAgentsTransaction(userUUID, status, pagination);
            if (transactionResponses.size() < 1)
                throw new CustomException(404, "No record");
            pagination.setTotalItem(agentTransactionRepo.countTransaction(userUUID, status));
            return transactionResponses;
        }
        List<TransactionResponse> transactionResponses = agentTransactionRepo.findAgentsAllTransaction(userUUID, pagination);
        if (transactionResponses.size() < 1)
            throw new CustomException(404, "No record");
        pagination.setTotalItem(agentTransactionRepo.countAllTransaction(userUUID));
        return transactionResponses;
    }


    // api/agent/favorite
    @Autowired
    private AgentFavoriteRepo agentFavoriteRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<AgentFavorite> findAgentFavorite(String email, Pagination pagination) {
        List<AgentFavorite> agentFavorites = agentFavoriteRepo.findAgentFavorite(email, pagination);
        if (agentFavorites.size() < 1) {
            throw new CustomException(404, "No record.");
        }
        for(int i=0;i<agentFavorites.size();i++){
            if(agentFavorites.get(i).getStatus().equalsIgnoreCase("available")){
                agentFavorites.get(i).setAgent(null);
            }
        }
        pagination.setTotalItem(agentFavoriteRepo.countAgentFavorite(email));
        return agentFavorites;
    }

    // api/agent/favorite/add
    @Autowired
    private AgentAddFavoriteRepo agentAddFavoriteRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public void addFavorite(AgentAddFavorite agentAddFavorite, String email) {
        agentAddFavorite.setUserId(agentAddFavoriteRepo.getUserIdByEmail(email));
        if (agentAddFavoriteRepo.addFavorite(agentAddFavorite) < 1) {
            throw new CustomException(403, "No record has deleted");
        }
    }

    // api/agent/favorite/delete
    @Autowired
    private AgentDeleteFavoriteRepo agentDeleteFavoriteRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public void deleteAgentFavorite(AgentDeleteFavorite agentDeleteFavorite, String email) {
        int id = agentDeleteFavoriteRepo.findUserByEmail(email);
        agentDeleteFavorite.setUserId(id);
        if (agentDeleteFavoriteRepo.deleteAgentFavorite(agentDeleteFavorite) < 1) {
            throw new CustomException(403, "No record have deleted");
        }
    }

    // api/search
    @Autowired
    private SearchRepo searchRepo;

    @Override
    public List<Buildings> search(String keyword, String type, Pagination pagination) {
        List<Buildings> buildings = searchRepo.search(keyword, type, pagination.getLimit(), pagination.getOffset());
        if (buildings.size() < 1) {
            throw new CustomException(404, "No record");
        }
        pagination.setTotalItem(searchRepo.countSearch(keyword));
        return buildings;
    }

    @Override
    public List<String> projectType() {
        List<String> type = searchRepo.projectType();
        if (type == null) {
            throw new CustomException(404, "Project Type No Record.");
        }
        return type;
    }

    // api/agent/members/uuid
    @Autowired
    private AgentMemberUUIDRepo agentMemberUUIDRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<AgentMember> findAgentMember(String uuid) {
        Integer userId = agentMemberUUIDRepo.getIdFromAgent(uuid);
        if (userId == null) {
            throw new CustomException(404, "Not found.");
        }
        return agentMemberUUIDRepo.findAgentMember(userId);
    }

    // api/agent/member/direct/uuid
    @Autowired
    private AgentMembersDirectRepo agentMembersDirectRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<AgentMemberDirect> findAgentMemberDirect(String uuid, Pagination pagination) {
        Integer userId = agentMembersDirectRepo.getUserParentId(uuid);
        if (userId == null) {
            throw new CustomException(404, "Agent not found.");
        }
        return agentMembersDirectRepo.findAgentMemberDirect(userId);
    }

    // api/noti/favorite
    @Autowired
    private NotiToFavoritorRepo notiToFavoritorRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<String> findPlayerId(String email,int ownerId,Integer agentId) {
        List<String> playerId = notiToFavoritorRepo.findPlayerId(agentId, ownerId);
        if (playerId == null || playerId.size() < 1) {
            playerId=null;
        }
        return playerId;
    }

    private void pushFavorite(Notification notification,String status, String email,Integer agentId) {
        List<String> playerIds = this.findPlayerId(email, notification.getBuildingID(),agentId);
        String buildingName=notiToFavoritorRepo.buildingName(notification.getBuildingID());
        String agentName=notiToFavoritorRepo.agentName(email);
        String title="Status Changed";
        String content=agentName+" change "+buildingName+"'s status to "+status+".";
        notification.setTitle(title);
        notification.setContent(content);
        String profilePhoto = notiToFavoritorRepo.getImage(email);
        if (profilePhoto != null) {
            profilePhoto = Default.profilePhoto + profilePhoto;
        }else {
            profilePhoto=Default.profilePhoto+"era.jpg";
        }
        String arrayIds = "";
        if(playerIds!=null) {
            arrayIds = "[";
            for (int i = 0; i < playerIds.size(); i++) {
                arrayIds += "\"" + playerIds.get(i) + "\"";
                if (i + 1 < playerIds.size()) {
                    arrayIds += ",";
                }
            }
            arrayIds += "]";
        }
        String jsonResponse = "";
        int statusCode = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + Default.oneSignalRestAPIKey);
            con.setRequestMethod("POST");
            String strJsonBody = "{"
                    + "\"app_id\": \"" + Default.oneSignalAppID + "\","
                    + "\"include_player_ids\" : " + arrayIds + ","
                    + "\"big_picture\": \"" + notification.getBigPicture() + "\","
                    + "\"headings\": {\"en\":\"" + notification.getTitle() + "\"},"
                    + "\"data\": {\"type\": \"buildingDetail\", \"key\": \""+notification.getBuildingID()+"\", \"key\": \""+timestamp.getTime()+"\" }, "
                    + "\"large_icon\": \"" + profilePhoto + "\","
                    + "\"contents\": {\"en\": \"" + notification.getContent() + "\"}"
                    + "}";
            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            statusCode = httpResponse;

            if (httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            throw new CustomException(200,jsonResponse.toString());
        } catch (Throwable t) {
            throw new CustomException(statusCode, jsonResponse.toString());
        }
    }

    // api/agent/status/{status}
    @Autowired
    private AgentRepo agentRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public List<Agent> findAgentProcess(String status, String email, Pagination pagination) {
        if (status.equalsIgnoreCase("all")) {
            List<Agent> list = agentRepo.findAgentsAllProcess(email, pagination);
            if (list.size() < 1) {
                throw new CustomException(404, "No record.");
            }
            pagination.setTotalItem(agentRepo.countAgentAllProcess(email));
            return list;
        } else {
            List<Agent> list = agentRepo.findAgentsProcess(status, email, pagination);
            if (list.size() < 1) {
                throw new CustomException(404, "No record of " + status);
            }
            pagination.setTotalItem(agentRepo.countAgentProcess(email, status));
            return list;
        }
    }

    //api/agent/transaction/total_commission
    @Autowired
    private AgentCommissionRepo agentCommissionRepo;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    public AgentCommission commissionCalculator(String uuid) {
        AgentCommission agentCommission = agentCommissionRepo.commissionCalculator(uuid);
        Double totalPrice = agentCommission.getBuildingCompleted();
        List<AgentGot> listAgentGot = agentCommissionRepo.getAgentCommissionAmount(uuid);
        agentCommission.setAgentGot(agentAmount(listAgentGot, totalPrice));
        return agentCommission;
    }

    private Double agentAmount(List<AgentGot> agentGotList, double totalPrice) {
        double amount = 0;
        double witholding = agentCommissionRepo.getBusinessValue("witholding");
        double vats = agentCommissionRepo.getBusinessValue("vat");
        double agentCommission = agentCommissionRepo.getBusinessValue("agent commission");
        double leaderCommission = agentCommissionRepo.getBusinessValue("leader commission");
        for (int i = 0; i < agentGotList.size(); i++) {
            double commission = totalPrice * agentGotList.get(i).getCommission();
            double netAfterTax = 0;
            if (agentGotList.get(i).isIncludeVat()) {
                netAfterTax = commission / 1.1;
            } else {
                netAfterTax = commission;
            }
            double vat = netAfterTax * vats;
            double commissionForAgent = netAfterTax * agentCommission;
            double withHolding = commissionForAgent * witholding;
            double agentAmount = commissionForAgent - withHolding;
            double leaderIncome = agentAmount * leaderCommission;
            double leaderAmount = leaderIncome - (leaderIncome * witholding);
            double companyIncome = netAfterTax * 0.5;
            double companyAmount = companyIncome - leaderIncome;
            amount += agentAmount;
        }
        return amount;
    }


}

