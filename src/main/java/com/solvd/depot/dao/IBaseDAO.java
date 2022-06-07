package com.solvd.depot.dao;

import com.solvd.depot.dao.jdbcMySqlImpl.StationDAO;
import org.apache.commons.dbutils.DbUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IBaseDAO <T>{

    final static Logger LOGGER = LogManager.getLogger(IBaseDAO.class);
    T getEntityById (Long id);
    void saveEntity(T entity);
    void updateEntity(T entity);
    void removeEntity(Long id);

    public static void closeResultSet(ResultSet resultSet) {
        try {
            DbUtils.close(resultSet);
        } catch (SQLException e) {
            LOGGER.info("ResultSet closure error", e);
        }
    }

    public static void closePreparedStatement(PreparedStatement statement) {
        try {
            DbUtils.close(statement);
        } catch (SQLException e) {
            LOGGER.info("PreparedStatement closure error", e);
        }
    }
}
