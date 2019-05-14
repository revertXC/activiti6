package com.revert.xc.askForLeave.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revert.platform.common.base.model.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class AskForLeave extends BaseEntity {

    private String proInstanceId;

    private String taskId;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Integer day;

    private String approverName;

    private Integer approverResult;

    private String approverRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date approverDate;

    private String queryType;

    private List<String> proInstanceIds;
}