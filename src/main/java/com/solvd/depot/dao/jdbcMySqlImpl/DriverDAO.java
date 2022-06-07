package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.IDriverDAO;
import com.solvd.depot.models.Driver;
import com.solvd.depot.models.Licence;
import com.solvd.depot.models.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDAO implements IDriverDAO {

    private final static Logger LOGGER = LogManager.getLogger(DriverDAO.class);
    private static final String GET_DRIVER_BY_ID ="SELECT * FROM drivers where id =?";
    private final static String INSERT_DRIVER = "INSERT INTO drivers(name, age, licences_id) values (?, ?, ?)";
    private final static String UPDATE_DRIVER = "UPDATE drivers SET name = ? , age = ? , licences_id = ? WHERE id in (?)";
    private final static String DELETE_DRIVER_BY_ID = "DELETE FROM drivers WHERE id=?";
    private final static String GET_DRIVER_BY_BUS_ID ="SELECT drivers.id, drivers.name from drivers " +
                                                      "INNER JOIN buses " +
                                                      "ON buses.drivers_id = drivers.id " +
                                                      "WHERE buses.id IN (?)";
    @Override
    public Driver getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Driver driver = new Driver();
        Licence licence = new Licence();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_DRIVER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            driver.setId(resultSet.getLong("id"));
            driver.setName(resultSet.getString("name"));
            driver.setAge(resultSet.getInt("age"));
            licence.setId(resultSet.getLong("licences_id"));
            driver.setLicence(licence);
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return driver;
    }

    @Override
    public void saveEntity(Driver entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_DRIVER);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getAge());
            statement.setLong(3, entity.getLicence().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creation error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public void updateEntity(Driver entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_DRIVER);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getAge());
            statement.setLong(3, entity.getLicence().getId());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
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
            statement = connection.prepareStatement(DELETE_DRIVER_BY_ID);
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
    public Driver getDriverByBusId(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Driver driver = new Driver();
        Licence licence = new Licence();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_DRIVER_BY_BUS_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            driver.setId(resultSet.getLong("id"));
            driver.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return driver;
    }
}
