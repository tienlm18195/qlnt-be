package com.tainika.qlnt.qlnt.model;

import lombok.Data;

@Data
public class Bill extends BaseModel {
    public String id;
    public String userId;
    public String userName;
    public String roomId;
    public String roomName;
    public Integer electricNumber;
    public String electricPrice;
    public Integer waterNumber;
    public String waterPrice;
    public String internetPrice;
    public String garbagePrice;
    public String roomPrice;
    public Integer total;
    public Integer isPaid;
    public String note;
    public Integer status;
}
