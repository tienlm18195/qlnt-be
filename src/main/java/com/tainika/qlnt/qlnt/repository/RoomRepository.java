package com.tainika.qlnt.qlnt.repository;

import com.tainika.qlnt.qlnt.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String>, RoomRepositoryCustom {
}
