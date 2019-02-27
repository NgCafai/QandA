package com.ngcafai.QandA.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object o){
        objs.put(key, o);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
