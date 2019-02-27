package com.ngcafai.QandA.service;

import com.ngcafai.QandA.Util.QandAUtil;
import com.ngcafai.QandA.dao.LoginTicketDAO;
import com.ngcafai.QandA.dao.UserDAO;
import com.ngcafai.QandA.model.LoginTicket;
import com.ngcafai.QandA.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();

        // check whether the new username and the password satisfy certain rules
        if(StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空或者包含空格");
            return map;
        }

        if(StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空或者包含空格");
            return map;
        }

        User user = userDAO.selectByName(username);
        if(user != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }

        if(password.length() < 6) {
            map.put("msg", "密码长度最小为6位");
            return map;
        }

        /*
        create the new user; use salt and the provided password to generate new passsword;
        then add the user to the database
        */
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://img.ivsky.com/img/tupian/pre/201808/31/senlin-%03d.jpg", 1 + new Random().nextInt(16));
        user.setHeadUrl(head);
        user.setPassword(QandAUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);

        // login as the new user;
        String ticket = addLoginTicket(user.getId());
        /* record the ticket for this login;
        the ticket can be later added as a cookie into the httpServletResponse Object */
        map.put("ticket", ticket);

        return map;
    }

    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();

        // check whether the new username and the password satisfy certain rules
        if(StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空或者包含空格");
            return map;
        }

        if(StringUtils.isBlank(password)) {
            map.put("msg", "密码错误");
            return map;
        }

        User user = userDAO.selectByName(username);
        if(user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if(!QandAUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }

        // login as the corresponding user;
        String ticket = addLoginTicket(user.getId());
        /* record the ticket for this login;
        the ticket can be later added as a cookie into the httpServletResponse Object */
        map.put("ticket", ticket);
        return map;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    // generate a LoginTicket instance for a user who just logins, and add the instance to the database
    private String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24 * 7);  // the ticket will expire after 7 days
        loginTicket.setExpiered(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
