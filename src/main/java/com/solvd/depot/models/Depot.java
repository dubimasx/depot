package com.solvd.depot.models;

import java.util.List;

public class Depot extends BaseEntity {
    private String name;
    private Manager manager;
    private List<Bus> buses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    @Override
    public String toString() {
        return "Depot{" +
                "name='" + name + '\'' +
                ", manager=" + manager +
                ", buses=" + buses +
                "} " + super.toString();
    }
}
