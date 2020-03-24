package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.*;

import eg.edu.alexu.csd.oop.db.control.DBMSController;
import eg.edu.alexu.csd.oop.db.model.DataCarrier;

public class StatementImp implements Statement {

    private Logger log;
    private ConnectionImp connection;
    private ResultSetImp resultSet;
    private ArrayList<String> batches;
    private int time = 0;
    private boolean isClose;
    private DBMSController executor;

    public StatementImp(ConnectionImp con) {
        log = Logger.getInstance();
        executor = DBMSController.getInstance();
        log.log.info("Building Statement object.");
        this.connection = con;
        isClose = false;
    }

    @Override
    public void addBatch(String arg0) {
        try {
            if (isClose) {
                throw new SQLException("Statement is closed ..Can't do operations");
            }
            log.log.info("adding new Batch.");
            batches.add(arg0);

        } catch (Exception e) {
            log.log.info("Error in adding Batch");

        }

    }

    @Override
    public void clearBatch() {
        try {
            if (isClose) {
                throw new SQLException("Statement is closed ..Can't do operations");
            }
            log.log.info("Empties list of SQL commands.");
            batches.clear();

        } catch (Exception e) {
            log.log.info("Error in clearing Batch List");

        }

    }

    @Override
    public void close() {
        connection = null;
        batches = null;
        resultSet = null;
        isClose = true;
    }

    @Override
    public boolean execute(String arg0) throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("Executing the given SQL statement");

        if (arg0.trim().split("\\s+")[0].equalsIgnoreCase("use") || arg0.trim().split("\\s+")[0].equalsIgnoreCase("create") || arg0.trim().split("\\s+")[0].equalsIgnoreCase("drop")) {
            if (time == 0) {
                String s = executor.invoke(arg0);
                return !s.contains("wasn't");
            } else {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future<String> future = executorService.submit(() -> executor.invoke(arg0));
                try {
                    String result = future.get(time, TimeUnit.SECONDS);
                    executorService.shutdownNow();
                    return !result.contains("wasn't");
                } catch (TimeoutException e) {
                    executorService.shutdownNow();
                    throw new SQLException("Time out!");

                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("An error occurred: " + e.getMessage());
                }
                executorService.shutdownNow();
            }


        } else if (arg0.trim().split("\\s+")[0].equalsIgnoreCase("insert") || arg0.trim().split("\\s+")[0].equalsIgnoreCase("delete") || arg0.trim().split("\\s+")[0].equalsIgnoreCase("update")) {
            if (time == 0) {
                int result = executeUpdate(arg0);
                return result > 0;
            } else {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future<Integer> future = executorService.submit(() -> executeUpdate(arg0));
                try {
                    Integer result = future.get(time, TimeUnit.SECONDS);
                    executorService.shutdownNow();
                    return result > 0;

                } catch (TimeoutException e) {
                    executorService.shutdownNow();
                    throw new SQLException("Time out!");

                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("An error occurred: " + e.getMessage());
                }
                executorService.shutdownNow();
            }


        } else if (arg0.trim().split("\\s+")[0].equalsIgnoreCase("select")) {
            log.log.info("Generating result of select query..");
            if (time == 0) {
                ResultSet result = executeQuery(arg0);
                return result.getMetaData().getColumnCount() > 0;
            }
        } else {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<ResultSet> future = executorService.submit(() -> executeQuery(arg0));
            try {
                ResultSet result = future.get(time, TimeUnit.SECONDS);
                executorService.shutdownNow();
                return result.getMetaData().getColumnCount() > 0;

            } catch (TimeoutException e) {
                executorService.shutdownNow();
                throw new SQLException("Time out!");

            } catch (InterruptedException | ExecutionException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            executorService.shutdownNow();
        }

        return false;
    }


    @Override
    public ResultSet executeQuery(String arg0) throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("Executing the given SQL statement and returning ResultSet ");
        if(time==0){
            Object[][] producedData = executor.invokee(arg0);
            String tablename = executor.getTableName(arg0);
            DataCarrier columnsinfo = executor.getColumnsInfo(arg0);
            return new ResultSetImp(producedData, columnsinfo, tablename, this);
        }else {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<Object[][]> future = executorService.submit(() -> executor.invokee(arg0));
            try {
                Object[][] producedData = future.get(time, TimeUnit.SECONDS);
                executorService.shutdownNow();
                String tablename = executor.getTableName(arg0);
                DataCarrier columnsinfo = executor.getColumnsInfo(arg0);
                return new ResultSetImp(producedData, columnsinfo, tablename, this);
            } catch (TimeoutException e) {
                executorService.shutdownNow();
                throw new SQLException("Time out!");

            } catch (InterruptedException | ExecutionException e) {
                executorService.shutdownNow();
                throw new SQLException("An error occurred: " + e.getMessage());
            }
        }



    }

    @Override
    public int executeUpdate(String arg0) throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("Executing the given SQL Update statement.");
        if (time == 0) {
            String s = executor.invoke(arg0);
            String[] ss = s.split("\\s");
            return Integer.parseInt(ss[0]);
        } else {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(() -> executor.invoke(arg0));
            try {
                String result = future.get(time, TimeUnit.SECONDS);
                executorService.shutdownNow();
                String[] ss = result.split("\\s");
                return Integer.parseInt(ss[0]);
            } catch (TimeoutException e) {
                executorService.shutdownNow();
                throw new SQLException("Time out!");

            } catch (InterruptedException | ExecutionException e) {
                executorService.shutdownNow();
                throw new SQLException("An error occurred: " + e.getMessage());
            }
        }

    }

    @Override
    public int[] executeBatch() throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("Executing list of SQL commands.");
        int[] arr = new int[batches.size()];
        for (int i = 0; i < batches.size(); i++) {
            String query = batches.get(i);
            //   String s = executor.invoke(query);
            if (execute(query))
                arr[i] = SUCCESS_NO_INFO;

            else
                arr[i] = executeUpdate(query);
        }
        return arr;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("getting the current query timeout limit ");
        return time;
    }

    @Override
    public void setQueryTimeout(int arg0) throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("setting the current query timeout limit ");
        time = arg0;

    }

    @Override
    public Connection getConnection() throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("Getting the connection that produced this statement");
        return connection;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        if (isClose) {
            log.log.warning("Statement is closed.");
            throw new SQLException("Statement is closed ..Can't do operations");
        }
        log.log.info("Getting the current result as a ResultSet object ");
        return this.resultSet;
    }

    @Override
    public int getUpdateCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearWarnings() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void closeOnCompletion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String arg0, int arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String arg0, int[] arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String arg0, String[] arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String arg0, int arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String arg0, int[] arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String arg0, String[] arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchDirection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getGeneratedKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxFieldSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxRows() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults(int arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetConcurrency() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetHoldability() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLWarning getWarnings() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCloseOnCompletion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPoolable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCursorName(String arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEscapeProcessing(boolean arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchDirection(int arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchSize(int arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxFieldSize(int arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxRows(int arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPoolable(boolean arg0) {
        throw new UnsupportedOperationException();
    }

}
