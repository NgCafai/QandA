package com.ngcafai.QandA.controller;

import com.ngcafai.QandA.service.QandAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SettingController {
    @Autowired
    QandAService qandAService;

    @RequestMapping(path="/setting", method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession){
        return "Setting OK.  " + qandAService.getMessage(1);
    }
}
