package com.solvd.depot.models;

import java.util.List;

public class Route extends BaseEntity {
    private String name;
    private List<Station> stations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", stations=" + stations +
                '}';
    }
}
