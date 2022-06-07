package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.IManagerDAO;
import com.solvd.depot.dao.IMechanicDAO;
import com.solvd.depot.models.Manager;
import com.solvd.depot.models.Mechanic;
import com.solvd.depot.models.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO implements IManagerDAO {

    private final static Logger LOGGER = LogManager.getLogger(ManagerDAO.class);
    private final static String GET_MANAGER_BY_ID = "SELECT * FROM managers where id =?";
    private final static String INSERT_MANAGER = "INSERT INTO managers(name) VALUES (?)";
    private final static String UPDATE_MANAGER = "UPDATE managers SET name =? WHERE  id =?";
    private final static String DELETE_MANAGER_BY_ID = "DELETE FROM managers WHERE id=?";
    @Override

    public Manager getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Manager manager = new Manager();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_MANAGER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            manager.setId(resultSet.getLong("id"));
            manager.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return manager;
    }

    @Override
    public void saveEntity(Manager entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_MANAGER);
            statement.setString(1, entity.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creation error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public void updateEntity(Manager entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_MANAGER);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getId());
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
            statement = connection.prepareStatement(DELETE_MANAGER_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Delete from the data base error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }
}
