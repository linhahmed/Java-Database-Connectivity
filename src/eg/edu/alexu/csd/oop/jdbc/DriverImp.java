package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;


public class DriverImp implements Driver {

    private static final String URL_REGEX = "jdbc:(\\w+)db://localhost";
    private static final Pattern urlPattern = Pattern.compile(URL_REGEX);
    private final String INVALID_URL = "Invalid url Format";


    @Override
    public boolean acceptsURL(String arg0) throws SQLException {
        Matcher urlMatcher = urlPattern.matcher(arg0);
        if (!urlMatcher.matches()) throw new SQLException(INVALID_URL);
        return true;
    }

    @Override
    public Connection connect(String url, Properties info){
        File path1 = (File) info.get("path");
        String path = path1.getAbsolutePath();
        return new ConnectionImp(url, path);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) {
        DriverPropertyInfo[] propertyInfos = new DriverPropertyInfo[info.keySet().size()];
        Iterator<Object> itr = info.keySet().iterator();
        int counter = 0;
        while (itr.hasNext()) {
            String str = (String) itr.next();
            propertyInfos[counter++] = new DriverPropertyInfo(str, info
                    .getProperty(str));
        }
        return propertyInfos;
    }


    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger()  {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException();
    }

}
