package com.ngcafai.QandA.async;

import com.alibaba.fastjson.JSONObject;
import com.ngcafai.QandA.Util.JedisAdapter;
import com.ngcafai.QandA.Util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by NgCafai on 2019/4/27 14:38.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * put the eventModel into a event queue
     * @param eventModel
     * @return
     */
    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
