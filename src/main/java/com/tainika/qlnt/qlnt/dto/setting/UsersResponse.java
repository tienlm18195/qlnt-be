package com.tainika.qlnt.qlnt.dto.setting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    protected String userId;
    protected String fullName;
    protected String email;
    protected Integer birthYear;
    protected String avatarPath;
    protected Integer status;
    protected boolean isBlackList;
}
