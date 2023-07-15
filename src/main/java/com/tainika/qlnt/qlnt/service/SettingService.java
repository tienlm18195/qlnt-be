package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.dto.setting.room.RoomDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomsRequest;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomsResponse;
import com.tainika.qlnt.qlnt.dto.setting.user.UserDetailRequest;
import com.tainika.qlnt.qlnt.dto.setting.user.UserDetailResponse;
import com.tainika.qlnt.qlnt.dto.setting.user.UsersRequest;
import com.tainika.qlnt.qlnt.dto.setting.user.UsersResponse;
import com.tainika.qlnt.qlnt.model.Room;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.model.UserLoginDetails;
import com.tainika.qlnt.qlnt.repository.RoomRepository;
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
import static com.tainika.qlnt.qlnt.ultil.DateUtils.now;

@Service
public class SettingService {
    private final BaseService baseService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public SettingService(BaseService baseService,
                          UserRepository userRepository,
                          RoomRepository roomRepository) {
        this.baseService = baseService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
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
            .withErrorResponse().throwRuntimeException();
        }
    }

    public UserDetailResponse getUserDetailById(String userId) throws RuntimeException {
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
            .responseMessage(NO_RESULT)
            .build()
        .withFailureResponse().throwRuntimeException();
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
            .responseMessage("Update user are not existed")
            .build()
        .withFailureResponse().throwRuntimeException();
    }

    public RoomsResponse findAllRooms(RoomsRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        boolean isAdmin = BaseService.hasAdminPermission(authorities);
        if (!isAdmin) {
            UserLoginDetails loginUser = (UserLoginDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
            request.setUserId(loginUser.getId());
        }

        List<Room> roomList = roomRepository.searchAllRooms(request);
        long total = roomRepository.countSearchAllRooms(request);

        List<RoomsResponse.RoomRecord> records = roomList
            .stream()
            .map(Room::convertToRoomsResponseRecord)
            .collect(Collectors.toList());

        return RoomsResponse.builder()
            .rooms(records)
            .page(request.getPage())
            .size(request.getSize())
            .total((int) total)
            .build();
    }

    public RoomDetailResponse createRoom(RoomDetailRequest request) throws IllegalAccessException {
        BaseService.checkAdminPermissionWithAction(CREATE);

        UserLoginDetails loginUser = (UserLoginDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        Room newRoom = new Room();
        newRoom.setCreateBy(loginUser.getId());
        newRoom.setCreateTime(now());
        newRoom.setUpdateTime(now());
        roomRepository.save(request.updateRoom(newRoom));

        return RoomDetailResponse.builder()
            .id(newRoom.getId())
            .build();
    }
}
