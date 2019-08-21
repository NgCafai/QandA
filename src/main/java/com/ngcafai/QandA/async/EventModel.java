package com.ngcafai.QandA.async;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NgCafai on 2019/4/13 17:59.
 */
public class EventModel implements Serializable {
    private EventType type;
    private int actorId;  // the id of the user who activate this event
    private int entityType;
    private int entityId;
    private int entityOwnerId;  // the id of the target user

    // an extended field to store extra informatin
    private Map<String, String> exts = new HashMap<>();

    public EventModel() {

    }

    public EventModel setExt(String key, String value) {
        this.exts.put(key, value);
        return this;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
