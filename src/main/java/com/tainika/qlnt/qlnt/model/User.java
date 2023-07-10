package com.tainika.qlnt.qlnt.model;

import com.tainika.qlnt.qlnt.dto.setting.UserDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.dto.signup.NewUserResponse;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="user")
public class User extends BaseModel {
    @Id
    private String id;
    @Lazy
    private Role role;
    private String roomId;
    private String roomName;
    @Indexed
    private String userName;
    private String password;
    private String salt;
    private String fullName;
    @Indexed
    private String email;
    private Integer age;
    private Integer birthYear;
    private String phone;
    private String identityNumber;
    private String address;
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
            .build();
    }

    public UsersResponse convertToGetUsersResponseData() {
        return UsersResponse.builder()
            .userId(id)
            .email(email)
            .fullName(fullName)
            .birthYear(birthYear)
            .avatarPath(avatarPath)
            .status(status)
            .isBlackList(isBlackList)
            .build();
    }

    public UserDetailResponse convertToDetailUserResponseData() {
        return UserDetailResponse.builder()
            .userId(id)
            .userName(userName)
            .fullName(fullName)
            .email(email)
            .phone(phone)
            .identityNumber(identityNumber)
            .address(address)
            .birthYear(birthYear)
            .workPlace(workPlace)
            .avatarPath(avatarPath)
            .identityImagePath(identityImagePath)
            .status(status)
            .isBlackList(isBlackList)
            .build();
    }
}
