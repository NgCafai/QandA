package com.ngcafai.QandA.controller;

import com.ngcafai.QandA.Util.QandAUtil;
import com.ngcafai.QandA.model.*;
import com.ngcafai.QandA.service.CommentService;
import com.ngcafai.QandA.service.QuestionService;
import com.ngcafai.QandA.service.UserService;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

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
        return QandAUtil.getJSONString(1, "失败");
    }

    @RequestMapping(path = "/question/{id}", method = RequestMethod.GET)
    public String questionDetail(Model model, @PathVariable("id") int id) {
        Question question = questionService.selectById(id);
        model.addAttribute("question", question);
        model.addAttribute("user", userService.getUser(question.getUserId()));

        List<Comment> commentList = commentService.getCommentsByEntity(id, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("comment", comment);
            viewObject.set("user", userService.getUser(comment.getUserId()));
            comments.add(viewObject);
        }
        model.addAttribute("comments", comments);

        return "detail";

    }


}
