package com.revert.platform.common.constant;

import lombok.Data;

import java.io.Serializable;

public class StaticsData implements Serializable {

    /**
     * 异常code
     *  2000:正常
     *  5000:异常
     */
    public final static String CODE_SUCCESS = "2000";
    public final static String CODE_ERROR = "5000";

    public final static String MSG_ERROR = "系统繁忙，请稍后再试";



}
