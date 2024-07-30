package com.fit2081.assignment12081;

public class Header {

    private String id;
    private String name;
    private String eventCount;
    private String isActive;


    public Header(String id, String name, String eventCount, String isActive) {
        this.id = id;
        this.name = name;
        this.eventCount = eventCount;
        this.isActive = isActive;
    }

    public String getTheId() {
        return id;
    }

    public String getTheName() {
        return name;
    }

    public String getTheEventCount() {
        return eventCount;
    }

    public String getTheIsActive() {
        return isActive;
    }
}
