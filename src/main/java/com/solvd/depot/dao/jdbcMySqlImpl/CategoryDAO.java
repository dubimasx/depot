package com.solvd.depot.dao.jdbcMySqlImpl;

import com.solvd.depot.connection.ConnectionPool;
import com.solvd.depot.dao.IBaseDAO;
import com.solvd.depot.dao.ICategoryDAO;
import com.solvd.depot.models.Category;
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

public class CategoryDAO implements ICategoryDAO {
    private final static Logger LOGGER = LogManager.getLogger(CategoryDAO.class);
    private final static String GET_CATEGORY_BY_ID = "SELECT * FROM categories where id =?";
    private final static String INSERT_CATEGORY = "INSERT INTO categories(name) VALUES (?)";
    private final static String UPDATE_CATEGORY = "UPDATE categories SET name =? WHERE  id =?";
    private final static String DELETE_CATEGORY_BY_ID = "DELETE FROM categories WHERE id=?";
    private final static String GET_ALL_CATEGORIES_BY_LICENCE_ID = "SELECT * FROM categories " +
            "INNER JOIN categories_has_licences " +
            "ON categories_has_licences.categories_id = categories.id " +
            "INNER JOIN licences " +
            "ON categories_has_licences.licences_id = licences.id " +
            "WHERE licences.id IN (?)";
    @Override
    public Category getEntityById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Category category = new Category();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_CATEGORY_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            category.setId(resultSet.getLong("id"));
            category.setName(resultSet.getString("name"));
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
        return category;
    }

    @Override
    public void saveEntity(Category entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_CATEGORY);
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
    public void updateEntity(Category entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_CATEGORY);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Update error", e);
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
    public void removeEntity(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_CATEGORY_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Delete from the data base error", e);
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
    public List<Category> getAllCategoriesByLicenceId(Long id) {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_CATEGORIES_BY_LICENCE_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            IBaseDAO.closeResultSet(resultSet);
            IBaseDAO.closePreparedStatement(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return categories;
    }
}
