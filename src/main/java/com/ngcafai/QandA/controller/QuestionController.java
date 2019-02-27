package com.ngcafai.QandA.controller;

import com.ngcafai.QandA.Util.QandAUtil;
import com.ngcafai.QandA.model.HostHolder;
import com.ngcafai.QandA.model.Question;
import com.ngcafai.QandA.service.QuestionService;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/question/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam String title,
                              @RequestParam String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            if(hostHolder.getUser() == null) {
                question.setUserId(QandAUtil.ANONYMOUS_USERID);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if (questionService.addQuestion(question) > 0) {
                return QandAUtil.getJSONString(0);  // 0 means having successfully added the question
            }

        } catch (Exception e) {
            logger.error("增加题目失败  " + e.getMessage());
        }
        return QandAUtil.getJSONString(0, "失败");
    }



}
