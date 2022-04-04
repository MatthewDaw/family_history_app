package com.example.mygeoquiz.struct;

public class recyclerViewData {

    private String id;
    private String type;

    public recyclerViewData(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
