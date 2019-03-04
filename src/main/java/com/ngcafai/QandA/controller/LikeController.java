package com.ngcafai.QandA.controller;

import com.ngcafai.QandA.Util.QandAUtil;
import com.ngcafai.QandA.model.EntityType;
import com.ngcafai.QandA.model.HostHolder;
import com.ngcafai.QandA.service.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    public static Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String likeComment(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return QandAUtil.getJSONString(999);
        }
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return QandAUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislikeComment(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return QandAUtil.getJSONString(999);
        }
        long likeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return QandAUtil.getJSONString(0, String.valueOf(likeCount));
    }


}
