package com.tainika.qlnt.qlnt.model;

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
}
