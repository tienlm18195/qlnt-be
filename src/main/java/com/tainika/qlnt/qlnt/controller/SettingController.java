package com.tainika.qlnt.qlnt.controller;

import com.tainika.qlnt.qlnt.dto.setting.room.RoomDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomsRequest;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomsResponse;
import com.tainika.qlnt.qlnt.dto.setting.user.UserDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.user.UserDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.user.UsersRequest;
import com.tainika.qlnt.qlnt.dto.setting.user.UsersResponse;
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

    @GetMapping(path = "/rooms")
    public ResponseEntity<RoomsResponse> findAllRooms(@RequestBody RoomsRequest request) {
        return new ResponseEntity<>(settingService.findAllRooms(request), HttpStatus.OK);
    }

    @PostMapping(path = "/rooms")
    public ResponseEntity<?> createRoom(@RequestBody RoomDetailRequest request) {
        try {
            return new ResponseEntity<>(settingService.createRoom(request), HttpStatus.OK);
        } catch (IllegalAccessException accessException) {
            return new ResponseEntity<>(accessException.toString(), HttpStatus.FORBIDDEN);
        }
    }
}
