package com.tainika.qlnt.qlnt.model;

import lombok.Data;
import java.util.Date;

@Data
public class BaseModel {
    private String createBy;
    private String updateBy;
    private boolean isDeleted;
    private Date createTime;
    private Date updateTime;
}
