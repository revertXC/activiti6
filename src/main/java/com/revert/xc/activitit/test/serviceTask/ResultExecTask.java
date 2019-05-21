package com.revert.xc.activitit.test.serviceTask;

import com.revert.xc.activitit.util.SpringContextUtils;
import com.revert.xc.askForLeave.model.AskForLeave;
import com.revert.xc.askForLeave.service.AskForLeaveService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

/**
 * serviceTask 流程标签处理
 * Delegate 委派
 * xiecong
 * Test流程图 结果自动处理处
 */
public class ResultExecTask implements JavaDelegate {

    private AskForLeaveService askForLeaveService = SpringContextUtils.getBean(AskForLeaveService.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("=============== 请假流程 结果处理  ====================");
        String id = delegateExecution.getId();
        String proId = delegateExecution.getProcessDefinitionId();
        Set<String> set = delegateExecution.getVariableNames();
        System.out.println("id:"+id+" proId:"+proId+"  varArrays:"+ Arrays.toString(set.toArray()));


        String processInstanceId = delegateExecution.getProcessInstanceId();

        AskForLeave askForLeave = new AskForLeave();
        askForLeave.setProInstanceId(processInstanceId);
        askForLeave.setApproverRemarkAppend("  ;[ResultExecTask]自动处理类型");
        askForLeaveService.updataByProInstanceId(askForLeave);
    }
}
