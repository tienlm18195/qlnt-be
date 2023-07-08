package com.tainika.qlnt.qlnt.dto.signup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserResponse {
    private String userName;
    private String email;
    private int age;
    private int birthYear;
    private String phoneNumber;
    private String identityNumber;
    private String address;
    private String currentAddress;
}
