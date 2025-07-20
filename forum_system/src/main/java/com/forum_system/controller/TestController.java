package com.forum_system.controller;

import com.forum_system.exception.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/exception")
    public String testException() throws Exception {
        throw new Exception("这是一个Exception");
    }



    @RequestMapping("/appexception")
    public String testApplicationException() {
        throw new ApplicationException("这是一个自定义的ApplicationException");
    }


    @RequestMapping("/testAPI")
    @Operation(summary = "测试自动生成API接口",description = "肥波最帅")
    public String testAPI() {
        return "肥波是我儿";
    }

}
