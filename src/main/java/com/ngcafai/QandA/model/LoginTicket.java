package com.ngcafai.QandA.model;

import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * An instance of this class is used to record the login of a user. Every time
 * a user logins, a new instance will be created. The ticket field can be added
 * to the cookie.
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired; // java.util.Date corresponds to the DATETIME in MySQL
    private int status;  // 0: valid; 1: invalid
    private String ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpiered(Date expiered) {
        this.expired = expiered;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
