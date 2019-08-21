package com.ngcafai.QandA.async;

/**
 * Created by NgCafai on 2019/4/13 17:48.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    // for testing
    public static void main(String[] args) {
        EventType type = EventType.COMMENT;
        System.out.print(type.getValue());
    }
}
