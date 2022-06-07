package com.solvd.depot.models;

public class Bus extends BaseEntity {
    private String name;
    private Type type;
    private Driver driver;
    private Model model;
    private Route route;
    private Depot depot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", driver=" + driver +
                ", model=" + model +
                ", route=" + route +
                ", depot=" + depot +
                '}';
    }
}
