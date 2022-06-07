package com.solvd.depot.dao;

import com.solvd.depot.models.Route;

public interface IRouteDAO extends IBaseDAO<Route> {
    Route getRouteByBusId (Long id);
}
