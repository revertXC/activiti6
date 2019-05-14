package com.revert.platform.common.base.controller;

import com.revert.platform.common.base.model.WebResult;
import com.revert.platform.common.constant.StaticsData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class BaseControllerExceptionHandler {

    private static final WebResult defaultResult = new WebResult();
    static {
        defaultResult.setCode(StaticsData.CODE_ERROR);
        defaultResult.setData(null);
        defaultResult.setMessage(StaticsData.MSG_ERROR);
    }

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public WebResult handleException(Exception e){
        log.error(ExceptionUtils.getStackTrace(e));
        return defaultResult;
    }



}
