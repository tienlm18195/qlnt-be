package com.tainika.qlnt.qlnt.dto.signup;

import com.tainika.qlnt.qlnt.constants.Status;
import com.tainika.qlnt.qlnt.model.User;
import lombok.Data;

import static com.google.common.base.Strings.nullToEmpty;
import static com.tainika.qlnt.qlnt.ultil.DateUtils.now;

@Data
public class NewUserRequest {
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private int age;
    private int birthYear;
    private String phoneNumber;
    private String identityNumber;
    private String address;
    private String currentAddress;

    public User convertToUser(String encode) {
        User rs = new User();
        rs.setUserName(userName);
        rs.setPassword(encode);
        rs.setFullName(nullToEmpty(fullName));
        rs.setEmail(nullToEmpty(email));
        rs.setAge(age);
        rs.setBirthYear(birthYear);
        rs.setPhone(nullToEmpty(phoneNumber));
        rs.setIdentityNumber(nullToEmpty(identityNumber));
        rs.setAddress(nullToEmpty(address));

        rs.setCreateTime(now());
        rs.setUpdateTime(now());
        rs.setStatus(Status.USER.TEMPORARY.getCode());
        return rs;
    }
}
