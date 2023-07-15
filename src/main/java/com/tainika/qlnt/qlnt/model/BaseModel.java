package com.tainika.qlnt.qlnt.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
public class BaseModel {
    private String createBy;
    private String updateBy;
    private boolean isDeleted;
    @CreatedDate
    private Date createTime;
    private Date updateTime;
}
