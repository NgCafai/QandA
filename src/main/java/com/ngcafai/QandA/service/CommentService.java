package com.ngcafai.QandA.service;

import com.ngcafai.QandA.dao.CommentDAO;
import com.ngcafai.QandA.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public List<Comment> getCommentsByEntity(int entityId, int entityTpye) {
        return commentDAO.selectByEntity(entityId, entityTpye);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

}
