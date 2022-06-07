package com.solvd.depot.models;

import java.util.List;

public class Category extends BaseEntity{
    private String name;
    public List<Licence> licences;

    public List<Licence> getLicences() {
        return licences;
    }

    public void setLicences(List<Licence> licences) {
        this.licences = licences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", licences=" + licences +
                '}';
    }
}
