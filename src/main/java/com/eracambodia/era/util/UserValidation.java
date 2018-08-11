package com.eracambodia.era.util;

import com.eracambodia.era.model.User;
import com.eracambodia.era.model.swagger.UpdateUserInfo;
import com.eracambodia.era.model.swagger.UserLogin;
import com.eracambodia.era.model.swagger.UserRegister;
import com.eracambodia.era.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

public class UserValidation {
    public static User checkUserExistOrNot(User user, UserService userService){
        if(user.getPassword()!=null&&user.getEmail()!=null || user.getPassword().length()<1&&user.getEmail().length()<1) {
            User u1 = user;
            User u2=null;
            if((u2=userService.userAvailable(user))!=null) {
                if(BCrypt.checkpw(u1.getPassword(),u2.getPassword())){
                    return new User();
                }else
                    return null;
            }else
                return null;
        }
        return null;
    }
    public static User checkUserFields(User user){
        if(user!=null){
            if(user.getUsername()!=null && user.getEmail()!=null && user.getPassword()!=null && user.getDob()!=null && user.getGender()!=null && user.getIdcard()!=null && user.getPhonenumber()!=null){
                return new User();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    public static UserLogin checkUserLoginFields(UserLogin userLogin){
        if(userLogin.getPassword()!=null && userLogin.getEmail()!=null){
            return new UserLogin();
        }else {
            return null;
        }
    }
    public static UserRegister checkUserRegisterFields(UserRegister userRegister){
        if(userRegister!=null){
            if(userRegister.getUsername()!=null && userRegister.getEmail()!=null && userRegister.getPassword()!=null && userRegister.getDob()!=null && userRegister.getGender()!=null && userRegister.getIdcard()!=null && userRegister.getPhonenumber()!=null){
                return new UserRegister();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    public static boolean checkImageExtension(MultipartFile file){
        String extension=com.google.common.io.Files.getFileExtension(file.getOriginalFilename());
        String imageExtension[]={"jpg","png","jpge"};
        boolean check=false;
        for(int i=0;i<imageExtension.length;i++){
            if(extension.equals(imageExtension[i])){
                check=true;
            }
        }
        return check;
    }
    public static boolean checkUpdateUserInfo(UpdateUserInfo updateUserInfo){
        if(updateUserInfo.getPhonenumber()!=null && updateUserInfo.getUsername()!=null){
            return true;
        }
        return false;
    }
}
