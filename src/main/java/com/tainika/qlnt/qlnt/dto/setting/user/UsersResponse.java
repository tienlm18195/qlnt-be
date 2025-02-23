package com.tainika.qlnt.qlnt.dto.setting.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    private List<UserRecord> users;
    private int page;
    private int size;
    private int total;

    @Data
    @Builder
    public static class UserRecord {
        private String userId;
        private String fullName;
        private String email;
        private String dateOfBirth;
        private String avatarPath;
        private Integer status;
        private boolean isBlackList;
    }
}
