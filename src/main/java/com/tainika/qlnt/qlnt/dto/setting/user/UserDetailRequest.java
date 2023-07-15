package com.tainika.qlnt.qlnt.dto.setting.user;

import com.tainika.qlnt.qlnt.constants.AppUserRole;
import com.tainika.qlnt.qlnt.model.Role;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.service.BaseService;
import com.tainika.qlnt.qlnt.service.MessageResultService;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

import static com.tainika.qlnt.qlnt.constants.Message.ACTION.UPDATE;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
public class UserDetailRequest {
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
    private String role;
    private boolean isBlackList;

    public User updateUserByAuthorityOfLoginUser(User updateUser,
                                                 List<String> authorities,
                                                 Function<AppUserRole, Role> getRoleFunc) throws Exception {
        boolean isUpdateUserName = isNotBlank(userName) && !userName.equals(updateUser.getUserName());
        if (isUpdateUserName) updateUser.setUserName(userName);

        boolean isUpdateFullName = isNotBlank(fullName) && !fullName.equals(updateUser.getFullName());
        if (isUpdateFullName) updateUser.setFullName(fullName);

        boolean isUpdatePhone = isNotBlank(phone) && !phone.equals(updateUser.getPhone());
        if (isUpdatePhone) updateUser.setPhone(phone);

        boolean isUpdateEmail = isNotBlank(email) && !email.equals(updateUser.getEmail());
        if (isUpdateEmail) updateUser.setEmail(email);

        boolean isUpdateAddress = isNotBlank(address) && !address.equals(updateUser.getAddress());
        if (isUpdateAddress) updateUser.setAddress(address);

        boolean isUpdateIdentityNumber = isNotBlank(identityNumber) && !identityNumber.equals(updateUser.getIdentityNumber());
        if (isUpdateIdentityNumber) updateUser.setIdentityNumber(identityNumber);

        boolean isUpdateWorkPlace = isNotBlank(workPlace) && !workPlace.equals(updateUser.getWorkPlace());
        if (isUpdateWorkPlace) updateUser.setWorkPlace(workPlace);

        boolean isUpdateBirthYear = birthYear != null && birthYear > 0 && !birthYear.equals(updateUser.getBirthYear());
        if (isUpdateBirthYear) updateUser.setBirthYear(birthYear);

        boolean isUpdateAvatarPath = isNotBlank(avatarPath) && !avatarPath.equals(updateUser.getAvatarPath());
        if (isUpdateAvatarPath) updateUser.setAvatarPath(avatarPath);

        boolean isUpdateRole = isNotBlank(role) && !role.equals(updateUser.getRole().getCode());
        if (isUpdateRole) {
            boolean validRole = BaseService.checkRoleCodeEnum(role);
            boolean hasAdminPermission = BaseService.hasAdminPermission(authorities);

            if (validRole && hasAdminPermission) {
                updateUser.setRole(getRoleFunc.apply(AppUserRole.valueOf(role)));
            } else {
                 throw MessageResultService.builder()
                    .action(UPDATE)
                    .content("Update failure!! Not have a permission to update role")
                    .build().withFailureResponse().throwIllegalAccessException();
            }
        }
        return updateUser;
    }
}
