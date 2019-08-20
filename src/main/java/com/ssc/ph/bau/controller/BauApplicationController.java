package com.ssc.ph.bau.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class BauApplicationController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
