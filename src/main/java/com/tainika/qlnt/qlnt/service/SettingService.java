package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.repository.UserRepository;
import com.tainika.qlnt.qlnt.constants.Message;
import org.springframework.beans.factory.annotation.Autowired;
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
                .map(User::convertToUsersResponseData)
                .collect(Collectors.toList());
            return new MessageResultService<>(Message.ACTION.GET_ALL, responses).withSuccessResponse();
        } catch (Exception err) {
            return new MessageResultService<List<UsersResponse>>(Message.ACTION.GET_ALL, err.getMessage(),
                new ArrayList<>()).withErrorResponse();
        }
    }
}
