package com.revert.xc.askForLeave.mapper;

import com.revert.platform.common.annotation.MasterMySqlDao;
import com.revert.platform.common.base.mapper.BaseMapper;
import com.revert.xc.askForLeave.model.AskForLeave;
import org.apache.ibatis.annotations.Param;

@MasterMySqlDao
public interface AskForLeaveMapper extends BaseMapper<AskForLeave> {


    void updataByProInstanceId(AskForLeave askForLeave);
}