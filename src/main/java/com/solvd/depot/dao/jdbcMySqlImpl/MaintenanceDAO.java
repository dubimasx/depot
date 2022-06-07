package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.IMaintenanceDAO;
import com.solvd.depot.models.Licence;
import com.solvd.depot.models.Maintenance;
import com.solvd.depot.models.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceDAO implements IMaintenanceDAO {
    private static final Logger LOGGER = LogManager.getLogger(IMaintenanceDAO.class);
    private static final String GET_MAINTENANCE_BY_ID = "SELECT * FROM maintenance WHERE id =?";
    private static final String INSERT_MAINTENANCE = "INSERT INTO maintenance(date, services_id, mechanics_id, buses_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_MAINTENANCE = "UPDATE maintenance SET date = ?, services_id = ?, mechanics_id = ?, buses_id = ? WHERE id IN(?)";
    private static final String DELETE_MAINTENANCE_BY_ID = "DELETE FROM maintenance WHERE id=?";

    @Override
    public Maintenance getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Maintenance maintenance = new Maintenance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_MAINTENANCE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            maintenance.setId(resultSet.getLong("id"));
            maintenance.setDate(resultSet.getDate("date"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return maintenance;
    }

    @Override
    public void saveEntity(Maintenance entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_MAINTENANCE);
            statement.setDate(1, entity.getDate());
            statement.setLong(2, entity.getService().getId());
            statement.setLong(3, entity.getMechanic().getId());
            statement.setLong(4, entity.getBus().getId());
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
    public void updateEntity(Maintenance entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_MAINTENANCE);
            statement.setDate(1, entity.getDate());
            statement.setLong(2, entity.getService().getId());
            statement.setLong(3, entity.getMechanic().getId());
            statement.setLong(4, entity.getBus().getId());
            statement.setLong(5, entity.getId());
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
            statement = connection.prepareStatement(DELETE_MAINTENANCE_BY_ID);
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
