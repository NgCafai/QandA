package com.ngcafai.QandA.controller;

import com.ngcafai.QandA.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//@Controller
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "hello";
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam("type") int type,
                          @RequestParam("key") String key){
        return String.format("Profile Page of %s %d, type: %d, key: %s", groupId, userId, type, key);
    }

    @RequestMapping(path="/vm", method={RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1", "vvvv1");
        List<String> colors = Arrays.asList("red", "green", "blue");
        model.addAttribute("colors", colors);

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i++){
            map.put(String.valueOf(i), String.valueOf(Math.pow(i, 3)));
        }
        model.addAttribute("map", map);
        model.addAttribute("user", new User("Lee"));
        return "home";
    }

    @RequestMapping(path="/request")
    @ResponseBody
    public String request(Model model,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ": " + request.getHeader(name) + "<br>");
        }

        sb.append(request.getMethod() + "<br>");
        sb.append(request.getRequestURL() + "<br>");
        return sb.toString();

    }

    @RequestMapping(path="/redirect/{code}", method={RequestMethod.GET})
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession){
        return "redirect:/";
    }

    @RequestMapping(path="/admin", method={RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if ("123456".equals(key)){
            return "access allowed";
        }
        throw new IllegalArgumentException("password is wrong");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error: " + e.getMessage();
    }




}

