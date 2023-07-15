package com.tainika.qlnt.qlnt.dto.setting.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomsRequest {
    private String userId;
    private String name;
    private String priceFrom;
    private String priceTo;
    private String quantityPersonFrom;
    private String quantityPersonTo;
    private int status;
    private boolean isDeleted;
    private int page;
    private int size;
}
