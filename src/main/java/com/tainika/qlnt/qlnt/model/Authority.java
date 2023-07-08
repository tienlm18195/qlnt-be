package com.tainika.qlnt.qlnt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection="authority")
public class Authority extends BaseModel {
    @Id
    private String id;
    private String name;
    @Indexed
    private String code;

    public Authority(String code) {
        this.code = code;
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
    }
}
