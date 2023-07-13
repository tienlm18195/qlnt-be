package com.tainika.qlnt.qlnt.repository;

import com.tainika.qlnt.qlnt.dto.setting.room.RoomsRequest;
import com.tainika.qlnt.qlnt.model.Room;

import java.util.List;

public interface RoomRepositoryCustom {
    List<Room> searchAllRooms(RoomsRequest request);
    long countSearchAllRooms(RoomsRequest request);
}
