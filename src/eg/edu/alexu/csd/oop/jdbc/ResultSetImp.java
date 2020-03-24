package eg.edu.alexu.csd.oop.jdbc;


import eg.edu.alexu.csd.oop.db.model.DataCarrier;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


public class ResultSetImp implements ResultSet {

	private Logger log;
	private boolean close;
	private int rows;
	private int Columns;
	private int RowCursor;
	private int ColumnCursor;
	private StatementImp statement;
	private DataCarrier ColumnsInfo;
	private Object[][] ProductedData;
	private String tableName;
	
	public ResultSetImp(Object[][] ProductedData, DataCarrier ColumnsInfo, String tableName, StatementImp statement) {
		log = Logger.getInstance();
		log.log.info("Building ResultSet object.");
		this.statement = statement;
		this.ColumnsInfo = ColumnsInfo;
		this.ProductedData = ProductedData;
		this.tableName = tableName;
		close = false;
		rows = ProductedData.length;
		if (ProductedData.length != 0 && ProductedData[0] != null) {
			Columns = ProductedData[0].length;
		} else {
			Columns = 0;
		}
		ColumnCursor = 0;
		RowCursor = 0;
	}

	

	@Override
	public boolean isWrapperFor(Class<?> iface)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		log.log.info("Moving to the absolute row " + row);
		if (row > 0) {
			RowCursor = row;
		} else if (row < 0) {
			RowCursor = rows + 1 + row;
		} else {
			RowCursor = 0;
		}
		return RowCursor > 0 && RowCursor < rows + 1;
	}

	@Override
	public void afterLast() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		log.log.info("Moving to after Last row.");
		if (rows != 0) {
			RowCursor = rows + 1;
		}
	}

	@Override
	public void beforeFirst() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		log.log.info("Moving to before First row.");
		RowCursor = 0;
	}

	@Override
	public void cancelRowUpdates()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws SQLException {
		if (!close) {
			log.log.warning("Closing ResultSet.");
			close = true;
			ProductedData = null;
		}
		log.log.warning("ResultSet is alraedy closed.");
		throw new SQLException("ResultSet is already closed.");
	}

	@Override
	public void deleteRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		if (columnLabel == null) {
			log.log.warning("column Label is null.");
			throw new SQLException("column Label is null.");
		} else {
			String[] names = ColumnsInfo.columns;
			String[] labels = ColumnsInfo.values;
			for (int i = 0; i < names.length; i++) {
				if (labels[i] == null) {
					if (columnLabel.equalsIgnoreCase(names[i])) {
						ColumnCursor = i;
						log.log.info("The index of " + columnLabel + "is" + ColumnCursor);
						return ColumnCursor;
					}
				} else if (labels[i] != null) {
					if (columnLabel.equalsIgnoreCase(labels[i])) {
						ColumnCursor = i;
						log.log.info("The index of " + columnLabel + "is" + ColumnCursor);
						return ColumnCursor;
					}
				}
			}
			log.log.warning("The given Column Label doesn't exist in ResultSet");
			throw new SQLException("The given Column Label doesn't exist in ResultSet");
		}
	}

	@Override
	public boolean first() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		log.log.info("Moving to First row.");
		RowCursor = 1;
		return rows != 0;
	}

	@Override
	public Array getArray(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int arg0, int arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String arg0, int arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getConcurrency()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCursorName()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int arg0, Calendar arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String arg0, Calendar arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchDirection()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		log.log.info("Fetching the value of the designated column at" + columnIndex);
		if (columnIndex > Columns) {
			log.log.info("Given column index that exceeds the number of Columns.");
			throw new SQLException("Invalid column index.");
		}
		if (isAfterLast() || isBeforeFirst()) {
			throw new SQLException();
		}
		Object x = ProductedData[RowCursor - 1][columnIndex - 1];
		if (x instanceof String) {
			String y = (String) x;
			if (y.equalsIgnoreCase("null")) {
				return 0;
			}
		}
		//see later
		int op = (Integer) x;
		return op;
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return getInt(findColumn(columnLabel));
	}

	@Override
	public long getLong(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		return new ResultSetMetaDataImp(ProductedData, ColumnsInfo, tableName);
	}

	@Override
	public Reader getNCharacterStream(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		if (columnIndex > Columns) {
			log.log.info("Given column index that exceeds the number of Columns.");
			throw new SQLException("Invalid column index.");
		}
		if (isAfterLast() || isBeforeFirst()) {
			throw new RuntimeException();
		}
		log.log.info("Fetching the value of the designated column at " + columnIndex);
		Object x = ProductedData[RowCursor - 1][columnIndex - 1];
		if (x instanceof String) {
			String y = (String) x;
			if (y.equalsIgnoreCase("null")) {
				return null;
			}
			return y;
		}
		else {
			Integer y = (Integer) x;
			return y;
		}
	}

	@Override
	public Object getObject(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(int arg0, Class<T> arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(String arg0, Class<T> arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement getStatement() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		return statement;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("ResultSet is closed.");
		}
		if (columnIndex > Columns) {
			log.log.info("Given invalid column index to getString.");
			throw new SQLException("Invalid column index.");
		}
		if (isAfterLast() || isBeforeFirst()) {
			throw new SQLException();
		}
		log.log.info("Fetching the value of the designated column at  " + columnIndex);
		Object x = ProductedData[RowCursor - 1][columnIndex - 1];
		if (x instanceof String) {
			final String y = (String) x;
			if (y.equalsIgnoreCase("null")) {
				return null;
			}
		}
		//see later
		String op = (String) x;
		return op;

	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return getString(findColumn(columnLabel));
	}

	@Override
	public Time getTime(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int arg0, Calendar arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String arg0, Calendar arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getType()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insertRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		return RowCursor == rows + 1;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		return RowCursor == 0;
	}

	@Override
	public boolean isClosed()  {
		return close;
	}

	@Override
	public boolean isFirst() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		return RowCursor == 1;
	}

	@Override
	public boolean isLast() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		return RowCursor == rows;
	}

	@Override
	public boolean last() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		log.log.info("Moving to last row.");
		RowCursor = rows;
		return rows != 0;
	}

	@Override
	public void moveToCurrentRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveToInsertRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean next() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		log.log.info("Moving forward one row.");
		RowCursor++;
		return !isAfterLast();
	}

	@Override
	public boolean previous() throws SQLException {
		if (close) {
			log.log.warning("ResultSet is closed.");
			throw new SQLException("Resultset is closed.");
		}
		log.log.info("Moving backwards one row.");
		RowCursor--;
		return !isBeforeFirst();
	}

	@Override
	public void refreshRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean relative(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowDeleted()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowInserted()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowUpdated()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchSize(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(int arg0, Array arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(String arg0, Array arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(int arg0, BigDecimal arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(String arg0, BigDecimal arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int arg0, Blob arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String arg0, Blob arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(int arg0, boolean arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(String arg0, boolean arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(int arg0, byte arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(String arg0, byte arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(int arg0, byte[] arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(String arg0, byte[] arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int arg0, Clob arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String arg0, Clob arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(int arg0, Date arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(String arg0, Date arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(int arg0, double arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(String arg0, double arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(int arg0, float arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(String arg0, float arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(int arg0, int arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(String arg0, int arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(int arg0, long arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(String arg0, long arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int arg0, NClob arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String arg0, NClob arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String arg0, Reader arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(int arg0, String arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(String arg0, String arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(int arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(String arg0)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int arg0, Object arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String arg0, Object arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int arg0, Object arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String arg0, Object arg1, int arg2)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(int arg0, Ref arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(String arg0, Ref arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRow()  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(int arg0, RowId arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(String arg0, RowId arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(int arg0, short arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(String arg0, short arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(int arg0, String arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(String arg0, String arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(int arg0, Time arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(String arg0, Time arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(int arg0, Timestamp arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(String arg0, Timestamp arg1)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean wasNull()  {
		throw new UnsupportedOperationException();
	}

}