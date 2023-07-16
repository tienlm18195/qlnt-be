package com.tainika.qlnt.qlnt.model;

import com.tainika.qlnt.qlnt.dto.setting.room.RoomsResponse;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="room")
public class Room extends BaseModel{
    private String id;
    @Indexed
    private String userId;
    private String name;
    private String price;
    private Integer quantityPerson;
    private String garbagePrice;
    private String waterPrice;
    private String electricPrice;
    private String internetPrice;
    private Integer status;

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
