package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController  {

    @RequestMapping("/test.do")
    @ResponseBody                                                   //user 对象  Map
    public Object test(String name, HttpServletRequest request, HttpServletResponse response){
        System.out.println("调用了");
        request.getParameter("name");
        Map hashMap = new HashMap();
        hashMap.put("key","value");
        return  hashMap;
    }

    @RequestMapping("/model.do")
    public String model(HttpServletRequest request,HttpServletResponse response){
        return "index";
    }

}
