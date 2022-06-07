package com.solvd.depot.models;

import java.util.List;

public class Driver extends BaseEntity{
    private String name;
    private Integer age;
    private Licence licence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", licence=" + licence +
                "} " + super.toString();
    }
}
