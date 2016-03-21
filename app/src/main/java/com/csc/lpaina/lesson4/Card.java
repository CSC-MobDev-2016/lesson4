package com.csc.lpaina.lesson4;

public class Card {
    private String name, info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Card(String name, String info) {
        this.name = name;
        this.info = info;
    }
}
