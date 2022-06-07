package com.solvd.depot.services;

import com.solvd.depot.dao.jdbcMySqlImpl.RouteDAO;
import com.solvd.depot.dao.jdbcMySqlImpl.StationDAO;
import com.solvd.depot.models.Route;

public class RouteService {
    public Route getRouteById(Long id) {

        Route route = new Route();
        RouteDAO routeDAO = new RouteDAO();
        StationDAO stationDAO = new StationDAO();
        route.setName(routeDAO.getEntityById(id).getName());
        route.setStations(stationDAO.getAllStationsByRouteId(id));
        return route;
    }
}
