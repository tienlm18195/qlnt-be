package com.tainika.qlnt.qlnt.model;

import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.dto.signup.NewUserResponse;
import com.tainika.qlnt.qlnt.repository.UserRepository;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="user")
public class User extends BaseModel {
    @Id
    private String id;
    private Role role;
    private String roomId;
    private String roomName;
    private String userName;
    private String password;
    private String salt;
    private String fullName;
    private String email;
    private Integer age;
    private Integer birthYear;
    private String phone;
    private String identityNumber;
    private String address;
    private String currentAddress;
    private String workPlace;
    private String avatarPath;
    private String identityImagePath;
    private Integer status;
    private boolean isBlackList;
    private Integer acceptorId;

    public NewUserResponse convertToNewUserResponseData() {
        return NewUserResponse.builder()
            .userName(userName)
            .age(age)
            .email(email)
            .phoneNumber(phone)
            .identityNumber(identityNumber)
            .address(address)
            .currentAddress(currentAddress)
            .build();
    }

    public UsersResponse convertToUsersResponseData() {
        return UsersResponse.builder()
            .userName(userName)
            .email(email)
            .fullName(fullName)
            .birthYear(birthYear)
            .workPlace(workPlace)
            .avatarPath(avatarPath)
            .status(status)
            .isBlackList(isBlackList)
            .build();
    }
}
