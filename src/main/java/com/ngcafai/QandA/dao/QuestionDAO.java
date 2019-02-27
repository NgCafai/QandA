package com.ngcafai.QandA.dao;

import com.ngcafai.QandA.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " (title, content, created_date, user_id, comment_count) ";
    String SELECT_FIELDS = " id, title, content, created_date, user_id, comment_count ";

    @Insert({"insert into ", TABLE_NAME, INSERT_FIELDS, " values (#{title}, #{content}, " +
            "#{createdDate}, #{userId}, #{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

}
