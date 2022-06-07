package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.ITypeDAO;
import com.solvd.depot.models.Licence;
import com.solvd.depot.models.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeDAO implements ITypeDAO {
    private final static Logger LOGGER = LogManager.getLogger(ITypeDAO.class);
    private final static String GET_TYPE_BY_ID = "SELECT * FROM types where id =?";
    private final static String INSERT_TYPE = "INSERT INTO types(name) VALUES (?)";
    private final static String UPDATE_TYPE = "UPDATE types SET name =? WHERE  id =?";
    private final static String DELETE_TYPE_BY_ID = "DELETE FROM types WHERE id=?";
    private static final String GET_TYPE_BY_BUS_ID = "SELECT buses.id, types.name FROM types INNER JOIN buses ON types.id = buses.types_id WHERE buses.id IN (?)";

    @Override
    public Type getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Type type = new Type();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_TYPE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            type.setId(resultSet.getLong("id"));
            type.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return type;
    }

    @Override
    public void saveEntity(Type entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_TYPE);
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
    public void updateEntity(Type entity) {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                statement = connection.prepareStatement(UPDATE_TYPE);
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
            statement = connection.prepareStatement(DELETE_TYPE_BY_ID);
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
    public Type getTypeByBusId(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Type type = new Type();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_TYPE_BY_BUS_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            type.setId(resultSet.getLong("id"));
            type.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return type;
    }
}
