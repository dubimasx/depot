package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.IStationDAO;
import com.solvd.depot.models.Licence;
import com.solvd.depot.models.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAO implements IStationDAO {

    private final static Logger LOGGER = LogManager.getLogger(StationDAO.class);
    private final static String GET_STATION_BY_ID = "SELECT * FROM stations where id =?";
    private final static String INSERT_STATION = "INSERT INTO stations(name) VALUES (?)";
    private final static String UPDATE_STATION = "UPDATE stations SET name =? WHERE  id =?";
    private final static String DELETE_STATION_BY_ID = "DELETE FROM stations WHERE id=?";

    private final static String GET_ALL_STATIONS_BY_ROUTE_ID = "SELECT routes_has_stations.routes_id, routes_has_stations.stations_id, stations.name, routes_has_stations.order FROM routes_has_stations " +
            " INNER JOIN routes " +
            " ON routes.id = routes_has_stations.routes_id" +
            " INNER JOIN stations ON stations.id = routes_has_stations.stations_id " +
            " WHERE routes.id = ? " +
            " ORDER BY routes_has_stations.order ASC";

    @Override
    public Station getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Station station = new Station();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_STATION_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            station.setId(resultSet.getLong("id"));
            station.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Request from the data base error", e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return station;
    }

    @Override
    public void saveEntity(Station entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_STATION);
            statement.setString(1, entity.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creation error", e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }

    @Override
    public void updateEntity(Station entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_STATION);
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
            statement = connection.prepareStatement(DELETE_STATION_BY_ID);
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
    public List<Station> getAllStationsByRouteId(Long id) {
        List<Station> stations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_STATIONS_BY_ROUTE_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Station station = new Station();
                station.setId(resultSet.getLong("stations_id"));
                station.setName(resultSet.getString("name"));
                stations.add(station);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return stations;
    }
}
