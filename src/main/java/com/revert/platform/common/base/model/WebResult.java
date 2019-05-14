package com.revert.platform.common.base.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebResult implements Serializable {

    private String code = "2000";

    private String message = "ok";

    private Object data;


}
