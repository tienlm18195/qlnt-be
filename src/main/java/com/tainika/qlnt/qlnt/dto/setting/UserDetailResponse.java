package com.tainika.qlnt.qlnt.dto.setting;

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
    private Integer birthYear;
    private String avatarPath;
    private String identityImagePath;
    private Integer status;
    private boolean isBlackList;
}
