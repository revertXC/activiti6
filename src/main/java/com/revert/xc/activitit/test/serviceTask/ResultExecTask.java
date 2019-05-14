package com.revert.xc.activitit.test.serviceTask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
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
@Component
public class ResultExecTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("=============== 请假流程 结果处理  ====================");
        String id = delegateExecution.getId();
        String proId = delegateExecution.getProcessDefinitionId();
        Set<String> set = delegateExecution.getVariableNames();
        System.out.println("id:"+id+" proId:"+proId+"  varArrays:"+ Arrays.toString(set.toArray()));
    }
}
