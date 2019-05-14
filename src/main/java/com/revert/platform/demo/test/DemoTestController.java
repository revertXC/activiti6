package com.revert.platform.demo.test;

import com.revert.platform.common.base.model.WebResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xiecong
 */
@RequestMapping("api/v1/demo/test")
@RestController
public class DemoTestController {

    @RequestMapping(method = RequestMethod.GET)
    public WebResult getData(){
        WebResult webResult = new WebResult();
        List<Map<String, String>> list = new ArrayList<>();
        for(int i=0; i<10 ;i++){
            Map<String, String> map = new HashMap<>();
            map.put("id",i+"");
            map.put("name",i+"name");
            map.put("price",i+"price");
            list.add(map);
        }
        webResult.setData(list);
        return webResult;
    }


}
