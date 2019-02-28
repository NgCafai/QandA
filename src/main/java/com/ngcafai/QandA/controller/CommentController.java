package com.ngcafai.QandA.controller;

import com.ngcafai.QandA.Util.QandAUtil;
import com.ngcafai.QandA.model.Comment;
import com.ngcafai.QandA.model.EntityType;
import com.ngcafai.QandA.model.HostHolder;
import com.ngcafai.QandA.service.CommentService;
import com.ngcafai.QandA.service.QuestionService;
import com.ngcafai.QandA.service.SensitiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensitiveService sensitiveService;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addCommentForQuestion(@RequestParam("questionId") int questionId,
                                        @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);

            Comment comment = new Comment();
            if(hostHolder.getUser() == null) {
                comment.setUserId(QandAUtil.ANONYMOUS_USERID);
            } else{
                comment.setUserId(hostHolder.getUser().getId());
            }
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);

            commentService.addComment(comment);

            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);

        } catch (Exception e){
            logger.error("fail to add new comment  " + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
