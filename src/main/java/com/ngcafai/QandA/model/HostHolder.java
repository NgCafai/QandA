package com.ngcafai.QandA.model;

import org.springframework.stereotype.Component;

/**
 * This class is used to bind the current user to the current thread
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        this.users.set(user);
    }

    public void remove() {
        this.users.remove();
    }
}
