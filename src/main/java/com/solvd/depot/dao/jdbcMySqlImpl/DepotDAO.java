package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.IDepotDAO;
import com.solvd.depot.models.Depot;
import com.solvd.depot.models.Manager;
import com.solvd.depot.models.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepotDAO implements IDepotDAO {

    private static final Logger LOGGER = LogManager.getLogger(IDepotDAO.class);
    private static final String GET_DEPOT_BY_ID = "SELECT * FROM depots where id =?";
    private static final String INSERT_DEPOT = "INSERT INTO depots(name) VALUES (?)";
    private static final String UPDATE_DEPOT = "UPDATE depots SET name =? WHERE  id =?";
    private static final String DELETE_DEPOT_BY_ID = "DELETE FROM depots WHERE id=?";
    private static final String GET_DEPOT_BY_BUS_ID = "SELECT buses.id, depots.name FROM depots INNER JOIN buses ON depots.id = buses.routes_id WHERE buses.id IN(?)";

    @Override
    public Depot getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Depot depot = new Depot();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_DEPOT_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            depot.setId(resultSet.getLong("id"));
            depot.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return depot;
    }

    @Override
    public void saveEntity(Depot entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_DEPOT);
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
    public void updateEntity(Depot entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_DEPOT);
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
            statement = connection.prepareStatement(DELETE_DEPOT_BY_ID);
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
    public Depot getDepotByBusId(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Depot depot = new Depot();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_DEPOT_BY_BUS_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            depot.setId(resultSet.getLong("id"));
            depot.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return depot;
    }
}
