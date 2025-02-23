package com.tainika.qlnt.qlnt.dto.setting.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDetailResponse {
    private String userId;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String identityNumber;
    private String workPlace;
    private String address;
    private String dateOfBirth;
    private String avatarPath;
    private String identityImagePath;
    private Integer status;
    private String role;
    private boolean isBlackList;
}
