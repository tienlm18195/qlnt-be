package com.tainika.qlnt.qlnt.controller;

import com.tainika.qlnt.qlnt.dto.setting.UserDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.UserDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.UsersRequest;
import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.service.RoleService;
import com.tainika.qlnt.qlnt.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ss")
public class SettingController {
    private final SettingService settingService;
    private final RoleService roleService;

    @Autowired
    public SettingController(SettingService settingService,
                             RoleService roleService) {
        this.settingService = settingService;
        this.roleService = roleService;
    }

    @GetMapping(path = "/users")
    public ResponseEntity<UsersResponse> findAllUser(@RequestBody UsersRequest request) throws Exception {
        return new ResponseEntity<>(settingService.getAllUser(request), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDetailResponse> findById(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(settingService.getUserDetailById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserDetailResponse> updateUser(@PathVariable String id, @RequestBody UserDetailRequest request) throws Exception {
        return new ResponseEntity<>(settingService.updateUser(id, request), HttpStatus.OK);
    }

    @GetMapping(path = "/roles")
    public ResponseEntity<List<String>> findAllRoles() {
        return new ResponseEntity<>(roleService.findAllRoleCode(), HttpStatus.OK);
    }
}
