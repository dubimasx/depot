package com.solvd.depot.models;

import java.util.List;

public class Model extends BaseEntity{
    private String name;
    private List<Bus> buses;

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", buses=" + buses +
                '}';
    }
}
