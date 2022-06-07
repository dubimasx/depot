package com.solvd.depot.models;

import java.sql.Date;
import java.util.List;

public class Licence extends BaseEntity {
    private Date expiration;
    private List<Category> categories;

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Licence{" +
                "expiration=" + expiration +
                ", categories=" + categories +
                '}';
    }
}
