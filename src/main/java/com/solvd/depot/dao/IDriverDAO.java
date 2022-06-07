package com.solvd.depot.dao;

import com.solvd.depot.models.Driver;

public interface IDriverDAO extends IBaseDAO<Driver> {

    Driver getDriverByBusId(Long id);
}
