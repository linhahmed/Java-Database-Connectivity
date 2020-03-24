package eg.edu.alexu.csd.oop.jdbc;

import java.sql.ResultSetMetaData;

import eg.edu.alexu.csd.oop.db.model.DataCarrier;

public class ResultSetMetaDataImp implements ResultSetMetaData {
    private DataCarrier columnsInfo;
    private Object[][] producedData;
    private String tableName;
    private Logger log;

     ResultSetMetaDataImp(Object[][] producedData, DataCarrier columnsInfo, String tableName) {
        this.producedData = producedData;
        this.columnsInfo = columnsInfo;
        this.tableName = tableName;
        log = Logger.getInstance();
    }

    @Override
    public int getColumnCount() {
        log.log.info("Fetching the column count");
        if (producedData.length == 0) {
            return 0;
        } else {
            return producedData[0].length;
        }

    }

    @Override
    public boolean isAutoIncrement(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCaseSensitive(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSearchable(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCurrency(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int isNullable(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSigned(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getColumnDisplaySize(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getColumnLabel(int column) {
        if (column < 0 || column > columnsInfo.columns.length) {
            log.log.warning("index out of columns bounds");
            throw new IndexOutOfBoundsException("index out of columns bounds");
        }
        log.log.info("Fetching the column label");
        if (columnsInfo.values[column] == null)
            return columnsInfo.columns[column];
        else
            return columnsInfo.values[column];
    }

    @Override
    public String getColumnName(int column) {
        if (column < 0 || column > columnsInfo.columns.length) {
            log.log.warning("index out of columns bounds");
            throw new IndexOutOfBoundsException("index out of columns bounds");
        }
        log.log.info("Fetching the column name");
        String[] names = columnsInfo.columns;
        return names[column];
    }

    @Override
    public String getSchemaName(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPrecision(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getScale(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTableName(int column)  {
        if (column < 0 || column > columnsInfo.columns.length) {
            log.log.warning("index out of columns bounds");
            throw new IndexOutOfBoundsException("index out of columns bounds");
        }
        log.log.info("Fetching the table name");
        return tableName;
    }

    @Override
    public String getCatalogName(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getColumnType(int column)  {
        if (column < 0 || column > columnsInfo.columns.length) {
            log.log.warning("index out of columns bounds");
            throw new IndexOutOfBoundsException("index out of columns bounds");
        }
        log.log.info("Fetching the column type");
        String[] types = columnsInfo.columnsTypes;
        if (types[column].equalsIgnoreCase("string"))
            return 12;
        else if (types[column].equalsIgnoreCase("int"))
            return 4;
        else
            return 0;
    }

    @Override
    public String getColumnTypeName(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isReadOnly(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWritable(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDefinitelyWritable(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getColumnClassName(int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iFace) {
        throw new UnsupportedOperationException();
    }
}