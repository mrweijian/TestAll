package com.web.controller;


import com.web.annotaion.Controller;
import com.web.annotaion.RequestMapping;
import com.web.annotaion.ResponseBody;
import com.web.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/getSpringMvc")
    @ResponseBody
    public String getSpringMvc(String uname, HttpServletRequest request,
                               HttpServletResponse response, UserEntity entity){

        System.out.println(uname);
        System.out.println(request);
        System.out.println(response);
        System.out.println(entity);
        return "SUCCESS";
    }

    @RequestMapping("/test")
    public String test(UserEntity userEntity){
        return "test";
    }

}
