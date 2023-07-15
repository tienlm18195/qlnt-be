package com.tainika.qlnt.qlnt.dto.setting.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDetailResponse {
    private String id;
    private String name;
    private String price;
    private String quantityPerson;
    private String garbagePrice;
    private String waterPrice;
    private String electricPrice;
    private String internetPrice;
    private String status;
}
