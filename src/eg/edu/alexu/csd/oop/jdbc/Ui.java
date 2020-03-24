package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Ui {
    public static void main(String[] args) throws SQLException {
        Driver driver = new DriverImp();
        File dbDir = new File("jdbc:xmldb://localhost");
        Properties info = new Properties();
        info.put("path", dbDir.getAbsoluteFile());
        Connection con = driver.connect("jdbc:xmldb://localhost", info);
        Statement statement = con.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println("type '.help' for help");
        while (true) {
            System.out.println("SQL >> ");
            String query = scan.nextLine();
            if (query.equalsIgnoreCase(".quit")) {
                System.exit(0);
            }
            if (query.equalsIgnoreCase(".help")) {
                System.out.println(
                        "Following queries can be handled:\nCreate database database_name\nCreate table table_name\nDrop database database_name\nDrop table table_name\nInsert into table_name (column1, column2,...) values (value1, value2,...)\nInsert into table_name values (value1, value2,...)\nDelete from table_name\nDelete from table_name where column [><=] value\nSelect * from table_name\nSelect * from table_name where column [><=] value\nSelect column1, column2,.... from table_name\nSelect column1, column2,.... from table_name where column [><=] value\nUpdate table_name set column1 = value1, column2 = value2,...\nUpdate table_name set column1 = value1, column2 = value2,... where column [><=] value");
                continue;
            }
            if (query.isEmpty()) {
                query = scan.nextLine();
            }
            try {
                String query0 = query.trim().split("\\s+")[0];
                if (query0.equalsIgnoreCase("create") || query0.equalsIgnoreCase("drop") || query0.equalsIgnoreCase("use")) {
                    boolean op = statement.execute(query);
                    if (op)
                        System.out.println("Query has been executed successfully");
                    else
                        System.out.println("Query hasn't been executed successfully");
                } else if (query0.equalsIgnoreCase("insert") || query0.equalsIgnoreCase("delete")
                        || query0.equalsIgnoreCase("update")) {
                    int op = statement.executeUpdate(query);
                    System.out.println(op + " row has been changed");
                } else if (query0.equalsIgnoreCase("select")) {
                    ResultSet rs = statement.executeQuery(query);
                    int CC = rs.getMetaData().getColumnCount();
                    StringBuilder st = new StringBuilder();
                    while (rs.next()) {
                        for (int i = 1; i <= CC; i++) {
                            if (rs.getObject(i) == null) {
                                st.append("|");
                                continue;
                            }
                            st.append(rs.getObject(i)).append("|");
                        }
                        st.append("\n");

                    }
                    System.out.println(st);
                } else {
                    System.out.println("Wrong Query");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}