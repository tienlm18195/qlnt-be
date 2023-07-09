package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.dto.setting.UserDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.UserDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.repository.UserRepository;
import com.tainika.qlnt.qlnt.constants.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingService {
    @Autowired
    private UserRepository userRepository;

    public MessageResultService<List<UsersResponse>> getAllUser() {
        try{
            List<User> users = userRepository.findAll();
            List<UsersResponse> responses = users.stream()
                .map(User::convertToGetUsersResponseData)
                .collect(Collectors.toList());
            return new MessageResultService<>(Message.ACTION.GET_ALL, responses).withSuccessResponse();
        } catch (Exception err) {
            return new MessageResultService<List<UsersResponse>>(Message.ACTION.GET_ALL, err.getMessage(),
                new ArrayList<>()).withErrorResponse();
        }
    }

    public MessageResultService<UserDetailResponse> getUserDetailById(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new MessageResultService<>(Message.ACTION.GET_DETAIL, user.convertToDetailUserResponseData())
                .withSuccessResponse();
        }
        return new MessageResultService<UserDetailResponse>(Message.ACTION.GET_DETAIL)
            .withFailureResponse();
    }

    public MessageResultService<UserDetailResponse> updateUser(String userId, UserDetailRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        User updateUser = userRepository.findById(userId).orElse(null);
        if (updateUser != null) {
            User updated = request.updateUserByAuthorityOfLoginUser(updateUser, authorities);
            userRepository.save(updated);

            return new MessageResultService<>(Message.ACTION.UPDATE, updated.convertToDetailUserResponseData())
                .withSuccessResponse();
        }
        return new MessageResultService<UserDetailResponse>(Message.ACTION.UPDATE)
            .withFailureResponse();
    }
}
