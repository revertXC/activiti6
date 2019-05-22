package com.revert.xc.activitit.highImg;

import com.revert.xc.activitit.util.activiti.generatorImg.ActGeneratorImgUtils;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

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
        ActGeneratorImgUtils.getFlowImgByInstanceId(proInstanceId, response.getOutputStream(), Color.RED, Color.GREEN);
    }
}
