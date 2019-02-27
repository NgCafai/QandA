package com.ngcafai.QandA.service;

import org.springframework.stereotype.Service;

@Service
public class QandAService {
    public String getMessage(int userId){
        return "hello message: " + userId;
    }
}
