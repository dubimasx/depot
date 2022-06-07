package com.solvd.depot.services;

import com.solvd.depot.dao.jdbcMySqlImpl.*;
import com.solvd.depot.models.*;

public class BusService {
    public Bus getBusById(Long id){
        BusDAO busDAO = new BusDAO();
        TypeDAO typeDAO = new TypeDAO();
        ModelDAO modelDAO = new ModelDAO();
        RouteDAO routeDAO = new RouteDAO();
        DriverDAO driverDAO = new DriverDAO();
        DepotDAO depotDAO = new DepotDAO();
        Type type = new Type();
        Model model = new Model();
        Route route = new Route();
        Driver driver = new Driver();
        Depot depot = new Depot();
        Bus bus = new Bus();
        bus = busDAO.getEntityById(1L);
        type.setName(typeDAO.getTypeByBusId(id).getName());
        bus.setType(type);
        model.setName(modelDAO.getModelByBusId(id).getName());
        bus.setModel(model);
        route.setName(routeDAO.getRouteByBusId(id).getName());
        bus.setRoute(route);
        driver.setName(driverDAO.getDriverByBusId(id).getName());
        bus.setDriver(driver);
        depot.setName(depotDAO.getDepotByBusId(id).getName());
        bus.setDepot(depot);

        return bus;
    }
}
