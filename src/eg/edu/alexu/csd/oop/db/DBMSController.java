package eg.edu.alexu.csd.oop.db;

import eg.edu.alexu.csd.oop.db.parser.QV;

import java.sql.SQLException;

class DBMSController {
    private DBMSController() throws SQLException {
        manager = new DBMS();
    }

    private static DBMSController instance;
    private static Database manager;
    private static QV validator;

    static {
        try {
            instance = new DBMSController();
            validator = new QV();
        } catch (SQLException s) {

        }
    }

    static DBMSController getInstance() {
        return instance;
    }

    String invoke(String query) throws SQLException {
        query = query.toLowerCase();
        int operation = validator.isValidQuery(query);
        if (operation == 1 || operation == 2) {
            boolean test = manager.executeStructureQuery(query);
            if (test) {
                return ((operation == 1 ? "Database" : "Table") + " created successfully.");
            } else {
                return ((operation == 1 ? "Database" : "Table") + " wasn't created successfully.");

            }
        } else if (operation == 3 || operation == 4) {
            boolean test1 = manager.executeStructureQuery(query);
            if (test1) {
                return ((operation == 3 ? "Database" : "Table") + " dropped successfully.");
            } else {
                return ((operation == 3 ? "Database" : "Table") + " wasn't dropped successfully.");
            }
        } else if (operation >= 5 && operation <= 8) {
            Object[][] test2 = manager.executeQuery(query);
            if (test2 == null) {
                return "Wrong selection!!";
            } else {
                StringBuilder st = new StringBuilder();
                for (Object[] objects : test2) {
                    for (int j = 0; j < test2[0].length; j++) {
                        st.append(objects[j].toString()).append(" ");
                    }
                    st.append("\n");
                }
                return st.toString();
            }
        } else if (operation >= 9 && operation <= 14) {
                return manager.executeUpdateQuery(query) + " rows has been Updated.";

        } else {
            throw new SQLException("Not a valid SQL query!");
        }
    }


}