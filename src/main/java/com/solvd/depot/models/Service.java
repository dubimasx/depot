package com.solvd.depot.models;

public class Service extends BaseEntity{
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Service{" +
                "type='" + type + '\'' +
                '}';
    }
}
