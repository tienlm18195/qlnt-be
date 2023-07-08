package com.tainika.qlnt.qlnt.dto.setting;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersResponse {
    private String userName;
    private String fullName;
    private String email;
    private Integer birthYear;
    private String workPlace;
    private String avatarPath;
    private Integer status;
    private boolean isBlackList;
}
