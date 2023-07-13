package com.tainika.qlnt.qlnt.model;

import com.tainika.qlnt.qlnt.dto.setting.room.RoomsResponse;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="room")
public class Room extends BaseModel{
    public String id;
    public String name;
    public String price;
    public Integer quantityPerson;
    public String garbagePrice;
    public String waterPrice;
    public String electricPrice;
    public String internetPrice;
    public Integer status;
    public boolean isDeleted;

    public RoomsResponse.RoomRecord convertToRoomsResponseRecord() {
        return RoomsResponse.RoomRecord.builder()
            .roomId(id)
            .name(name)
            .price(price)
            .quantityPerson(quantityPerson)
            .garbagePrice(garbagePrice)
            .waterPrice(waterPrice)
            .electricPrice(electricPrice)
            .internetPrice(internetPrice)
            .status(status)
            .build();
    }
}
