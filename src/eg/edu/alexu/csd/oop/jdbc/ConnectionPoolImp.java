package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectionPoolImp implements ConnectionPool {
    private String url;
    private Properties info;
    private ArrayList<Connection> connectionPool;
    private ArrayList<Connection> usedConnections ;
    private int maxSize ;

    public  ConnectionPoolImp (String url, Properties info,int maxSize)  {
        this.maxSize=maxSize;
        this.info =info;
        this.url = url;
        connectionPool = new ArrayList<>(maxSize);
        usedConnections = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            connectionPool.add(createConnection(url,info));
        }
    }

    @Override
    public Connection getConnection() {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxSize) {
                connectionPool.add(createConnection(url,info));
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Properties getInfo() {
        return info;
    }

    private Connection createConnection(String url, Properties info ) {
        File path1 = (File) info.get("path");
        String path = path1.getAbsolutePath();
        return new ConnectionImp(url,path);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();

    }
    @Override
    public void destroy() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();

    }
}

