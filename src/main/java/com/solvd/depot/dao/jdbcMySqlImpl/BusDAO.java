package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.IBusDAO;
import com.solvd.depot.models.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusDAO implements IBusDAO {

    private static final Logger LOGGER = LogManager.getLogger(IBusDAO.class);
    private final static String GET_BUS_BY_ID = "SELECT * FROM buses where id =?";
    private final static String INSERT_BUS = "INSERT INTO buses(name, types_id, drivers_id, models_id, routes_id, depots_id) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_BUS = " UPDATE buses SET name = (?), types_id = (?), drivers_id = (?), models_id = (?), routes_id = (?), depots_id = (?) WHERE id = ?";
    private final static String DELETE_BUS_BY_ID = "DELETE FROM buses WHERE id=?";
    private static final String GET_ALL_BUSES_BY_TYPE_ID = "SELECT buses.id, buses.name, types.name FROM buses INNER JOIN types ON types.id = buses.types_id WHERE types.id = ?";


    @Override
    public Bus getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Bus bus = new Bus();
        Type type = new Type();
        Driver driver = new Driver();
        Model model = new Model();
        Route route = new Route();
        Depot depot = new Depot();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_BUS_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            bus.setId(resultSet.getLong("id"));
            bus.setName(resultSet.getString("name"));
            type.setId(resultSet.getLong("types_id"));
            bus.setType(type);
            driver.setId(resultSet.getLong("drivers_id"));
            bus.setDriver(driver);
            model.setId(resultSet.getLong("models_id"));
            bus.setModel(model);
            route.setId(resultSet.getLong("routes_id"));
            bus.setRoute(route);
            depot.setId(resultSet.getLong("depots_id"));
            bus.setDepot(depot);

        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return bus;
    }

    @Override
    public void saveEntity(Bus entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_BUS);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getType().getId());
            statement.setLong(3, entity.getDriver().getId());
            statement.setLong(4, entity.getModel().getId());
            statement.setLong(5, entity.getRoute().getId());
            statement.setLong(6, entity.getDepot().getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Creation error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public void updateEntity(Bus entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_BUS);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getType().getId());
            statement.setLong(3, entity.getDriver().getId());
            statement.setLong(4, entity.getModel().getId());
            statement.setLong(5, entity.getRoute().getId());
            statement.setLong(6, entity.getDepot().getId());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Update error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public void removeEntity(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_BUS_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Delete from the data base error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public Bus getBusByDepotId(Long id) {
        return null;
    }

    @Override
    public List<Bus> getALLBusesByTypeId(Long id) {
        List<Bus> buses = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_BUSES_BY_TYPE_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bus bus = new Bus();
                bus.setId(resultSet.getLong("id"));
                bus.setName(resultSet.getString("name"));
                buses.add(bus);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return buses;
    }


}
