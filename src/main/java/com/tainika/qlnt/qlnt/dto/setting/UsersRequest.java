package com.tainika.qlnt.qlnt.dto.setting;

import lombok.Data;

@Data
public class UsersRequest {
    private String fullName;
    private String email;
    private String phone;
    private String identityNumber;
    private String address;
    private boolean isBlackList;
    private boolean isDeleted;
    private int status;
    private int page;
    private int size;
}
