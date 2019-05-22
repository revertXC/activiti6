package com.revert.xc.askForLeave.service;

import com.revert.platform.common.base.service.BaseService;
import com.revert.xc.askForLeave.mapper.AskForLeaveMapper;
import com.revert.xc.askForLeave.model.AskForLeave;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * xiecong
 */
@Service
public class AskForLeaveService extends BaseService<AskForLeaveMapper, AskForLeave> {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ManagementService managementService;

    @Override
    public List<AskForLeave> selectByProperties(AskForLeave askForLeave) {
        if(askForLeave.getQueryType() != null){
            List<String> proInstanceIds = new ArrayList<>();
            List<Task> list = taskService.createTaskQuery().taskDefinitionKey(askForLeave.getQueryType()).orderByTaskId().desc().list();
            list.forEach(Task -> {
                proInstanceIds.add(Task.getProcessInstanceId());
            });
            if(proInstanceIds.size() == 0){
                return new ArrayList<>();
            }
            askForLeave.setProInstanceIds(proInstanceIds);
        }
        return super.selectByProperties(askForLeave);
    }

    //新增请假单
    public Map<String, Object> saveAskForLeave(AskForLeave askForLeave){
        String proInstanceId = null;
        String taskId = null;

        //创建流程实例，Test 为 processes 文件里面的 BPMN文件前缀名
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test");
        proInstanceId = processInstance.getId();
        //流程实例启动后，流程会跳转到请假申请节点
        Task vacationApply = taskService.createTaskQuery().processInstanceId(proInstanceId).singleResult();
        taskId = vacationApply.getId();
        //设置请假申请任务的执行人
        taskService.setAssignee(taskId, "办理人-张三");

        askForLeave.setProInstanceId(proInstanceId);
        askForLeave.setTaskId(taskId);

        //保存请假信息
        this.saveNotNull(askForLeave);

        //提交到下个节点，发出请假
        taskService.complete(taskId);


        Map<String, Object> res = new HashMap<>();
        res.put("proInstanceId", proInstanceId);
        res.put("name",askForLeave.getName());
        return res;
    }

    //获取节点名称 通过 实例ID
    public String queryTaskNameByProInstanceId(String proInstanceId){
        Task task = getTaskByProInstanceId(proInstanceId);
        return task.getName();
    }


    public String submitApplyForComplete(Long id){
        AskForLeave askForLeave = this.mapper.selectByPrimaryKey(id);
        if(askForLeave != null){
            //提交到下个节点，带参数
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("day", askForLeave.getDay());
            Task task = getTaskByProInstanceId(askForLeave.getProInstanceId());
            taskService.complete(task.getId(), paramsMap);
            task = getTaskByProInstanceId(askForLeave.getProInstanceId());
            AskForLeave up = new AskForLeave();
            up.setId(askForLeave.getId());
            up.setTaskId(task.getId());
            this.mapper.updateByPrimaryKeySelective(up);
            return "ok";
        }else{
            return "序号："+id+"在库中不存在";
        }
    }

    //同意
    public String AFLeaveAgree(AskForLeave askForLeave){
        askForLeave.setApproverDate(new Date());
        Task task = getTaskByProInstanceId(askForLeave.getProInstanceId());
        taskService.complete(task.getId());
        this.mapper.updateByPrimaryKeySelective(askForLeave);
        return "ok";
    }



    public void updataByProInstanceId(AskForLeave askForLeave){
        this.mapper.updataByProInstanceId(askForLeave);
    }

    //cacheNames 缓存区间， key 缓存唯一标识
    @Cacheable(cacheNames = "node", key = "#processDefinitionId")
    public List<Map<String, String>> getAllNode(String processDefinitionId){
        System.out.println("==================查询一次");
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        List<Map<String, String>> listEleIds = null;
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            listEleIds = new ArrayList<>(flowElements.size()+5);
            for (FlowElement e : flowElements) {
                if(e instanceof UserTask){
                    Map<String, String> map = new HashMap<>();
                    map.put("id", e.getId());
                    map.put("name", e.getName());
                    listEleIds.add(map);
                }
            }
        }
        return listEleIds;
    }


    private Task getTaskByProInstanceId(String id){
        return taskService.createTaskQuery().processInstanceId(id).singleResult();
    }
}
