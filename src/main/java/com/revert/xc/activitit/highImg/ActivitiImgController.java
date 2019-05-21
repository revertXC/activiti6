package com.revert.xc.activitit.highImg;

import com.revert.xc.activitit.util.ActUtils;
import jdk.internal.util.xml.impl.Input;
import lombok.extern.log4j.Log4j2;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * xiecong
 */
@Log4j2
@RestController
@RequestMapping("/api/v1/activitiImg")
public class ActivitiImgController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    HistoryService historyService;
    @Autowired
    TaskService taskService;


    @GetMapping
    public void getImg(@RequestParam("proInstanceId") String proInstanceId, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(proInstanceId)){
            return;
        }
        //下面的代码生成的图片有 线文字的偏移情况 未找到问题
//        InputStream imageStream = getResourceDiagramInputStream(proInstanceId);
//        InputStream imageStream = getTest(proInstanceId);
//        // 输出资源内容到相应对象
//        byte[] b = new byte[1024];
//        int len;
//        while ((len = imageStream.read(b, 0, 1024)) != -1) {
//            response.getOutputStream().write(b, 0, len);
//        }

        //使用这个生成图片 自定义的
        ActUtils.getFlowImgByInstanceId(proInstanceId, response.getOutputStream(), Color.blue, Color.GREEN);
    }
}
