package com.solvd.depot.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.dbutils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private String url;
    private String username;
    private String password;
    private int poolSize;
    private BlockingQueue<Connection> connections;
    private static ConnectionPool instance = new ConnectionPool();
    private ReentrantLock locker = new ReentrantLock();
    public final static String DEFAULT_PATH_TO_PROPERTIES = "src/main/resources/db.properties";

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void init() {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(DEFAULT_PATH_TO_PROPERTIES);
            property.load(fis);
            instance.setUrl(property.getProperty("db.url"));
            instance.setUsername(property.getProperty("db.username"));
            instance.setPassword(property.getProperty("db.password"));
            instance.setPoolSize(Integer.parseInt(property.getProperty("db.poolSize")));
            Class.forName(property.getProperty("db.driver"));
            connections = new ArrayBlockingQueue<Connection>(getPoolSize());
            Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
            connections.add(connection);
            LOGGER.info("The new connection was created");
            poolSize--;
            LOGGER.info("The connection pool was initialized");
        } catch (SQLException e) {
            LOGGER.fatal("The connection pool initialization error", e);
        } catch (IOException e) {
            LOGGER.error("Properties file for database not found", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Connection driver class not found", e);
        }
    }

    public Connection getConnection() {
        locker.lock();
        Connection connection = null;
        Connection newConnection = null;
        try {
            if (connections.size() == 0 && poolSize > 0) {
                poolSize--;
                LOGGER.info(poolSize);
                newConnection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
                LOGGER.info("The new connection was created");
                connections.add(newConnection);
                connection = connections.take();
                LOGGER.info("The connection was taken");
            } else {
                connection = connections.take();
                LOGGER.info("The connection was taken");
            }
        } catch (InterruptedException e) {
            LOGGER.info("The connection error", e);
        } catch (SQLException e) {
            LOGGER.info("Unable to connect", e);
        }
        finally {
            locker.unlock();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            connections.add(connection);
            LOGGER.info("The connection was released");
        } else {
            closeConnection(connection);
            LOGGER.info("The connection is null");
        }
    }

    public void closeConnection(Connection connection) {
        DbUtils.closeQuietly(connection);
        poolSize--;
        LOGGER.info("The connection was closed");
    }
}
