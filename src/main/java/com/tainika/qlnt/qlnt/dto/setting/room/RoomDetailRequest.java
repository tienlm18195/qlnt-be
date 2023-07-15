package com.tainika.qlnt.qlnt.dto.setting.room;

import com.tainika.qlnt.qlnt.model.Room;
import lombok.Data;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
public class RoomDetailRequest {
    private String userId;
    private String name;
    private String price;
    private int quantityPerson;
    private String garbagePrice;
    private String waterPrice;
    private String electricPrice;
    private String internetPrice;
    private int status;
    private boolean deleted;

    public Room updateRoom(Room room) {
        if (isNotBlank(userId)) room.setUserId(userId);

        if (isNotBlank(name)) room.setName(name);

        if (isNotBlank(price)) room.setPrice(price);

        if (quantityPerson > 0)  room.setQuantityPerson(quantityPerson);

        if (isNotBlank(garbagePrice)) room.setGarbagePrice(garbagePrice);

        if (isNotBlank(waterPrice)) room.setWaterPrice(waterPrice);

        if (isNotBlank(electricPrice)) room.setElectricPrice(electricPrice);

        if (isNotBlank(internetPrice)) room.setInternetPrice(internetPrice);

        room.setStatus(status);
        room.setDeleted(deleted);
        return room;
    }
}
