package com.ngcafai.QandA.dao;

import com.ngcafai.QandA.model.Comment;
import com.ngcafai.QandA.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " (user_id, content, created_date, entity_id, entity_type, status) ";
    String SELECT_FIELDS = " id, user_id, content, created_date, entity_id, entity_type, status ";

    @Insert({"insert into ", TABLE_NAME, INSERT_FIELDS, " values (#{userId}, #{content}, " +
            "#{createdDate}, #{entityId}, #{entityType}, #{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, "from", TABLE_NAME,
            "where entity_id = #{entityId} and entity_type = #{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from", TABLE_NAME,
            "where entity_id = #{entityId} and entity_type = #{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
}
