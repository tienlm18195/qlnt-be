package com.tainika.qlnt.qlnt.controller;

import com.tainika.qlnt.qlnt.dto.setting.UserDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.UsersRequest;
import com.tainika.qlnt.qlnt.dto.setting.UsersResponse;
import com.tainika.qlnt.qlnt.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ss")
public class SettingController {
    @Autowired
    SettingService settingService;

    @GetMapping(path = "/users")
    public ResponseEntity<UsersResponse> findAllUser(@RequestBody UsersRequest request) throws Exception {
        return new ResponseEntity<>(settingService.getAllUser(request), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return new ResponseEntity<>(settingService.getUserDetailById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDetailRequest request) {
        return new ResponseEntity<>(settingService.updateUser(id, request), HttpStatus.OK);
    }

}
