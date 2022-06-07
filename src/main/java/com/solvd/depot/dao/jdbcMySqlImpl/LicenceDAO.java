package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.ILicenceDAO;
import com.solvd.depot.models.Licence;
import com.solvd.depot.models.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LicenceDAO implements ILicenceDAO {

    private static final Logger LOGGER = LogManager.getLogger(LicenceDAO.class);
    private static final String INSERT_LICENCE = "INSERT INTO licences(expiration) VALUES(?)";
    private static final String UPDATE_LICENCE = "UPDATE licences SET expiration =? WHERE id =?";
    private static final String DELETE_LICENCE_BY_ID = "DELETE FROM licences WHERE id=?";
    private static final String GET_ALL_LICENCES_BY_CATEGORY_ID = "SELECT licences.id, licences.expiration, categories.name FROM licences " +
            "INNER JOIN categories_has_licences " +
            "ON licences.id = categories_has_licences.licences_id " +
            "INNER JOIN categories " +
            "ON categories.id = categories_has_licences.categories_id " +
            "WHERE categories.id IN (?) ";
    private static final String GET_LICENCE_BY_DRIVER_ID = "SELECT licences.id, licences.expiration FROM licences INNER JOIN drivers ON drivers.licences_id = licences.id WHERE drivers.id IN(?) ";
    private static final String GET_LICENCE_BY_ID = "SELECT * FROM licences WHERE id =?";
    @Override
    public Licence getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Licence licence = new Licence();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_LICENCE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            licence.setId(resultSet.getLong("id"));
            licence.setExpiration(resultSet.getDate("expiration"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return licence;
    }

    @Override
    public void saveEntity(Licence entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_LICENCE);
            statement.setDate(1, entity.getExpiration());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creation error", e);
        } finally {
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public void updateEntity(Licence entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_LICENCE);
            statement.setDate(1, entity.getExpiration());
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
            statement = connection.prepareStatement(DELETE_LICENCE_BY_ID);
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
    public List<Licence> getAllLicencesByCategoryId(Long id) {
        List<Licence> licences = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_LICENCES_BY_CATEGORY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Licence licence = new Licence();
                licence.setId(resultSet.getLong("id"));
                licence.setExpiration(resultSet.getDate("expiration"));
                licences.add(licence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return licences;
    }

    @Override
    public Licence getLicenceByDriverId(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Licence licence = new Licence();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_LICENCE_BY_DRIVER_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            licence.setId(resultSet.getLong("id"));
            licence.setExpiration(resultSet.getDate("expiration"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return licence;
    }
}
