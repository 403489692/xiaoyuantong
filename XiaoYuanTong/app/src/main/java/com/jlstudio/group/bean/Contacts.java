package com.jlstudio.group.bean;

public class Contacts {
    private String name;
    private String pinYinName;

    public Contacts(String name) {
        super();
        this.name = name;
    }

    public Contacts(String name, String pinYinName) {
        super();
        this.name = name;
        this.pinYinName = pinYinName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinYinName() {
        return pinYinName;
    }

    public void setPinYinName(String pinYinName) {
        this.pinYinName = pinYinName;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "name='" + name + '\'' +
                ", pinYinName='" + pinYinName + '\'' +
                '}';
    }
}
