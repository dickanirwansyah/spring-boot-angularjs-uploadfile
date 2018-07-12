package com.spring.app.springangularjsupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class ControllerMain {

    @GetMapping
    public String getPageUpload(ModelMap map){
        map.addAttribute("title", "upload file");
        return "upload";
    }

}
