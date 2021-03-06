package com.ngcafai.QandA;

import com.ngcafai.QandA.dao.QuestionDAO;
import com.ngcafai.QandA.dao.UserDAO;
import com.ngcafai.QandA.model.Message;
import com.ngcafai.QandA.model.Question;
import com.ngcafai.QandA.model.User;
import com.ngcafai.QandA.service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QandAApplication.class)
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
	UserDAO userDAO;

	@Autowired
	QuestionDAO questionDAO;

	@Autowired
	MessageService messageService;

	@Test
	public void messageTest() {
		Message msg = new Message();

		msg.setCreatedDate(new Date());
		//msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));

		Assert.assertEquals(1, messageService.addMessage(msg));
	}

	/*
	@Test
	public void contextLoads() {
		Random random = new Random();
		for (int i = 1; i < 11; i++){
			User user = new User();
			user.setHeadUrl(String.format("http://img.ivsky.com/img/tupian/pre/201808/31/senlin-%03d.jpg", 1 + new Random().nextInt(16)));
			user.setName(String.format("USER%d", i));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);

			user.setPassword("newpassword");
			userDAO.updatePassword(user);

			Question question = new Question();
			question.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
			question.setCreatedDate(date);
			question.setUserId(i);
			question.setTitle(String.format("title %d", i));
			question.setContent(String.format("Balaababalalalal Content %d", i));
			questionDAO.addQuestion(question);

		}
		Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
		userDAO.deleteById(1);
		Assert.assertNull(userDAO.selectById(1));
		System.out.println(questionDAO.selectLatestQuestions(0, 0, 5));
	}
    */


}

