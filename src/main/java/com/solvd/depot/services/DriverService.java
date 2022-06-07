package com.solvd.depot.services;

import com.solvd.depot.dao.jdbcMySqlImpl.CategoryDAO;
import com.solvd.depot.dao.jdbcMySqlImpl.DriverDAO;
import com.solvd.depot.dao.jdbcMySqlImpl.LicenceDAO;
import com.solvd.depot.dao.jdbcMySqlImpl.RouteDAO;
import com.solvd.depot.models.Driver;
import com.solvd.depot.models.Licence;
import com.solvd.depot.models.Route;
import com.solvd.depot.models.Station;

import java.util.List;

public class DriverService {
    public Driver getDriverById(Long id) {

        CategoryDAO categoryDAO = new CategoryDAO();
        LicenceDAO licenceDAO = new LicenceDAO();
        DriverDAO driverDAO = new DriverDAO();
        Licence licence = new Licence();
        Driver driver = driverDAO.getEntityById(id);
        licence.setCategories(categoryDAO.getAllCategoriesByLicenceId(id));
        licence.setExpiration(licenceDAO.getLicenceByDriverId(id).getExpiration());
        driver.setLicence(licence);
        return driver;
    }

}
