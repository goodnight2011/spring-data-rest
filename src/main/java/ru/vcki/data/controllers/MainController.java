package ru.vcki.data.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello!!!";
    }
}
