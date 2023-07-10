package com.tainika.qlnt.qlnt.repository;

import com.tainika.qlnt.qlnt.dto.setting.UsersRequest;
import com.tainika.qlnt.qlnt.model.User;

import java.util.List;

public interface UserRepositoryCustom {

    boolean isExistedUserName(String userName);

    boolean isExistedEmail(String email);

    List<User> searchAllUser(UsersRequest request);
}
