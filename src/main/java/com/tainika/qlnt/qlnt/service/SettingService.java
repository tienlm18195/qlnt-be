package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.dto.setting.UserDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.UserDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.UsersRequest;
import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tainika.qlnt.qlnt.constants.Message.ACTION.*;
import static com.tainika.qlnt.qlnt.constants.Message.ALERT.NO_RESULT;

@Service
public class SettingService {
    private final BaseService baseService;
    private final UserRepository userRepository;

    @Autowired
    public SettingService(BaseService baseService, UserRepository userRepository) {
        this.baseService = baseService;
        this.userRepository = userRepository;
    }

    public UsersResponse getAllUser(UsersRequest request) throws Exception {
        try{
            List<User> users = userRepository.searchAllUser(request);
            long totalRecords = userRepository.countTotalUsersRecord(request);
            List<UsersResponse.UserRecord> records = users.stream()
                .map(User::convertToGetUserRecordResponseData)
                .collect(Collectors.toList());

            return UsersResponse.builder()
                .users(records)
                .page(request.getPage())
                .size(request.getSize())
                .total((int) totalRecords)
                .build();
        } catch (Exception err) {
            throw MessageResultService.builder()
                .action(GET_ALL)
                .responseMessage(err.getMessage())
                .build()
            .withErrorResponse().throwException();
        }
    }

    public UserDetailResponse getUserDetailById(String userId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.convertToDetailUserResponseData(BaseService.hasAdminPermission(authorities));
        }

        throw MessageResultService.builder()
            .action(GET_DETAIL)
            .content(NO_RESULT)
            .build()
        .withFailureResponse().throwException();
    }

    public UserDetailResponse updateUser(String userId, UserDetailRequest request) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        User updateUser = userRepository.findById(userId).orElse(null);
        if (updateUser != null) {
            User updated = request.updateUserByAuthorityOfLoginUser(updateUser, authorities, baseService::getOrCreateRole);
            userRepository.save(updated);

            return updated.convertToDetailUserResponseData(BaseService.hasAdminPermission(authorities));
        }

        throw MessageResultService.builder()
            .action(UPDATE)
            .content("Update user are not existed")
            .build()
        .withFailureResponse().throwException();
    }
}
