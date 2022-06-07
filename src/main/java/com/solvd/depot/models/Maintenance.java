package com.solvd.depot.models;

import java.sql.Date;

public class Maintenance extends BaseEntity {
    private Date date;
    private Mechanic mechanic;
    private Service service;
    private Bus bus;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "date=" + date +
                ", mechanic=" + mechanic +
                ", service=" + service +
                ", bus=" + bus +
                '}';
    }
}
