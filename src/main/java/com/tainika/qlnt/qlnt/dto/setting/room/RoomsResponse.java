package com.tainika.qlnt.qlnt.dto.setting.room;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomsResponse {
    private List<RoomRecord> rooms;
    private int page;
    private int size;
    private int total;

    @Data
    @Builder
    public static class RoomRecord {
        private String roomId;
        private String name;
        private String price;
        private Integer quantityPerson;
        private String garbagePrice;
        private String waterPrice;
        private String electricPrice;
        private String internetPrice;
        private Integer status;
    }
}
