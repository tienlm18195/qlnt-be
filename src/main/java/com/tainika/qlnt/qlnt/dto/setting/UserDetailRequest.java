package com.tainika.qlnt.qlnt.dto.setting;

import com.tainika.qlnt.qlnt.constants.AppUserPermission;
import com.tainika.qlnt.qlnt.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailRequest {
    private String userId;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String identityNumber;
    private String workPlace;
    private String address;
    private Integer birthYear;
    private String avatarPath;
    private Integer status;
    private boolean isBlackList;

    public User updateUserByAuthorityOfLoginUser(User updateUser, List<String> authorities) {
        authorities.forEach(auth -> {
            switch (AppUserPermission.valueOf(auth)) {
                case AM01:
                case MR01:
                    updateUser.setFullName(fullName);
                    updateUser.setPhone(phone);
                    updateUser.setEmail(email);
                    updateUser.setAddress(address);
                    updateUser.setIdentityNumber(identityNumber);
                    updateUser.setWorkPlace(workPlace);
                    updateUser.setBirthYear(birthYear);
                    updateUser.setAvatarPath(avatarPath);
                    updateUser.setStatus(status);
                    updateUser.setBlackList(isBlackList);
                default:
            }
        });
        return updateUser;
    }
}
