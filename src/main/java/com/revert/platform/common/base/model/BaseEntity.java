package com.revert.platform.common.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private Long id;

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    private String orderBy;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Long createBy;

    public void insertPre(){
        this.createDate = new Date();
    }


}
