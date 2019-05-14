package com.revert.test;

import com.revert.xc.askForLeave.model.AskForLeave;
import com.revert.xc.askForLeave.service.AskForLeaveService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.impl.cmd.DeleteTaskCmd;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * xiecong
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AskForLeaveService askForLeaveService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ManagementService managementService;

    @org.junit.Test
    public void startProInstance(){
        String proInstanceId = null;
        String taskId = null;

        //创建流程实例，Test 为 processes 文件里面的 BPMN文件前缀名
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test");
        proInstanceId = processInstance.getId();
        //流程实例启动后，流程会跳转到请假申请节点
        Task vacationApply = taskService.createTaskQuery().processInstanceId(proInstanceId).singleResult();
        taskId = vacationApply.getId();
        //设置请假申请任务的执行人
        taskService.setAssignee(taskId, "张三");

        AskForLeave askForLeave = new AskForLeave();
        askForLeave.setProInstanceId(proInstanceId);
        askForLeave.setTaskId(taskId);
        askForLeave.setName("请假人007-特殊权限");
        askForLeave.setDay(5);
        askForLeave.setStartDate(new Date());
        askForLeave.setCreateDate(new Date());

        askForLeaveService.saveNotNull(askForLeave);
        System.out.println("实列ID："+proInstanceId + "  任务ID："+taskId);

    }

    Task getTaskByProInstanceId(String id){
        return taskService.createTaskQuery().processInstanceId(id).singleResult();
    }

    /**
     * 提交第一节点
     */
    @org.junit.Test
    public void completeNode_1(){
        String taskId = "45005";
        AskForLeave askForLeave = new AskForLeave();
        askForLeave.setTaskId(taskId);
        List<AskForLeave> askForLeaves = askForLeaveService.selectByProperties(askForLeave);
        if(askForLeaves.isEmpty()) return;

        askForLeave = askForLeaves.get(0);
        //流程实例启动后，流程会跳转到请假申请节点
        Task task = getTaskByProInstanceId(askForLeave.getProInstanceId());
        System.out.println("【提交之前】当前节点名称："+task.getName());

        //提交到下个节点，带参数
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("day", askForLeave.getDay());
        taskService.complete(taskId, paramsMap);

        //流程实例启动后，流程会跳转到请假申请节点
        task = getTaskByProInstanceId(askForLeave.getProInstanceId());
        System.out.println("【提交之后】当前节点名称："+task.getName() + "新的 taskID："+task.getId());

    }


    //https://segmentfault.com/a/1190000013952695
    /**
     * 节点跳过
     */
    @org.junit.Test
    public void skipNode(){
        String taskId = "47505";

        //当前任务
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        System.out.println("【提交之前】当前节点名称："+currentTask.getName());
        //获取流程定义
        Process process = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();

        //获取目标节点定义
        FlowNode targetNode = (FlowNode)process.getFlowElement("sid-25EC8283-5733-4452-B451-C219BC0CD6EA");

        //删除当前运行任务
        String executionEntityId = managementService.executeCommand(new DeleteTaskCmd(currentTask.getId()));
        System.out.println(executionEntityId);
        //流程执行到来源节点
        managementService.executeCommand(new SetFLowNodeAndGoCmd(targetNode, executionEntityId));

        currentTask = getTaskByProInstanceId(currentTask.getProcessInstanceId());
        System.out.println("【提交之后】当前节点名称："+currentTask.getName());
    }



    //删除当前运行时任务命令，并返回当前任务的执行对象id
    //这里继承了NeedsActiveTaskCmd，主要时很多跳转业务场景下，要求不能时挂起任务。可以直接继承Command即可
    public class DeleteTaskCmd extends NeedsActiveTaskCmd<String> {
        public DeleteTaskCmd(String taskId){
            super(taskId);
        }
        public String execute(CommandContext commandContext, TaskEntity currentTask){
            //获取所需服务
            TaskEntityManagerImpl taskEntityManager = (TaskEntityManagerImpl)commandContext.getTaskEntityManager();
            //获取当前任务的来源任务及来源节点信息
            ExecutionEntity executionEntity = currentTask.getExecution();
            //删除当前任务,来源任务
            taskEntityManager.deleteTask(currentTask, "jumpReason", false, false);
            return executionEntity.getId();
        }
        public String getSuspendedTaskException() {
            return "挂起的任务不能跳转";
        }
    }

}
