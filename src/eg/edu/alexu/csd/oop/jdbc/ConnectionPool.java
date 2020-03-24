package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionPool {
    Connection getConnection();

    boolean releaseConnection(Connection connection);

    String getUrl();

    Properties getInfo();

    public int getSize();

    public void destroy() throws SQLException;

}
