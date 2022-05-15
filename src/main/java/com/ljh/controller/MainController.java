package com.ljh.controller;

import com.ljh.config.annotion.TokenValidatorAnnotaion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    @TokenValidatorAnnotaion(required = false)
    public String run(){
        return "SIMPLE-BOOT";
    }

}
