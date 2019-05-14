package com.revert.xc.activitit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * xiecong
 */
@Service
public class InitService {

//    @Resource
//    ProcessEngine engineEngine ;//注入流程引擎

//    @PostConstruct
    private void InitProcessEngine(){
        /** spring 可以设置自动部署 流程文件*/
        // 部署流程文件
//        DeploymentBuilder builder = engineEngine.getRepositoryService().createDeployment();
//
//        Deployment deploy = builder.addClasspathResource("processes/Test.bpmn20.bpmn")
//                                    .addClasspathResource("processes/Test.bpmn20.png")
//                                    .deploy();
//
//        System.out.println("部署完成\n"+deploy.getId());
//        System.out.println("----------------");
    }


}
